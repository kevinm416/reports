package com.kevinm416.report.shiftreport;

import java.util.List;

import org.skife.jdbi.v2.Handle;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.kevinm416.report.common.cache.IdCache;
import com.kevinm416.report.house.House;
import com.kevinm416.report.rc.ResidentCoordinator;
import com.kevinm416.report.resident.Resident;
import com.kevinm416.report.shiftreport.db.ShiftReport;
import com.kevinm416.report.shiftreport.db.ShiftReportDAO;

public class ShiftReportViewLoader {

    private final Handle h;
    private final IdCache<House> houseCache;
    private final IdCache<ResidentCoordinator> rcCache;
    private final IdCache<Resident> residentCache;

    public ShiftReportViewLoader(
            Handle h,
            IdCache<House> houseCache,
            IdCache<ResidentCoordinator> rcCache,
            IdCache<Resident> residentCache) {
        this.h = h;
        this.houseCache = houseCache;
        this.rcCache = rcCache;
        this.residentCache = residentCache;
    }

    public ShiftReportView loadShiftReport(long shiftReportId) {
        ShiftReportResidentDAO shiftReportResidentDao = h.attach(ShiftReportResidentDAO.class);
        ShiftReportOnShiftDAO shiftReportOnShiftDao = h.attach(ShiftReportOnShiftDAO.class);
        ShiftReportDAO shiftReportDAO = h.attach(ShiftReportDAO.class);
        List<ShiftReportResident> shiftReportResidents = shiftReportResidentDao.loadShiftReportResidentsForShiftReport(shiftReportId);
        List<Long> onShift = shiftReportOnShiftDao.loadOnShiftForShiftReport(shiftReportId);
        ShiftReport shiftReport = shiftReportDAO.loadShiftReport(shiftReportId);
        return assembleShiftReportView(shiftReport, onShift, shiftReportResidents);
    }

    private ShiftReportView assembleShiftReportView(
            ShiftReport shiftReport,
            List<Long> onShift,
            List<ShiftReportResident> shiftReportResidents) {
        House house = houseCache.loadById(h, shiftReport.getHouseId());
        ResidentCoordinator residentCoordinator = rcCache.loadById(h, shiftReport.getCreatedBy());
        List<ShiftReportResidentView> shiftReportResidentViews = convertShiftReportResidents(shiftReportResidents);
        return new ShiftReportView(
                shiftReport.getId(),
                house.getName(),
                shiftReport.getDate(),
                residentCoordinator.getName(),
                shiftReport.getShift(),
                shiftReport.getTimeCreated(),
                shiftReport.isKeysAccountedFor(),
                shiftReport.getKeysAccountedForReason(),
                shiftReportResidentViews);

    }

    private List<ShiftReportResidentView> convertShiftReportResidents(
            List<ShiftReportResident> shiftReportResidents) {
        return Lists.transform(shiftReportResidents, new Function<ShiftReportResident, ShiftReportResidentView>() {
            @Override
            public ShiftReportResidentView apply(ShiftReportResident shiftReportResident) {
                Resident resident = residentCache.loadById(h, shiftReportResident.getResidentId());
                return new ShiftReportResidentView(
                        shiftReportResident.getId(),
                        shiftReportResident.getShiftReportId(),
                        resident.getName(),
                        shiftReportResident.getSummary(),
                        shiftReportResident.getNotes());
            }
        });
    }

}
