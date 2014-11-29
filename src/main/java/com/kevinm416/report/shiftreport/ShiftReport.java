package com.kevinm416.report.shiftreport;

import java.util.List;

import com.kevinm416.report.common.Identifiable;


public class ShiftReport implements Identifiable {

    private final long id;
    private final long houseId;
    private final boolean keysAccountedFor;
    private final String keysAccountedForReason;
    private final ShiftReportMetadata shiftReportMetadata;
    private final List<CreateShiftReportResident> shiftReportResidents;

    public ShiftReport(
            long id,
            long houseId,
            boolean keysAccountedFor,
            String keysAccountedForReason,
            ShiftReportMetadata shiftReportMetadata,
            List<CreateShiftReportResident> shiftReportResidents) {
        this.id = id;
        this.houseId = houseId;
        this.shiftReportMetadata = shiftReportMetadata;
        this.keysAccountedFor = keysAccountedFor;
        this.keysAccountedForReason = keysAccountedForReason;
        this.shiftReportResidents = shiftReportResidents;
    }

    @Override
    public long getId() {
        return id;
    }

    public long getHouseId() {
        return houseId;
    }

    public ShiftReportMetadata getShiftReportMetadata() {
        return shiftReportMetadata;
    }

    public boolean isKeysAccountedFor() {
        return keysAccountedFor;
    }

    public String getKeysAccountedForReason() {
        return keysAccountedForReason;
    }

    public List<CreateShiftReportResident> getShiftReportResidents() {
        return shiftReportResidents;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (houseId ^ (houseId >>> 32));
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + (keysAccountedFor ? 1231 : 1237);
        result = prime
                * result
                + ((keysAccountedForReason == null) ? 0
                        : keysAccountedForReason.hashCode());
        result = prime
                * result
                + ((shiftReportMetadata == null) ? 0 : shiftReportMetadata
                        .hashCode());
        result = prime
                * result
                + ((shiftReportResidents == null) ? 0 : shiftReportResidents
                        .hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ShiftReport other = (ShiftReport) obj;
        if (houseId != other.houseId) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (keysAccountedFor != other.keysAccountedFor) {
            return false;
        }
        if (keysAccountedForReason == null) {
            if (other.keysAccountedForReason != null) {
                return false;
            }
        } else if (!keysAccountedForReason.equals(other.keysAccountedForReason)) {
            return false;
        }
        if (shiftReportMetadata == null) {
            if (other.shiftReportMetadata != null) {
                return false;
            }
        } else if (!shiftReportMetadata.equals(other.shiftReportMetadata)) {
            return false;
        }
        if (shiftReportResidents == null) {
            if (other.shiftReportResidents != null) {
                return false;
            }
        } else if (!shiftReportResidents.equals(other.shiftReportResidents)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ShiftReport [id=" + id + ", houseId=" + houseId
                + ", keysAccountedFor=" + keysAccountedFor
                + ", keysAccountedForReason=" + keysAccountedForReason
                + ", shiftReportMetadata=" + shiftReportMetadata
                + ", shiftReportResidents=" + shiftReportResidents + "]";
    }

}
