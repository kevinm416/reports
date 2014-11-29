package com.kevinm416.report.shiftreport;

import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.TransactionCallback;
import org.skife.jdbi.v2.TransactionStatus;

public class CreateShiftReportTransaction {

    private final Handle h;

    public CreateShiftReportTransaction(Handle h) {
        this.h = h;
    }

    public long createShiftReport(
            final long userId,
            final CreateShiftReport createShiftReport) {
        return h.inTransaction(new TransactionCallback<Long>() {
            @Override
            public Long inTransaction(Handle txn, TransactionStatus status) throws Exception {
                return createShiftReportInTransaction(txn, userId, createShiftReport);
            }
        });

    }

    private static long createShiftReportInTransaction(Handle txn, long userId, CreateShiftReport createShiftReport) {
        ShiftReportDAO shiftReportDAO = txn.attach(ShiftReportDAO.class);
        ShiftReportResidentDAO shiftReportResidentDAO = txn.attach(ShiftReportResidentDAO.class);
        ShiftReportOnShiftDAO shiftReportOnShiftDAO = txn.attach(ShiftReportOnShiftDAO.class);

        long shiftReportId = shiftReportDAO.createShiftReport(userId, createShiftReport);
        shiftReportResidentDAO.createShiftReportResidents(
                shiftReportId,
                createShiftReport.getTimeCreated(),
                createShiftReport.getShiftReportResidents());
        shiftReportOnShiftDAO.createOnShiftRecords(shiftReportId, createShiftReport.getOnShift());
        return shiftReportId;
    }

}