package com.kevinm416.report.shiftreport;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateShiftReport {

    private final long houseId;
    private final List<Long> onShift;
    private final String shift;
    private final long timeCreated;
    private final boolean keysAccountedFor;
    private final String keysAccountedForReason;
    private final List<CreateShiftReportResident> shiftReportResidents;

    @JsonCreator
    public CreateShiftReport(
            @JsonProperty("houseId") long houseId,
            @JsonProperty("onShift") List<Long> onShift,
            @JsonProperty("shift") String shift,
            @JsonProperty("timeCreated") long timeCreated,
            @JsonProperty("keysAccountedFor") boolean keysAccountedFor,
            @JsonProperty("keysAccountedForReason") String keysAccountedForReason,
            @JsonProperty("shiftReportResidents") List<CreateShiftReportResident> shiftReportResidents) {
        this.houseId = houseId;
        this.onShift = onShift;
        this.shift = shift;
        this.timeCreated = timeCreated;
        this.keysAccountedFor = keysAccountedFor;
        this.keysAccountedForReason = keysAccountedForReason;
        this.shiftReportResidents = shiftReportResidents;
    }

    public long getHouseId() {
        return houseId;
    }

    public List<Long> getOnShift() {
        return onShift;
    }

    public String getShift() {
        return shift;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public boolean isKeysAccountedFor() {
        return keysAccountedFor;
    }

    public String getKeysAccountedForReason() {
        return keysAccountedForReason;
    }

    public Iterable<CreateShiftReportResident> getShiftReportResidents() {
        return shiftReportResidents;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (houseId ^ (houseId >>> 32));
        result = prime * result + (keysAccountedFor ? 1231 : 1237);
        result = prime
                * result
                + ((keysAccountedForReason == null) ? 0
                        : keysAccountedForReason.hashCode());
        result = prime * result + ((onShift == null) ? 0 : onShift.hashCode());
        result = prime * result + ((shift == null) ? 0 : shift.hashCode());
        result = prime
                * result
                + ((shiftReportResidents == null) ? 0 : shiftReportResidents
                        .hashCode());
        result = prime * result + (int) (timeCreated ^ (timeCreated >>> 32));
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
        CreateShiftReport other = (CreateShiftReport) obj;
        if (houseId != other.houseId) {
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
        if (onShift == null) {
            if (other.onShift != null) {
                return false;
            }
        } else if (!onShift.equals(other.onShift)) {
            return false;
        }
        if (shift == null) {
            if (other.shift != null) {
                return false;
            }
        } else if (!shift.equals(other.shift)) {
            return false;
        }
        if (shiftReportResidents == null) {
            if (other.shiftReportResidents != null) {
                return false;
            }
        } else if (!shiftReportResidents.equals(other.shiftReportResidents)) {
            return false;
        }
        if (timeCreated != other.timeCreated) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CreateShiftReport [houseId=" + houseId + ", onShift=" + onShift
                + ", shift=" + shift + ", timeCreated=" + timeCreated
                + ", keysAccountedFor=" + keysAccountedFor
                + ", keysAccountedForReason=" + keysAccountedForReason
                + ", shiftReportResidents=" + shiftReportResidents + "]";
    }

}
