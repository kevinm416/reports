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

        h.begin();
        long shiftReportId = shiftReportDAO.createShiftReport(1, createShiftReport);
        shiftReportResidentDAO.createShiftReportResidents(createShiftReport.getShiftReportResidents());
        h.commit();
        h.close();

        return shiftReportId;
    }

}
