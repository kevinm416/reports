package com.kevinm416.report.openid;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.openid4java.association.AssociationException;
import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.MessageException;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.ax.FetchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.base.Throwables;
import com.kevinm416.report.user.User;
import com.kevinm416.report.user.UserCache;

@Path("openid")
public class OpenIdResource {

    Logger log = LoggerFactory.getLogger(OpenIdResource.class);

    private final static String YAHOO_ENDPOINT = "https://me.yahoo.com";
    private final static String GOOGLE_ENDPOINT = "https://www.google.com/accounts/o8/id";

    @POST
    public Response authenticationRequest(
            @Context HttpServletRequest request,
            @FormParam("openid_identifier") String identifier) {
        UUID sessionToken= UUID.randomUUID();
        String returnToUrl = getReturnToUrl(request, sessionToken);

        ConsumerManager consumerManager = new ConsumerManager();

        try {
            @SuppressWarnings("rawtypes")
            List discoveries = consumerManager.discover(identifier);
            DiscoveryInformation discovered = consumerManager.associate(discoveries);
            DiscoveryInformationMemento memento = getDiscoveryInformationMomento(discovered);

            OpenIdState openIdState = new OpenIdState(consumerManager, memento);
            OpenIdCache.INSTANCE.putOpenIdState(sessionToken, openIdState);

            AuthRequest authRequest = getAuthRequest(consumerManager, discovered, returnToUrl, identifier);
            return Response.seeOther(URI.create(authRequest.getDestinationUrl(true))).build();
        } catch (DiscoveryException | MessageException | ConsumerException e) {
            throw Throwables.propagate(e);
        }

    }

    private DiscoveryInformationMemento getDiscoveryInformationMomento(DiscoveryInformation discovered) {
        String claimedIdentifier = null;
        if (discovered.getClaimedIdentifier() != null) {
            claimedIdentifier = discovered.getClaimedIdentifier().getIdentifier();
        }
        String delegate = discovered.getDelegateIdentifier();
        String opEndpoint = null;
        if (discovered.getOPEndpoint() != null) {
            opEndpoint = discovered.getOPEndpoint().toString();
        }
        @SuppressWarnings("unchecked")
        Set<String> types = (Set<String>) discovered.getTypes();
        String version = discovered.getVersion();
        return new DiscoveryInformationMemento(opEndpoint, claimedIdentifier, delegate, version, types);
    }

    private static String getReturnToUrl(
            HttpServletRequest request,
            UUID sessionToken) {
        if (request.getServerPort() == 80) {
            return String.format("http://%s/api/openid/verify?token=%s",
                    request.getServerName(),
                    sessionToken);
        } else {
            return String.format("http://%s:%d/api/openid/verify?token=%s",
                    request.getServerName(),
                    request.getServerPort(),
                    sessionToken);
        }
    }

    private AuthRequest getAuthRequest(
            ConsumerManager consumerManager,
            DiscoveryInformation discovered,
            String returnToUrl,
            String identifier) throws MessageException, ConsumerException {
        AuthRequest authRequest = consumerManager.authenticate(discovered, returnToUrl);
        FetchRequest fetch = FetchRequest.createFetchRequest();
        if (identifier.startsWith(GOOGLE_ENDPOINT)) {
            fetch.addAttribute("email", "http://axschema.org/contact/email", true);
            fetch.addAttribute("firstName", "http://axschema.org/namePerson/first", true);
            fetch.addAttribute("lastName", "http://axschema.org/namePerson/last", true);
        } else if (identifier.startsWith(YAHOO_ENDPOINT)) {
            fetch.addAttribute("email", "http://axschema.org/contact/email", true);
            fetch.addAttribute("fullname", "http://axschema.org/namePerson", true);
        } else { // works for myOpenID
            fetch.addAttribute("fullname", "http://schema.openid.net/namePerson", true);
            fetch.addAttribute("email", "http://schema.openid.net/contact/email", true);
        }
        authRequest.addExtension(fetch);
        return authRequest;
    }

