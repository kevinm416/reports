package com.kevinm416.report.shiftreport;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(ShiftReportResidentDBMapper.class)
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
            "     :residentId, " +
            "     :timeCreated, " +
            "     :summary, " +
            "     :notes " +
            " )"
    )
    void createShiftReportResidents(
            @Bind("shiftReportId") long shiftReportId,
            @Bind("timeCreated") long timeCreated,
            @BindBean Iterable<CreateShiftReportResident> createShiftReportResident);

    @SqlQuery(
            " SELECT * FROM shift_report_residents " +
            " WHERE resident_id = :residentId " +
            " ORDER BY time_created DESC " +
            " LIMIT :pageSize "
    )
    List<ShiftReportResident> loadShiftReportResidents(
            @Bind("residentId") long residentId,
            @Bind("pageSize") int pageSize);

    @SqlQuery(
            " SELECT * FROM shift_report_residents " +
            " WHERE resident_id = :residentId " +
            "   AND id < :lastShiftReportResidentId " +
            " ORDER BY time_created DESC " +
            " LIMIT :pageSize "
    )
    List<ShiftReportResident> loadShiftReportResidentPage(
            @Bind("residentId") long residentId,
            @Bind("pageSize") int pageSize,
            @Bind("lastShiftReportResidentId") long lastShiftReportResidentId);

    @SqlQuery(
            " SELECT * FROM shift_report_residents " +
            " WHERE shift_report_id = :shiftReportId " +
            " ORDER BY time_created DESC "
    )
    List<ShiftReportResident> loadShiftReportResidentsForShiftReport(
            @Bind("shiftReportId") long shiftReportId);

}
