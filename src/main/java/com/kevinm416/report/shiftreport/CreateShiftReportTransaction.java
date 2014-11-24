package com.kevinm416.report.shiftreport;

import org.skife.jdbi.v2.Handle;

public class CreateShiftReportTransaction {

    private final Handle h;

    public CreateShiftReportTransaction(Handle h) {
        this.h = h;
    }

    public long createShiftReport(CreateShiftReport createShiftReport) {
        ShiftReportDAO shiftReportDAO = h.attach(ShiftReportDAO.class);
        ShiftReportResidentDAO shiftReportResidentDAO = h.attach(ShiftReportResidentDAO.class);
        ShiftReportOnShiftDAO shiftReportOnShiftDAO = h.attach(ShiftReportOnShiftDAO.class);

        h.begin();
        long shiftReportId = shiftReportDAO.createShiftReport(1, createShiftReport);
        shiftReportResidentDAO.createShiftReportResidents(
                shiftReportId,
                createShiftReport.getTimeCreated(),
                createShiftReport.getShiftReportResidents());
        shiftReportOnShiftDAO.createOnShiftRecords(shiftReportId, createShiftReport.getOnShift());
        h.commit();
        h.close();

        return shiftReportId;
    }

}