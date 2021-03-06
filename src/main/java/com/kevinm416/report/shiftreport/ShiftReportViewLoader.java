package com.kevinm416.report.shiftreport;

import java.util.List;
import java.util.Set;

import org.skife.jdbi.v2.Handle;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.kevinm416.report.common.cache.IdCache;
import com.kevinm416.report.house.House;
import com.kevinm416.report.resident.api.Resident;
import com.kevinm416.report.shiftreport.db.ShiftReport;
import com.kevinm416.report.shiftreport.db.ShiftReportDAO;
import com.kevinm416.report.user.User;

public class ShiftReportViewLoader {

    private final Handle h;
    private final IdCache<House> houseCache;
    private final IdCache<User> userCache;
    private final IdCache<Resident> residentCache;

    public ShiftReportViewLoader(
            Handle h,
            IdCache<House> houseCache,
            IdCache<User> rcCache,
            IdCache<Resident> residentCache) {
        this.h = h;
        this.houseCache = houseCache;
        this.userCache = rcCache;
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
        User user = userCache.loadById(h, shiftReport.getCreatedBy());
        Set<String> onShiftNames = loadOnShiftNames(onShift);
        List<ShiftReportResidentView> shiftReportResidentViews = convertShiftReportResidents(shiftReportResidents);
        return new ShiftReportView(
                shiftReport.getId(),
                house.getName(),
                shiftReport.getDate(),
                user.getName(),
                shiftReport.getShift(),
                shiftReport.getTimeCreated(),
                shiftReport.isKeysAccountedFor(),
                shiftReport.getKeysAccountedForReason(),
                onShiftNames,
                shiftReportResidentViews);

    }

    private List<ShiftReportResidentView> convertShiftReportResidents(
            List<ShiftReportResident> shiftReportResidents) {
        return FluentIterable.from(shiftReportResidents).transform(new Function<ShiftReportResident, ShiftReportResidentView>() {
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
        }).toList();
    }

    private Set<String> loadOnShiftNames(List<Long> onShift) {
        return FluentIterable.from(onShift).transform(new Function<Long, String>() {
            @Override
            public String apply(Long input) {
                return userCache.loadById(h, input).getName();
            }
        }).toSet();
    }

}
