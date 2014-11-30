package com.kevinm416.report.shiftreport;

import java.util.List;
import java.util.Set;

import com.kevinm416.report.common.Identifiable;

public class ShiftReportView implements Identifiable {

    private final long id;
    private final String houseName;
    private final long date;
    private final String createdByName;
    private final String shift;
    private final long timeCreated;
    private final boolean keysAccountedFor;
    private final String keysAccountedForReason;
    private final Set<String> onShiftNames;
    private final List<ShiftReportResidentView> shiftReportResidents;

    public ShiftReportView(
            long id,
            String houseName,
            long date,
            String createdByName,
            String shift,
            long timeCreated,
            boolean keysAccountedFor,
            String keysAccountedForReason,
            Set<String> onShiftNames,
            List<ShiftReportResidentView> shiftReportResidents) {
        this.id = id;
        this.houseName = houseName;
        this.date = date;
        this.createdByName = createdByName;
        this.shift = shift;
        this.timeCreated = timeCreated;
        this.keysAccountedFor = keysAccountedFor;
        this.keysAccountedForReason = keysAccountedForReason;
        this.onShiftNames = onShiftNames;
        this.shiftReportResidents = shiftReportResidents;
    }

    @Override
    public long getId() {
        return id;
    }

    public String getHouseName() {
        return houseName;
    }

    public long getDate() {
        return date;
    }

    public String getCreatedByName() {
        return createdByName;
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

    public Set<String> getOnShiftNames() {
        return onShiftNames;
    }

    public List<ShiftReportResidentView> getShiftReportResidents() {
        return shiftReportResidents;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((createdByName == null) ? 0 : createdByName.hashCode());
        result = prime * result + (int) (date ^ (date >>> 32));
        result = prime * result
                + ((houseName == null) ? 0 : houseName.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + (keysAccountedFor ? 1231 : 1237);
        result = prime
                * result
                + ((keysAccountedForReason == null) ? 0
                        : keysAccountedForReason.hashCode());
        result = prime * result
                + ((onShiftNames == null) ? 0 : onShiftNames.hashCode());
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
        ShiftReportView other = (ShiftReportView) obj;
        if (createdByName == null) {
            if (other.createdByName != null) {
                return false;
            }
        } else if (!createdByName.equals(other.createdByName)) {
            return false;
        }
        if (date != other.date) {
            return false;
        }
        if (houseName == null) {
            if (other.houseName != null) {
                return false;
            }
        } else if (!houseName.equals(other.houseName)) {
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
        if (onShiftNames == null) {
            if (other.onShiftNames != null) {
                return false;
            }
        } else if (!onShiftNames.equals(other.onShiftNames)) {
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
        return "ShiftReportView [id=" + id + ", houseName=" + houseName
                + ", date=" + date + ", createdByName=" + createdByName
                + ", shift=" + shift + ", timeCreated=" + timeCreated
                + ", keysAccountedFor=" + keysAccountedFor
                + ", keysAccountedForReason=" + keysAccountedForReason
                + ", onShiftNames=" + onShiftNames + ", shiftReportResidents="
                + shiftReportResidents + "]";
    }

}
