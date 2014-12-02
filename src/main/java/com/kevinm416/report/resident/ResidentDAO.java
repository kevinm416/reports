package com.kevinm416.report.resident;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import com.kevinm416.report.resident.api.CreateResidentForm;

@RegisterMapper(ResidentDBMapper.class)
public interface ResidentDAO {

    @SqlQuery(
            " INSERT INTO residents ( " +
            "     name, " +
            "     birthdate, " +
            "     house_id, " +
            "     deleted " +
            " ) " +
            " VALUES ( " +
            "     :name, " +
            "     :birthdate, " +
            "     :houseId, " +
            "     FALSE " +
            " ) " +
            " RETURNING id "
    )
    long createResident(@BindBean CreateResidentForm form);

    @SqlUpdate(
            " UPDATE residents " +
            " SET name = :name, " +
            "     birthdate = :birthdate, " +
            "     house_id = :houseId " +
            " WHERE id = :id " +
            "   AND deleted = FALSE "
    )
    void updateResident(@BindBean Resident resident);

    @SqlQuery(
            " SELECT * " +
            " FROM residents " +
            " WHERE id = :id " +
            "   AND deleted = FALSE "
    )
    Resident loadResident(@Bind("id") long id);

    @SqlQuery(
            " SELECT * " +
            " FROM residents " +
            " WHERE id = :id "
    )
    Resident loadResidentIncludeDeleted(@Bind("id") long id);

    @SqlQuery(
            " SELECT * " +
            " FROM residents " +
            " WHERE deleted = FALSE " +
            " ORDER BY name ASC "
    )
    List<Resident> loadResidents();

    @SqlUpdate(
            " UPDATE residents " +
            " SET deleted = TRUE " +
            " WHERE id = :id "
    )
    void deleteResident(@Bind("id") long id);

}
