package com.kevinm416.report.shiftreport;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlBatch;

public interface ShiftReportOnShiftDAO {

    @SqlBatch(
            " INSERT INTO shift_report_on_shift (shift_report_id, resident_coordinator_id) " +
            " VALUES (:shiftReportId, :residentCoordinatorId) "
    )
    void createOnShiftRecords(
            @Bind("shiftReportId") long shiftReportId,
            @Bind("residentCoordinatorId") Iterable<Long> residentCoordinatorIds);

}
