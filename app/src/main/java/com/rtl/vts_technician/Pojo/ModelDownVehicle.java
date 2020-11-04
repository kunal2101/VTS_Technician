package com.rtl.vts_technician.Pojo;

public class ModelDownVehicle {

    String VehicleNo;
    String ComAddess;
    String  dateTime;
    String ImeiNo;
    String DepoName;
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVehicleNo () {
        return VehicleNo;
    }

    public void setVehicleNo ( String vehicleNo ) {
        VehicleNo = vehicleNo;
    }

    public String getComAddess () {
        return ComAddess;
    }

    public void setComAddess ( String comAddess ) {
        ComAddess = comAddess;
    }

    public String getDateTime () {
        return dateTime;
    }

    public void setDateTime ( String dateTime ) {
        this.dateTime = dateTime;
    }

    public String getImeiNo () {
        return ImeiNo;
    }

    public void setImeiNo ( String imeiNo ) {
        ImeiNo = imeiNo;
    }

    public String getDepoName () {
        return DepoName;
    }

    public void setDepoName ( String depoName ) {
        DepoName = depoName;
    }

    public ModelDownVehicle () {
    }
}
