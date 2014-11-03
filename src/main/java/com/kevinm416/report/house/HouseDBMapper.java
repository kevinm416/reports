package com.kevinm416.report.house;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class HouseDBMapper implements ResultSetMapper<House> {

    @Override
    public House map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new House(
                r.getLong("id"),
                r.getString("name"));
    }

}
