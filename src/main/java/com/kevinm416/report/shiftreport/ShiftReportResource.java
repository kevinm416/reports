package com.kevinm416.report.shiftreport;

import io.dropwizard.auth.Auth;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
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

    public ShiftReportResource(DBI jdbi) {
        this.jdbi = jdbi;
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

}
