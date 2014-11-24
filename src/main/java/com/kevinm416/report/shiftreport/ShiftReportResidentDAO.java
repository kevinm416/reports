package com.kevinm416.report.shiftreport;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;

public interface ShiftReportResidentDAO {

    @SqlBatch(
            " INSERT INTO shift_report_residents ( " +
            "     shift_report_id, " +
            "     resident_id, " +
            "     time_created, " +
            "     summary, " +
            "     notes " +
            " ) " +
            " VALUES (" +
            "     :shiftReportId, " +
            "     :c.residentId, " +
            "     :timeCreated, " +
            "     :c.summary, " +
            "     :c.notes " +
            " )"
    )
    void createShiftReportResidents(
            @Bind("shiftReportId") long shiftReportId,
            @Bind("timeCreated") long timeCreated,
            @BindBean("c") Iterable<CreateShiftReportResident> createShiftReportResident);

    @SqlQuery(
            " SELECT * FROM shift_report_residents " +
            " WHERE resident_id = :residentId " +
            " ORDER BY time_created DESC "
    )
    List<ShiftReportResident> loadShiftReportResidents(@Bind("residentId") long residentId);

}
