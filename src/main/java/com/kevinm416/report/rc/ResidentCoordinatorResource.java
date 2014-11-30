package com.kevinm416.report.rc;

import io.dropwizard.auth.Auth;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.kevinm416.report.rc.api.CreateResidentCoordinatorForm;

@Path("/residentCoordinators")
@Produces(MediaType.APPLICATION_JSON)
public class ResidentCoordinatorResource {

    private final ResidentCoordinatorDAO residentCoordinatorDao;

    public ResidentCoordinatorResource(
            ResidentCoordinatorDAO residentCoordinatorDao) {
        this.residentCoordinatorDao = residentCoordinatorDao;
    }

    @GET
    @Timed
    public List<ResidentCoordinator> loadResidentCoordinators(@Auth ResidentCoordinator user) {
        return residentCoordinatorDao.loadResidentCoordinators();
    }

    @POST
    @Timed
    public long createResidentCoordinator(
            @Auth ResidentCoordinator user,
            @Valid CreateResidentCoordinatorForm form) {
        return residentCoordinatorDao.createResidentCoordinator(form);
    }

}
