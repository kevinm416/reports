package com.kevinm416.report.shiftreport;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;

public interface ShiftReportDAO {

    @SqlQuery(
            " INSERT INTO shift_reports ( " +
            "     house_id, " +
            "     created_by, " +
            "     on_shift, " +
            "     shift, " +
            "     time_created, " +
            "     keys_accounted_for, " +
            "     keys_accounted_for_reason " +
            " ) " +
            " VALUES ( " +
            "     :houseId, " +
            "     :createdBy, " +
            "     :onShift, " +
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

}
