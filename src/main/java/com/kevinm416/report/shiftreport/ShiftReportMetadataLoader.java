package com.kevinm416.report.shiftreport;

import java.util.Map;

import org.skife.jdbi.v2.Handle;

import com.google.common.collect.Iterables;
import com.kevinm416.report.common.cache.IdCache;
import com.kevinm416.report.user.User;

public class ShiftReportMetadataLoader {

    private final Handle h;
    private final IdCache<User> residentCoordinatorCache;

    public ShiftReportMetadataLoader(
            Handle h,
            IdCache<User> residentCoordinatorCache) {
        this.h = h;
        this.residentCoordinatorCache = residentCoordinatorCache;
    }

    public ShiftReportMetadata loadMetadata(final long shiftReportId) {
        Map<String, Object> row = Iterables.getOnlyElement(
                h.select(LOAD_SHIFT_REPORT_METADATA_SQL, shiftReportId));

        long createdById = (long) row.get("created_by");

        User residentCoordinator =
                residentCoordinatorCache.loadById(h, createdById);

        return new ShiftReportMetadata(
                (long) row.get("id"),
                (long) row.get("date"),
                residentCoordinator.getName(),
                (String) row.get("shift"),
                (long) row.get("time_created"));
    }

    private static final String LOAD_SHIFT_REPORT_METADATA_SQL =
            " SELECT id, " +
            "        date, " +
            "        shift, " +
            "        time_created, " +
            "        created_by " +
            "   FROM shift_reports " +
            "  WHERE id = ? ";
}
