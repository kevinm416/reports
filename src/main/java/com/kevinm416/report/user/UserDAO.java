package com.kevinm416.report.user;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import com.kevinm416.report.auth.UserWithAuthInfo;
import com.kevinm416.report.user.api.CreateUserForm;

@RegisterMapper({
    UserDBMapper.class,
    UserWithAuthInfoDBMapper.class
})
public interface UserDAO {

    @SqlQuery(
            " INSERT INTO users (name) " +
            " VALUES (:name) " +
            " RETURNING id "
    )
    long createUser(@BindBean CreateUserForm form);

    @SqlQuery(
            " SELECT * FROM users " +
            " WHERE name = :name "
    )
    User loadResidentCoordinatorByName(@Bind("name") String name);

    @SqlQuery(
            " SELECT * FROM users " +
            " WHERE id = :id "
    )
    User loadResidentCoordinatorById(@Bind("id") long id);

    @SqlQuery(
            " SELECT * " +
            " FROM users " +
            " ORDER BY name ASC "
    )
    List<User> loadResidentCoordinators();

    @SqlQuery(
            " SELECT * " +
            " FROM users " +
            " WHERE name = :name "
    )
    UserWithAuthInfo loadAuthenticationInformation(
            @Bind("name") String name);

    @SqlUpdate(
            " UPDATE users " +
            " SET pw_hash = :newPasswordHash, " +
            "     salt = :salt " +
            " WHERE id = :userId "
    )
    void updatePassword(
            @Bind("userId") long userId,
            @Bind("newPasswordHash") String newPasswordHash,
            @Bind("salt") String salt);

}
