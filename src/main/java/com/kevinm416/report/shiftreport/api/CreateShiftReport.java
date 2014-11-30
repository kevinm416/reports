package com.kevinm416.report.shiftreport.api;

import io.dropwizard.validation.ValidationMethod;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;
import com.kevinm416.report.shiftreport.CreateShiftReportResident;

public class CreateShiftReport {

    private final long houseId;
    private final long date;
    @NotEmpty private final List<Long> onShift;
    @NotEmpty private final String shift;
    private final long timeCreated;
    private final boolean keysAccountedFor;
    private final String keysAccountedForReason;
    @NotEmpty private final List<CreateShiftReportResident> shiftReportResidents;

    @JsonCreator
    public CreateShiftReport(
            @JsonProperty("houseId") long houseId,
            @JsonProperty("date") long date,
            @JsonProperty("onShift") List<Long> onShift,
            @JsonProperty("shift") String shift,
            @JsonProperty("timeCreated") long timeCreated,
            @JsonProperty("keysAccountedFor") boolean keysAccountedFor,
            @JsonProperty("keysAccountedForReason") String keysAccountedForReason,
            @JsonProperty("shiftReportResidents") List<CreateShiftReportResident> shiftReportResidents) {
        this.houseId = houseId;
        this.date = date;
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

    public long getDate() {
        return date;
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

    @ValidationMethod(message = "keysAccountedForReason should be null iff keysAccountedFor == true")
    public boolean isKeysAccountedForReasonValid() {
        return keysAccountedFor && keysAccountedForReason == null ||
                !keysAccountedFor && !Strings.isNullOrEmpty(keysAccountedForReason);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (date ^ (date >>> 32));
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
        if (date != other.date) {
            return false;
        }
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
        return "CreateShiftReport [houseId=" + houseId + ", date=" + date
                + ", onShift=" + onShift + ", shift=" + shift
                + ", timeCreated=" + timeCreated + ", keysAccountedFor="
                + keysAccountedFor + ", keysAccountedForReason="
                + keysAccountedForReason + ", shiftReportResidents="
                + shiftReportResidents + "]";
    }

}
