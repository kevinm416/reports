package com.kevinm416.report.shiftreport;

import java.util.List;

import org.skife.jdbi.v2.Handle;

import com.google.common.collect.Lists;
import com.kevinm416.report.common.cache.IdCache;
import com.kevinm416.report.user.User;

public class LoadShiftReportResidentWithMetadata {

    private final Handle h;
    private final IdCache<User> residentCoordinatorCache;

    public LoadShiftReportResidentWithMetadata(Handle h,
            IdCache<User> residentCoordinatorCache) {
        this.h = h;
        this.residentCoordinatorCache = residentCoordinatorCache;
    }

    public List<ShiftReportResidentWithMetadata> loadShiftReportResidentsWithMetadata(
            long residentId,
            int pageSize) {
        ShiftReportResidentDAO shiftReportResidentDAO = getShiftReportResidentDAO();
        List<ShiftReportResident> shiftReportResidents =
                shiftReportResidentDAO.loadShiftReportResidents(residentId, pageSize);
        return loadMetadataForResidents(shiftReportResidents);
    }

    public List<ShiftReportResidentWithMetadata> loadShiftReportResidentsWithMetadataPage(
            long residentId,
            int pageSize,
            Long lastShiftReportResidentId) {
        ShiftReportResidentDAO shiftReportResidentDao = getShiftReportResidentDAO();
        List<ShiftReportResident> shiftReportResidents;
        if (lastShiftReportResidentId == null) {
            shiftReportResidents = shiftReportResidentDao.loadShiftReportResidents(residentId, pageSize);
        } else {
            shiftReportResidents = shiftReportResidentDao.loadShiftReportResidentPage(
                    residentId,
                    pageSize,
                    lastShiftReportResidentId.longValue());
        }
        return loadMetadataForResidents(shiftReportResidents);
    }

    private List<ShiftReportResidentWithMetadata> loadMetadataForResidents(
            List<ShiftReportResident> shiftReportResidents) {
        ShiftReportMetadataLoader loadShiftReportMetadata =
                new ShiftReportMetadataLoader(h, residentCoordinatorCache);
        List<ShiftReportResidentWithMetadata> ret = Lists.newArrayListWithCapacity(shiftReportResidents.size());
        for (ShiftReportResident shiftReportResident : shiftReportResidents) {
            ShiftReportMetadata metadata = loadShiftReportMetadata.loadMetadata(shiftReportResident.getShiftReportId());
            ret.add(new ShiftReportResidentWithMetadata(shiftReportResident, metadata));
        }
        return ret;
    }

    private ShiftReportResidentDAO getShiftReportResidentDAO() {
        return h.attach(ShiftReportResidentDAO.class);
    }

}
