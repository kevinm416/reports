package com.kevinm416.report.user;

import org.h2.command.ddl.CreateUser;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(UserDBMapper.class)
public interface UserDAO {

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
    public User loadUserByEmail(@Bind String email);

}
