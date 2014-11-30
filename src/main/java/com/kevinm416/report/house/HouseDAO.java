package com.kevinm416.report.house;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import com.kevinm416.report.house.api.CreateHouseForm;

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

    @SqlQuery(
            " SELECT * " +
            " FROM houses " +
            " WHERE id = :id "
    )
    House loadHouse(@Bind("id") long id);

}
