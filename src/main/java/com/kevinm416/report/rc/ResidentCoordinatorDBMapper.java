package com.kevinm416.report.rc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class ResidentCoordinatorDBMapper implements ResultSetMapper<ResidentCoordinator> {

    @Override
    public ResidentCoordinator map(int index, ResultSet r, StatementContext ctx)
            throws SQLException {
        return new ResidentCoordinator(r.getLong("id"), r.getString("name"));
    }

}
