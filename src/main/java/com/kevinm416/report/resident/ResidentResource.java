package com.kevinm416.report.resident;

import io.dropwizard.auth.Auth;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.kevinm416.report.rc.ResidentCoordinator;

@Path("/residents")
@Produces(MediaType.APPLICATION_JSON)
public class ResidentResource {

    private final ResidentDAO residentDAO;

    public ResidentResource(ResidentDAO residentDAO) {
        this.residentDAO = residentDAO;
    }

    @GET
    public List<Resident> loadResidents(@Auth ResidentCoordinator user) {
        return residentDAO.loadResidents();
    }

    @POST
    public long createResident(@Auth ResidentCoordinator user, CreateResidentForm form) {
        return residentDAO.createUser(form);
    }

    @PUT
    @Path("/{id}")
    public void updateResident(@Auth ResidentCoordinator user, Resident resident) {
        residentDAO.updateResident(resident);
    }

    @GET
    @Path("/{id}")
    public Resident loadResident(@Auth ResidentCoordinator user, @PathParam("id") long id) {
        return residentDAO.loadResident(id);
    }

}
