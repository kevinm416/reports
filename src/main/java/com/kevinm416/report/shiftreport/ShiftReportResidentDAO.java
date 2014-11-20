package com.kevinm416.report.shiftreport;

import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlBatch;

public interface ShiftReportResidentDAO {

    @SqlBatch(
            " INSERT INTO shift_report_residents ( " +
            "     shift_report_id, " +
            "     resident_id, " +
            "     time_created, " +
            "     summary, " +
            "     notes," +
            " ) " +
            " VALUES (" +
            "     :shiftReportId, " +
            "     :residentId, " +
            "     :timeCreated, " +
            "     :summary, " +
            "     :notes " +
            " )"
    )
    void createShiftReportResidents(@BindBean Iterable<CreateShiftReportResident> createShiftReportResident);


}
