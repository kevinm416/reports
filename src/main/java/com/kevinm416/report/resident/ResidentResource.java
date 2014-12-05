package com.kevinm416.report.resident;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.Validate;

import com.codahale.metrics.annotation.Timed;
import com.kevinm416.report.auth.ShiroAuth;
import com.kevinm416.report.common.cache.IdCache;
import com.kevinm416.report.resident.api.CreateResidentForm;
import com.kevinm416.report.user.User;

@Path("/residents")
@Produces(MediaType.APPLICATION_JSON)
public class ResidentResource {

    private final ResidentDAO residentDAO;
    private final IdCache<Resident> residentCache;

    public ResidentResource(ResidentDAO residentDAO,
            IdCache<Resident> residentCache) {
        this.residentDAO = residentDAO;
        this.residentCache = residentCache;
    }

    @GET
    @Timed
    public List<Resident> loadResidents(@ShiroAuth User user) {
        return residentDAO.loadResidents();
    }

    @POST
    @Timed
    public long createResident(@ShiroAuth User user, CreateResidentForm form) {
        return residentDAO.createResident(form);
    }

    @PUT
    @Timed
    @Path("/{id}")
    public void updateResident(
            @ShiroAuth User user,
            @PathParam("id") long id,
            Resident resident) {
        Validate.isTrue(id == resident.getId());
        residentDAO.updateResident(resident);
        residentCache.invalidate(resident.getId());
    }

    @GET
    @Timed
    @Path("/{id}")
    public Resident loadResident(@ShiroAuth User user, @PathParam("id") long id) {
        return residentDAO.loadResident(id);
    }

    @DELETE
    @Timed
    @Path("/{id}")
    public void deleteResident(@ShiroAuth User user, @PathParam("id") long id) {
        residentDAO.deleteResident(id);
    }

}
