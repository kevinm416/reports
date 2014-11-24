package com.kevinm416.report.rc;

import io.dropwizard.auth.Auth;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.kevinm416.report.server.User;

@Path("/residentCoordinators")
@Produces(MediaType.APPLICATION_JSON)
public class ResidentCoordinatorResource {

    private final ResidentCoordinatorDAO residentCoordinatorDao;

    public ResidentCoordinatorResource(
            ResidentCoordinatorDAO residentCoordinatorDao) {
        this.residentCoordinatorDao = residentCoordinatorDao;
    }

    @GET
    public List<ResidentCoordinator> loadResidentCoordinators(@Auth User user) {
        return residentCoordinatorDao.loadResidentCoordinators();
    }

    @POST
    public long createResidentCoordinator(@Auth User user, CreateResidentCoordinatorForm form) {
        return residentCoordinatorDao.createResidentCoordinator(form);
    }

}
