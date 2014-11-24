package com.kevinm416.report.rc;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(ResidentCoordinatorDBMapper.class)
public interface ResidentCoordinatorDAO {

    @SqlQuery(
            " INSERT INTO resident_coordinators (name) " +
            " VALUES (:name) " +
            " RETURNING id "
    )
    long createResidentCoordinator(@BindBean CreateResidentCoordinatorForm form);

    @SqlQuery(
            " SELECT * from resident_coordinators " +
            " WHERE name = :name "
    )
    ResidentCoordinator loadResidentCoordinatorByName(@Bind("name") String name);

    @SqlQuery(
            " SELECT * " +
            " FROM resident_coordinators " +
            " ORDER BY name ASC "
    )
    List<ResidentCoordinator> loadResidentCoordinators();

}
