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
import com.kevinm416.report.rc.ResidentCoordinatorCache;

@Path("/shiftReports")
@Produces(MediaType.APPLICATION_JSON)
public class ShiftReportResource {

    private final DBI jdbi;
    private final ResidentCoordinatorCache residentCoordinatorCache;

    public ShiftReportResource(
            DBI jdbi,
            ResidentCoordinatorCache residentCoordinatorCache) {
        this.jdbi = jdbi;
        this.residentCoordinatorCache = residentCoordinatorCache;
    }

    @POST
    public long createShiftReport(
            @Auth final ResidentCoordinator user,
            final CreateShiftReport createShiftReport) {
        return jdbi.withHandle(new HandleCallback<Long>() {
            @Override
            public Long withHandle(Handle handle) throws Exception {
                return new CreateShiftReportTransaction(handle).createShiftReport(
                        user.getId(),
                        createShiftReport);
            }
        });
    }

    @GET
    @Path("/{residentId}")
    public List<ShiftReportResidentWithMetadata> loadShiftReportsForResident(
            @Auth ResidentCoordinator user,
            @PathParam("residentId") final long residentId) {
        List<ShiftReportResidentWithMetadata> a = jdbi.withHandle(new HandleCallback<List<ShiftReportResidentWithMetadata>>() {
            @Override
            public List<ShiftReportResidentWithMetadata> withHandle(Handle handle) throws Exception {
                return new LoadShiftReportResidentWithMetadata(handle, residentCoordinatorCache)
                        .loadShiftReportResidentsWithMetadata(residentId);
            }
        });
        return a;
    }

}
