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

    @SqlUpdate(
            " UPDATE users " +
            " SET deleted = TRUE " +
            " WHERE id = :userId "
    )
    void deleteUser(@Bind("userId") long userId);

    @SqlQuery(
            " SELECT * FROM users " +
            " WHERE name = :name "
    )
    User loadUserByName(@Bind("name") String name);

    @SqlQuery(
            " SELECT * FROM users " +
            " WHERE id = :id "
    )
    User loadUserById(@Bind("id") long id);

    @SqlQuery(
            " SELECT * " +
            " FROM users " +
            " WHERE deleted = FALSE " +
            " ORDER BY name ASC "
    )
    List<User> loadUsers();

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
