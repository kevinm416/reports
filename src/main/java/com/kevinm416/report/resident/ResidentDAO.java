package com.kevinm416.report.resident;

import java.util.Set;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(ResidentDBMapper.class)
public interface ResidentDAO {

    @SqlQuery(
            " INSERT INTO residents (name, birthdate, house_id) " +
            " VALUES (:name, :birthdate, :houseId) " +
            " RETURNING id "
    )
    long createUser(@BindBean CreateResidentForm form);

    @SqlQuery(
            " SELECT * " +
            " FROM residents " +
            " WHERE id = :id "
    )
    Resident loadResident(@Bind("id") long id);

    @SqlQuery(
            " SELECT * " +
            " FROM residents "
    )
    Set<Resident> loadResidents();

}
