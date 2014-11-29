package com.kevinm416.report.shiftreport;

import com.kevinm416.report.common.Identifiable;

public class ShiftReportResidentView implements Identifiable {

    private final long id;
    private final long shiftReportId;
    private final String residentName;
    private final String summary;
    private final String notes;

    public ShiftReportResidentView(
            long id,
            long shiftReportId,
            String residentName,
            String summary,
            String notes) {
        this.id = id;
        this.shiftReportId = shiftReportId;
        this.residentName = residentName;
        this.summary = summary;
        this.notes = notes;
    }

    @Override
    public long getId() {
        return id;
    }

    public long getShiftReportId() {
        return shiftReportId;
    }

    public String getResidentName() {
        return residentName;
    }

    public String getSummary() {
        return summary;
    }

    public String getNotes() {
        return notes;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((notes == null) ? 0 : notes.hashCode());
        result = prime * result
                + ((residentName == null) ? 0 : residentName.hashCode());
        result = prime * result
                + (int) (shiftReportId ^ (shiftReportId >>> 32));
        result = prime * result + ((summary == null) ? 0 : summary.hashCode());
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
        ShiftReportResidentView other = (ShiftReportResidentView) obj;
        if (id != other.id) {
            return false;
        }
        if (notes == null) {
            if (other.notes != null) {
                return false;
            }
        } else if (!notes.equals(other.notes)) {
            return false;
        }
        if (residentName == null) {
            if (other.residentName != null) {
                return false;
            }
        } else if (!residentName.equals(other.residentName)) {
            return false;
        }
        if (shiftReportId != other.shiftReportId) {
            return false;
        }
        if (summary == null) {
            if (other.summary != null) {
                return false;
            }
        } else if (!summary.equals(other.summary)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ShiftReportResidentView [id=" + id + ", shiftReportId="
                + shiftReportId + ", residentName=" + residentName
                + ", summary=" + summary + ", notes=" + notes + "]";
    }

}
