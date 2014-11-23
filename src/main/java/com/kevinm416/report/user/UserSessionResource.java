package com.kevinm416.report.user;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.kevinm416.report.openid.RestrictedTo;

@Path("session")
@Produces(MediaType.APPLICATION_JSON)
public class UserSessionResource {

    @GET
    public boolean validSession(@RestrictedTo UserSession session) {
        return true;
    }

}
