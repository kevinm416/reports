package com.kevinm416.report.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class UserSessionDBMapper implements ResultSetMapper<UserSession> {

    @Override
    public UserSession map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new UserSession(
                (UUID) r.getObject("id"),
                r.getLong("user_id"));
    }

}
