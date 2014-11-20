package com.kevinm416.report.rc;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.kevinm416.report.openid.RestrictedTo;
import com.kevinm416.report.user.User;

@Path("/residentCoordinators")
@Produces(MediaType.APPLICATION_JSON)
public class ResidentCoordinatorResource {

    private final ResidentCoordinatorDAO residentCoordinatorDao;

    public ResidentCoordinatorResource(
            ResidentCoordinatorDAO residentCoordinatorDao) {
        this.residentCoordinatorDao = residentCoordinatorDao;
    }

    @GET
    public List<ResidentCoordinator> loadResidentCoordinators(@RestrictedTo User user) {
        return residentCoordinatorDao.loadResidentCoordinators();
    }

    @POST
    public long createResidentCoordinator(@RestrictedTo User user, CreateResidentCoordinatorForm form) {
        return residentCoordinatorDao.createResidentCoordinator(form);
    }

}
