package com.kevinm416.report.resident;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(ResidentDBMapper.class)
public interface ResidentDAO {

    @SqlQuery(
            " INSERT INTO residents (name, birthdate, house_id) " +
            " VALUES (:name, :birthdate, :houseId) " +
            " RETURNING id "
    )
    long createUser(@BindBean CreateResidentForm form);

    @SqlUpdate(
            " UPDATE residents " +
            " SET name = :name, " +
            "     birthdate = :birthdate, " +
            "     house_id = :houseId " +
            " WHERE id = :id "
    )
    void updateResident(@BindBean Resident resident);

    @SqlQuery(
            " SELECT * " +
            " FROM residents " +
            " WHERE id = :id "
    )
    Resident loadResident(@Bind("id") long id);

    @SqlQuery(
            " SELECT * " +
            " FROM residents " +
            " ORDER BY name ASC "
    )
    List<Resident> loadResidents();

}
