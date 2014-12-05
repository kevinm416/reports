package com.kevinm416.report.user;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.kevinm416.report.auth.ShiroAuth;
import com.kevinm416.report.user.api.CreateUserForm;

@Path("/residentCoordinators")
@Produces(MediaType.APPLICATION_JSON)
public class ResidentCoordinatorResource {

    private final UserDAO residentCoordinatorDao;

    public ResidentCoordinatorResource(
            UserDAO residentCoordinatorDao) {
        this.residentCoordinatorDao = residentCoordinatorDao;
    }

    @GET
    @Timed
    public List<User> loadResidentCoordinators(@ShiroAuth User user) {
        return residentCoordinatorDao.loadResidentCoordinators();
    }

    @POST
    @Timed
    public long createResidentCoordinator(
            @ShiroAuth User user,
            @Valid CreateUserForm form) {
        return residentCoordinatorDao.createResidentCoordinator(form);
    }

}
