package com.kevinm416.report.house;

import io.dropwizard.auth.Auth;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.kevinm416.report.house.api.CreateHouseForm;
import com.kevinm416.report.rc.ResidentCoordinator;

@Path("/houses")
@Produces(MediaType.APPLICATION_JSON)
public class HouseResource {

    private final HouseDAO houseDAO;

    public HouseResource(HouseDAO houseDAO) {
        this.houseDAO = houseDAO;
    }

    @GET
    public List<House> loadHouses(@Auth ResidentCoordinator user) {
        return houseDAO.loadHouses();
    }

    @POST
    public long createHouse(@Auth ResidentCoordinator user, CreateHouseForm form) {
        return houseDAO.createHouse(form);
    }

}
