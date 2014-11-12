package com.kevinm416.report.rc;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;

public interface ResidentCoordinatorDAO {

    @SqlQuery(
            " INSERT INTO resident_coordinator (name) " +
            " VALUES (:name) " +
            " RETURNING id "
    )
    long createResidentCoordinator(@BindBean CreateResidentCoordinatorForm form);

    @SqlQuery(
            " SELECT * " +
            " FROM resident_coordinators " +
            " ORDER BY name ASC "
    )
    List<ResidentCoordinator> loadResidentCoordinators();

}
