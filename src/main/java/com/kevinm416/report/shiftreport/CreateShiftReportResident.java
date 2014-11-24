package com.kevinm416.report.shiftreport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateShiftReportResident {

    private final long residentId;
    private final String summary;
    private final String notes;

    @JsonCreator
    public CreateShiftReportResident(
            @JsonProperty("residentId") long residentId,
            @JsonProperty("summary") String summary,
            @JsonProperty("notes") String notes) {
        this.residentId = residentId;
        this.summary = summary;
        this.notes = notes;
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
        result = prime * result + ((notes == null) ? 0 : notes.hashCode());
        result = prime * result + (int) (residentId ^ (residentId >>> 32));
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
        CreateShiftReportResident other = (CreateShiftReportResident) obj;
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
        return "CreateShiftReportResident [residentId=" + residentId
                + ", summary=" + summary + ", notes=" + notes + "]";
    }

}
