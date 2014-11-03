package com.kevinm416.report.house;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(HouseDBMapper.class)
public interface HouseDAO {

    @SqlQuery(
            " INSERT INTO houses (name)" +
            " VALUES (:name) " +
            " RETURNING id "
    )
    long createHouse(@BindBean CreateHouseForm form);

    @SqlQuery(
            " SELECT * " +
            " FROM houses " +
            " ORDER BY name ASC "
    )
    List<House> loadHouses();

}
