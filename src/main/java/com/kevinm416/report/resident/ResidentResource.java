package com.kevinm416.report.resident;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.kevinm416.report.openid.RestrictedTo;
import com.kevinm416.report.user.User;

@Path("/residents")
@Produces(MediaType.APPLICATION_JSON)
public class ResidentResource {

    private final ResidentDAO residentDAO;

    public ResidentResource(ResidentDAO residentDAO) {
        this.residentDAO = residentDAO;
    }

    @GET
    public List<Resident> loadResidents(@RestrictedTo User user) {
        return residentDAO.loadResidents();
    }

    @POST
    public long createResident(@RestrictedTo User user, CreateResidentForm form) {
        return residentDAO.createUser(form);
    }

    @PUT
    @Path("/{id}")
    public void updateResident(@RestrictedTo User user, Resident resident) {
        residentDAO.updateResident(resident);
    }

    @GET
    @Path("/{id}")
    public Resident loadResident(@RestrictedTo User user, @PathParam("id") long id) {
        return residentDAO.loadResident(id);
    }

}
