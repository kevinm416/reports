package com.kevinm416.report.shiftreport;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class ShiftReportResidentDBMapper implements ResultSetMapper<ShiftReportResident> {

    @Override
    public ShiftReportResident map(int index, ResultSet r, StatementContext ctx)
            throws SQLException {
        return new ShiftReportResident(
                r.getLong("id"),
                r.getLong("shift_report_id"),
                r.getLong("resident_id"),
                r.getString("summary"),
                r.getString("notes"));
    }

}
