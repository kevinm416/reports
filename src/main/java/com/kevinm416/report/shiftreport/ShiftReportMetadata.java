package com.kevinm416.report.shiftreport;

import com.kevinm416.report.common.Identifiable;


public class ShiftReportMetadata implements Identifiable {

    private final long id;
    private final long date;
    private final String createdByName;
    private final String shift;
    private final long timeCreated;

    public ShiftReportMetadata(
            long id,
            long date,
            String createdByName,
            String shift,
            long timeCreated) {
        this.id = id;
        this.date = date;
        this.createdByName = createdByName;
        this.shift = shift;
        this.timeCreated = timeCreated;
    }

    @Override
    public long getId() {
        return id;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((createdByName == null) ? 0 : createdByName.hashCode());
        result = prime * result + (int) (date ^ (date >>> 32));
        result = prime * result + (int) (id ^ (id >>> 32));
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
        ShiftReportMetadata other = (ShiftReportMetadata) obj;
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
        if (id != other.id) {
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
        return "ShiftReportMetadata [id=" + id + ", date=" + date
                + ", createdByName=" + createdByName + ", shift=" + shift
                + ", timeCreated=" + timeCreated + "]";
    }

}
