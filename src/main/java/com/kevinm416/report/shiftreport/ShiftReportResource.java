package com.kevinm416.report.shiftreport;

import io.dropwizard.auth.Auth;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.tweak.HandleCallback;

import com.kevinm416.report.rc.ResidentCoordinator;

@Path("/shiftReports")
@Produces(MediaType.APPLICATION_JSON)
public class ShiftReportResource {

    private final DBI jdbi;
    private final ShiftReportResidentDAO shiftReportResidentDao;

    public ShiftReportResource(
            DBI jdbi,
            ShiftReportResidentDAO shiftReportResidentDao) {
        this.jdbi = jdbi;
        this.shiftReportResidentDao = shiftReportResidentDao;
    }

    @POST
    public long createShiftReport(
            @Auth final ResidentCoordinator user,
            final CreateShiftReport createShiftReport) {
        return jdbi.withHandle(new HandleCallback<Long>() {
            @Override
            public Long withHandle(Handle handle) throws Exception {
                return new CreateShiftReportTransaction(handle).createShiftReport(createShiftReport);
            }
        });
    }

    @GET
    @Path("/{residentId}")
    public List<ShiftReportResident> loadShiftReportsForResident(
            @Auth ResidentCoordinator user,
            @PathParam("residentId") long residentId) {
        return shiftReportResidentDao.loadShiftReportResidents(residentId);
    }

}
