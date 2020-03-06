package com.rtl.vts_technician.model;

/**
 * Created by DKC on 04-01-2018.
 */

public class ApprovedDeviceModel {

    private String deviceId;
    private String status;
    private String submitionDate;

    public ApprovedDeviceModel(String deviceId, String status, String submitionDate) {
        this.deviceId = deviceId;
        this.status = status;
        this.submitionDate = submitionDate;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubmitionDate() {
        return submitionDate;
    }

    public void setSubmitionDate(String submitionDate) {
        this.submitionDate = submitionDate;
    }
}
