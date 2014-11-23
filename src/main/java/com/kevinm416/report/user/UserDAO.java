package com.kevinm416.report.user;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(UserDBMapper.class)
public interface UserDAO {

    @SqlQuery(
            " UPDATE users " +
            "    SET first_name = :firstName, " +
            "        last_name = :lastName " +
            "  WHERE email = :email " +
            " ; " +
            " INSERT INTO users (email, first_name, last_name) " +
            " SELECT :email, :firstName, :lastName " +
            " WHERE NOT EXISTS (SELECT 1 from users WHERE email = :email) " +
            " ; " +
            " SELECT id FROM users WHERE email = :email "
    )
    public long createOrUpdateUser(@BindBean CreateUser createUser);

    @SqlQuery(
            " INSERT INTO users (email, first_name, last_name) " +
            " VALUES (:email, :firstName, :lastName) " +
            " RETURNING id "
    )
    public long createUser(@BindBean CreateUser createUser);


    @SqlQuery(
            " SELECT * " +
            " FROM users " +
            " WHERE email = :email "
    )
    public User loadUserByEmail(@Bind("email") String email);

}
