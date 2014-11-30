package com.kevinm416.report.shiftreport;

import io.dropwizard.auth.Auth;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.tweak.HandleCallback;

import com.codahale.metrics.annotation.Timed;
import com.kevinm416.report.common.cache.IdCache;
import com.kevinm416.report.house.House;
import com.kevinm416.report.rc.ResidentCoordinator;
import com.kevinm416.report.resident.Resident;
import com.kevinm416.report.shiftreport.api.CreateShiftReport;

@Path("/shiftReports")
@Produces(MediaType.APPLICATION_JSON)
public class ShiftReportResource {

    private final DBI jdbi;
    private final IdCache<House> houseCache;
    private final IdCache<ResidentCoordinator> rcCache;
    private final IdCache<Resident> residentCache;

    public ShiftReportResource(
            DBI jdbi,
            IdCache<House> houseCache,
            IdCache<ResidentCoordinator> rcCache,
            IdCache<Resident> residentCache) {
        this.jdbi = jdbi;
        this.houseCache = houseCache;
        this.rcCache = rcCache;
        this.residentCache = residentCache;
    }

    @POST
    @Timed
    public long createShiftReport(
            @Auth final ResidentCoordinator user,
            @Valid final CreateShiftReport createShiftReport) {
        return jdbi.withHandle(new HandleCallback<Long>() {
            @Override
            public Long withHandle(Handle handle) {
                return new CreateShiftReportTransaction(handle).createShiftReport(
                        user.getId(),
                        createShiftReport);
            }
        });
    }

    @GET
    @Timed
    @Path("/resident/{residentId}")
    public List<ShiftReportResidentWithMetadata> loadShiftReportsForResident(
            @Auth ResidentCoordinator user,
            @PathParam("residentId") final long residentId,
            @QueryParam("pageSize") final int pageSize,
            @QueryParam("lastShiftReportResidentId") final Long lastShiftReportResidentId) {
        List<ShiftReportResidentWithMetadata> ret = jdbi.withHandle(new HandleCallback<List<ShiftReportResidentWithMetadata>>() {
            @Override
            public List<ShiftReportResidentWithMetadata> withHandle(Handle handle) {
                return loadShiftReportsForResidentWithHandle(handle, residentId, pageSize, lastShiftReportResidentId);
            }
        });
        return ret;
    }

    private List<ShiftReportResidentWithMetadata> loadShiftReportsForResidentWithHandle(
            Handle h,
            long residentId,
            int pageSize,
            Long lastShiftReportResidentId) {
        LoadShiftReportResidentWithMetadata loadShiftReportResidentWithMetadata =
                new LoadShiftReportResidentWithMetadata(h, rcCache);
        return loadShiftReportResidentWithMetadata.loadShiftReportResidentsWithMetadataPage(
                residentId,
                pageSize,
                lastShiftReportResidentId);
    }

    @GET
    @Timed
    @Path("/{shiftReportId}")
    public ShiftReportView loadShiftReport(
            @Auth ResidentCoordinator user,
            @PathParam("shiftReportId") final long shiftReportId) {
        ShiftReportView shiftReportView = jdbi.withHandle(new HandleCallback<ShiftReportView>() {
            @Override
            public ShiftReportView withHandle(Handle h) {
                return loadShiftReportWithHandle(h, shiftReportId);
            }
        });
        return shiftReportView;
    }

    private ShiftReportView loadShiftReportWithHandle(Handle h, long shiftReportId) {
        ShiftReportViewLoader shiftReportViewLoader = new ShiftReportViewLoader(h, houseCache, rcCache, residentCache);
        return shiftReportViewLoader.loadShiftReport(shiftReportId);
    }

}
