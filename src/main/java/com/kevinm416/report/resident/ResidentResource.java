package com.kevinm416.report.resident;

import io.dropwizard.auth.Auth;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.kevinm416.report.server.User;

@Path("/residents")
@Produces(MediaType.APPLICATION_JSON)
public class ResidentResource {

    private final ResidentDAO residentDAO;

    public ResidentResource(ResidentDAO residentDAO) {
        this.residentDAO = residentDAO;
    }

    @GET
    public Set<Resident> loadResidents(@Auth User user) {
        return residentDAO.loadResidents();
    }

    @POST
    public long createResident(@Auth User user, CreateResidentForm form) {
        return residentDAO.createUser(form);
    }

    @GET
    @Path("/{id}")
    public Resident loadResident(@Auth User user, @PathParam("id") long id) {
        return residentDAO.loadResident(id);
    }

}
