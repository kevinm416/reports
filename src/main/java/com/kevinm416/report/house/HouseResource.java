package com.kevinm416.report.house;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.kevinm416.report.auth.ShiroAuth;
import com.kevinm416.report.house.api.CreateHouseForm;
import com.kevinm416.report.user.User;

@Path("/houses")
@Produces(MediaType.APPLICATION_JSON)
public class HouseResource {

    private final HouseDAO houseDAO;

    public HouseResource(HouseDAO houseDAO) {
        this.houseDAO = houseDAO;
    }

    @GET
    @Timed
    public List<House> loadHouses(@ShiroAuth User user) {
        return houseDAO.loadHouses();
    }

    @POST
    @Timed
    public long createHouse(@ShiroAuth User user, @Valid CreateHouseForm form) {
        return houseDAO.createHouse(form);
    }

}
