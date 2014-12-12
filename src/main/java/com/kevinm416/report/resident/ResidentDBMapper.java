package com.kevinm416.report.resident;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.kevinm416.report.resident.api.Resident;

public class ResidentDBMapper implements ResultSetMapper<Resident> {

    @Override
    public Resident map(int index, ResultSet r, StatementContext ctx)
            throws SQLException {
        return new Resident(
                r.getLong("id"),
                r.getString("name"),
                r.getLong("birthdate"),
                r.getLong("house_id"));
    }

}
