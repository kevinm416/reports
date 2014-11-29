package com.kevinm416.report.shiftreport;

import java.util.List;

import org.skife.jdbi.v2.Handle;

import com.google.common.collect.Lists;
import com.kevinm416.report.rc.ResidentCoordinatorCache;

public class LoadShiftReportResidentWithMetadata {

    private final Handle h;
    private final ResidentCoordinatorCache residentCoordinatorCache;

    public LoadShiftReportResidentWithMetadata(Handle h,
            ResidentCoordinatorCache residentCoordinatorCache) {
        this.h = h;
        this.residentCoordinatorCache = residentCoordinatorCache;
    }

    public List<ShiftReportResidentWithMetadata> loadShiftReportResidentsWithMetadata(long residentId) {
        ShiftReportResidentDAO shiftReportResidentDAO = h.attach(ShiftReportResidentDAO.class);
        List<ShiftReportResident> shiftReportResidents = shiftReportResidentDAO.loadShiftReportResidents(residentId);
        return loadMetadataForResidents(shiftReportResidents);
    }

    private List<ShiftReportResidentWithMetadata> loadMetadataForResidents(
            List<ShiftReportResident> shiftReportResidents) {
        LoadShiftReportMetadata loadShiftReportMetadata =
                new LoadShiftReportMetadata(h, residentCoordinatorCache);
        List<ShiftReportResidentWithMetadata> ret = Lists.newArrayListWithCapacity(shiftReportResidents.size());
        for (ShiftReportResident shiftReportResident : shiftReportResidents) {
            ShiftReportMetadata metadata = loadShiftReportMetadata.loadMetadata(shiftReportResident.getShiftReportId());
            ret.add(new ShiftReportResidentWithMetadata(shiftReportResident, metadata));
        }
        return ret;
    }

}
