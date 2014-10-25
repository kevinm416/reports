package com.kevinm416.report.resident;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(ResidentDBMapper.class)
public interface ResidentDAO {

    @SqlQuery(
            " INSERT INTO residents (name, birthdate, house_id) " +
            " VALUES (:name, :birthdate, :houseId) " +
            " RETURNING id "
    )
    long createUser(
            @Bind("name") String name,
            @Bind("birthdate") long birthdate,
            @Bind("houseId") long houseId);

    @SqlQuery(
            " SELECT * " +
            " FROM residents " +
            " WHERE id = :id "
    )
    Resident loadResident(@Bind("id") long id);

}
