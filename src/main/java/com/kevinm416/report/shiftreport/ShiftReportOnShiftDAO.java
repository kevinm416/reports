package com.kevinm416.report.shiftreport;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;

public interface ShiftReportOnShiftDAO {

    @SqlBatch(
            " INSERT INTO shift_report_on_shift (shift_report_id, resident_coordinator_id) " +
            " VALUES (:shiftReportId, :residentCoordinatorId) "
    )
    void createOnShiftRecords(
            @Bind("shiftReportId") long shiftReportId,
            @Bind("residentCoordinatorId") Iterable<Long> residentCoordinatorIds);

    @SqlQuery(
            " SELECT resident_coordinator_id " +
            " FROM shift_report_on_shift " +
            " WHERE shift_report_id = :shiftReportId "
    )
    List<Long> loadOnShiftForShiftReport(@Bind("shiftReportId") long shiftReportId);

}
