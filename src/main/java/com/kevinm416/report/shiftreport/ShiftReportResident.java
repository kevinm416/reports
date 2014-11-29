package com.kevinm416.report.shiftreport;

import com.kevinm416.report.common.Identifiable;

public class ShiftReportResident implements Identifiable {

    private final long id;
    private final long shiftReportId;
    private final long residentId;
    private final String summary;
    private final String notes;

    public ShiftReportResident(
            long id,
            long shiftReportId,
            long residentId,
            String summary,
            String notes) {
        this.id = id;
        this.shiftReportId = shiftReportId;
        this.residentId = residentId;
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

    public long getResidentId() {
        return residentId;
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
        result = prime * result + (int) (residentId ^ (residentId >>> 32));
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
        ShiftReportResident other = (ShiftReportResident) obj;
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
        if (residentId != other.residentId) {
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
        return "ShiftReportResident [id=" + id + ", shiftReportId="
                + shiftReportId + ", residentId=" + residentId + ", summary="
                + summary + ", notes=" + notes + "]";
    }

}
