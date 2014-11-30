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

import com.codahale.metrics.annotation.Timed;
import com.kevinm416.report.common.cache.IdCache;
import com.kevinm416.report.rc.ResidentCoordinator;
import com.kevinm416.report.resident.api.CreateResidentForm;

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
    public List<Resident> loadResidents(@Auth ResidentCoordinator user) {
        return residentDAO.loadResidents();
    }

    @POST
    @Timed
    public long createResident(@Auth ResidentCoordinator user, CreateResidentForm form) {
        return residentDAO.createResident(form);
    }

    @PUT
    @Timed
    @Path("/{id}")
    public void updateResident(@Auth ResidentCoordinator user, Resident resident) {
        residentDAO.updateResident(resident);
        residentCache.invalidate(resident.getId());
    }

    @GET
    @Timed
    @Path("/{id}")
    public Resident loadResident(@Auth ResidentCoordinator user, @PathParam("id") long id) {
        return residentDAO.loadResident(id);
    }

}
