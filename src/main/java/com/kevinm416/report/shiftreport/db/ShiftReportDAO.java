package com.kevinm416.report.shiftreport.db;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import com.kevinm416.report.shiftreport.CreateShiftReport;

@RegisterMapper(ShiftReportDBMapper.class)
public interface ShiftReportDAO {

    @SqlQuery(
            " INSERT INTO shift_reports ( " +
            "     house_id, " +
            "     date, " +
            "     created_by, " +
            "     shift, " +
            "     time_created, " +
            "     keys_accounted_for, " +
            "     keys_accounted_for_reason " +
            " ) " +
            " VALUES ( " +
            "     :houseId, " +
            "     :date, " +
            "     :createdBy, " +
            "     :shift, " +
            "     :timeCreated, " +
            "     :keysAccountedFor, " +
            "     :keysAccountedForReason " +
            " ) " +
            " RETURNING id "
    )
    long createShiftReport(
            @Bind("createdBy") long userId,
            @BindBean CreateShiftReport createShiftReport);

    @SqlQuery(
            " SELECT * FROM shift_reports " +
            " WHERE id = :shiftReportId "
    )
    ShiftReport loadShiftReport(@Bind("shiftReportId") long shiftReportId);

}