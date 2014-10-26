package com.kevinm416.report.house;

import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;

public interface HouseDAO {

    @SqlQuery(
            " INSERT INTO houses (name)" +
            " VALUES (:name) " +
            " RETURNING id "
    )
    long createHouse(@BindBean CreateHouseForm form);

}
