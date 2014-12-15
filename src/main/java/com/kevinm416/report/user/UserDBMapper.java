package com.kevinm416.report.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class UserDBMapper implements ResultSetMapper<User> {

    @Override
    public User map(int index, ResultSet r, StatementContext ctx)
            throws SQLException {
        return new User(
                r.getLong("id"),
                r.getString("name"),
                r.getBoolean("admin"));
    }

}
