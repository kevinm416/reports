package com.kevinm416.report.shiftreport.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class ShiftReportDBMapper implements ResultSetMapper<ShiftReport> {

    @Override
    public ShiftReport map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new ShiftReport(
                r.getLong("id"),
                r.getLong("house_id"),
                r.getLong("date"),
                r.getLong("created_by"),
                r.getString("shift"),
                r.getLong("time_created"),
                r.getBoolean("keys_accounted_for"),
                r.getString("keys_accounted_for_reason"));
    }

}