    @GET
    @Path("/verify")
    public Response verifyOpenIdServerResponse(
            @Context HttpServletRequest request,
            @QueryParam("token") String rawToken) {
        if (rawToken == null) {
            log.debug("Authentication failed due to no session token");
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        UUID sessionToken = UUID.fromString(rawToken);
        Optional<OpenIdState> openIdState = OpenIdCache.INSTANCE.getOpenIdState(sessionToken);
        if (!openIdState.isPresent()) {
            log.debug("Authentication failed due to no openIdState matching session token {}", rawToken);
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        DiscoveryInformationMemento memento = openIdState.get().getDiscoveryInformationMemento();
        DiscoveryInformation discovered = getDiscoveryInformation(memento);
        VerificationResult verification = verify(request, openIdState.get().getConsumerManager(), discovered);
        if (verification.getVerifiedId() != null) {
            OpenIdCache.INSTANCE.invalidate(sessionToken);

            String identifier = verification.getVerifiedId().getIdentifier();

            AuthSuccess authSuccess = (AuthSuccess) verification.getAuthResponse();
            if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX)) {
                String email = getStuff(authSuccess, "email");
                String firstName = getStuff(authSuccess, "firstName");
                String lastName = getStuff(authSuccess, "lastName");
                UserCache.INSTANCE.putUser(sessionToken, new User(0, email, firstName, lastName));
            }

            return Response
                    .seeOther(URI.create("http://localhost:8080"))
                    .cookie(getCookie(rawToken))
                    .build();
        } else {
            throw new WebApplicationException();
        }
    }

    private static NewCookie getCookie(@Nullable String sessionToken) {
        if (sessionToken != null) {
            return new NewCookie(
                    OpenIdConstants.SESSION_TOKEN_KEY,
                    sessionToken,
                    "/",
                    null,
                    null,
                    86400 * 30, // 30 days
                    false);

        } else {
            return new NewCookie(OpenIdConstants.SESSION_TOKEN_KEY, null, null, null, null, 0, false);
        }
    }

    private static String getStuff(AuthSuccess authSuccess, String field) {
        try {
            FetchResponse response = (FetchResponse) authSuccess.getExtension(AxMessage.OPENID_NS_AX);
            String value = response.getAttributeValue(field);
            if (value != null) {
                return value;
            } else {
                throw new IllegalArgumentException("Field not provided: " + field);
            }
        } catch (MessageException e) {
            throw Throwables.propagate(e);
        }
    }

    private static DiscoveryInformation getDiscoveryInformation(
            final DiscoveryInformationMemento memento) {
        Identifier identifier = new Identifier() {
            private static final long serialVersionUID = 1L;
            @Override
            public String getIdentifier() {
                return memento.getClaimedIdentifier();
            }
        };
        try {
            return new DiscoveryInformation(
                    URI.create(memento.getOpEndpoint()).toURL(),
                    identifier,
                    memento.getDelegate(),
                    memento.getVersion(),
                    memento.getTypes());
        } catch (DiscoveryException | MalformedURLException e) {
            throw Throwables.propagate(e);
        }
    }

    private static VerificationResult verify(
            HttpServletRequest request,
            ConsumerManager consumerManager,
            DiscoveryInformation discovered)  {
        StringBuffer receivingURL = request.getRequestURL();
        String queryString = request.getQueryString();
        if (queryString != null && queryString.length() > 0) {
            receivingURL.append("?").append(request.getQueryString());
        }
        ParameterList parameterList = new ParameterList(request.getParameterMap());
        try {
            return consumerManager.verify(
                    receivingURL.toString(),
                    parameterList,
                    discovered);
        } catch (MessageException | DiscoveryException | AssociationException e) {
            throw Throwables.propagate(e);
        }
    }

}
