package com.kevinm416.report.test;

import io.dropwizard.auth.Auth;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.kevinm416.report.server.User;

@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
public class TestResouce {

    public TestResouce() {
    }

    @GET
    @Timed
    public Test getTest(
            @Auth User user,
            @QueryParam("name") Optional<String> name) {
        String ret = "hi" + " " + name.or("") + " " + user;
        return new Test(ret);
    }

}
