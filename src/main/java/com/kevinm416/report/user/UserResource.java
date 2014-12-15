package com.kevinm416.report.user;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.kevinm416.report.auth.Auth;
import com.kevinm416.report.auth.HashedPassword;
import com.kevinm416.report.auth.PasswordHasher;
import com.kevinm416.report.user.api.ChangePasswordForm;
import com.kevinm416.report.user.api.CreateUserForm;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserDAO userDao;

    public UserResource(
            UserDAO residentCoordinatorDao) {
        this.userDao = residentCoordinatorDao;
    }

    @GET
    @Timed
    public List<User> loadUsers(@Auth User user) {
        return userDao.loadUsers();
    }

    @POST
    @Timed
    public long createUser(
            @Auth User user,
            @Valid CreateUserForm form) {
        return userDao.createUser(form);
    }

    @DELETE
    @Timed
    @Path("/{userId}")
    public void delete(
            @Auth User user,
            @PathParam("userId") long userId) {
        userDao.deleteUser(userId);
    }

    @GET
    @Timed
    @Path("/whoAmI")
    public User whoAmI(@Auth User user) {
        return user;
    }

    @POST
    @Timed
    @Path("/changePassword")
    public void changePassword(
            @Auth User user,
            @Valid ChangePasswordForm changePasswordForm) {
        HashedPassword hashedPassword = PasswordHasher.saltAndHashPassword(changePasswordForm.getPassword());
        userDao.updatePassword(
                user.getId(),
                hashedPassword.getHashedPassword(),
                hashedPassword.getSalt());
    }

}
