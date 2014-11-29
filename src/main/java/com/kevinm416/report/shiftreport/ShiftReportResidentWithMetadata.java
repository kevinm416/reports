package com.kevinm416.report.shiftreport;

public class ShiftReportResidentWithMetadata {

    private final ShiftReportResident shiftReportResident;
    private final ShiftReportMetadata shiftReportMetadata;

    public ShiftReportResidentWithMetadata(
            ShiftReportResident shiftReportResident,
            ShiftReportMetadata shiftReportMetadata) {
        this.shiftReportResident = shiftReportResident;
        this.shiftReportMetadata = shiftReportMetadata;
    }

    public ShiftReportResident getShiftReportResident() {
        return shiftReportResident;
    }

    public ShiftReportMetadata getShiftReportMetadata() {
        return shiftReportMetadata;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime
                * result
                + ((shiftReportMetadata == null) ? 0 : shiftReportMetadata
                        .hashCode());
        result = prime
                * result
                + ((shiftReportResident == null) ? 0 : shiftReportResident
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
        ShiftReportResidentWithMetadata other = (ShiftReportResidentWithMetadata) obj;
        if (shiftReportMetadata == null) {
            if (other.shiftReportMetadata != null) {
                return false;
            }
        } else if (!shiftReportMetadata.equals(other.shiftReportMetadata)) {
            return false;
        }
        if (shiftReportResident == null) {
            if (other.shiftReportResident != null) {
                return false;
            }
        } else if (!shiftReportResident.equals(other.shiftReportResident)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ShiftReportResidentWithMetadata [shiftReportResident="
                + shiftReportResident + ", shiftReportMetadata="
                + shiftReportMetadata + "]";
    }

}
