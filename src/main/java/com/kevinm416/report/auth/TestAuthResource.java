package com.kevinm416.report.auth;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.kevinm416.report.user.User;

@Path("auth")
public class TestAuthResource {

    @GET
    public String getAuth(@ShiroAuth User rc) {
        System.out.println(rc);
        return "hi";
    }

}
