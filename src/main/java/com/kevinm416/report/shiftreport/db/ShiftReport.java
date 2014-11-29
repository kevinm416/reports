package com.kevinm416.report.shiftreport.db;


public class ShiftReport {

    private final long id;
    private final long houseId;
    private final long date;
    private final long createdBy;
    private final String shift;
    private final long timeCreated;
    private final boolean keysAccountedFor;
    private final String keysAccountedForReason;

    public ShiftReport(
            long id,
            long houseId,
            long date,
            long createdBy,
            String shift,
            long timeCreated,
            boolean keysAccountedFor,
            String keysAccountedForReason) {
        this.id = id;
        this.houseId = houseId;
        this.date = date;
        this.createdBy = createdBy;
        this.shift = shift;
        this.timeCreated = timeCreated;
        this.keysAccountedFor = keysAccountedFor;
        this.keysAccountedForReason = keysAccountedForReason;
    }

    public long getId() {
        return id;
    }

    public long getHouseId() {
        return houseId;
    }

    public long getDate() {
        return date;
    }

    public long getCreatedBy() {
        return createdBy;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (createdBy ^ (createdBy >>> 32));
        result = prime * result + (int) (date ^ (date >>> 32));
        result = prime * result + (int) (houseId ^ (houseId >>> 32));
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + (keysAccountedFor ? 1231 : 1237);
        result = prime
                * result
                + ((keysAccountedForReason == null) ? 0
                        : keysAccountedForReason.hashCode());
        result = prime * result + ((shift == null) ? 0 : shift.hashCode());
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
        ShiftReport other = (ShiftReport) obj;
        if (createdBy != other.createdBy) {
            return false;
        }
        if (date != other.date) {
            return false;
        }
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
        if (shift == null) {
            if (other.shift != null) {
                return false;
            }
        } else if (!shift.equals(other.shift)) {
            return false;
        }
        if (timeCreated != other.timeCreated) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ShiftReport [id=" + id + ", houseId=" + houseId + ", date="
                + date + ", createdBy=" + createdBy + ", shift=" + shift
                + ", timeCreated=" + timeCreated + ", keysAccountedFor="
                + keysAccountedFor + ", keysAccountedForReason="
                + keysAccountedForReason + "]";
    }

}
