package com.rtl.vts_technician.Pojo;

public class ReplaceDeviceModel {

    private String id;
    private String veh_no;
    private String depo;
    private String division;
    private String new_imieno;
    private String old_imie;
    private String remarks;
    private String instal_time;
    private String instal_date;
    private String lat;
    private String lng;
    private String address;
    private String imageString;
    private String status;
    private String problem_type;
    private String acton_taken;
    private String last_location;

    public ReplaceDeviceModel() {
    }

    public ReplaceDeviceModel(String veh_no, String depo, String division, String new_imieno, String old_imie, String remarks, String instal_time, String instal_date, String lat, String lng, String address) {
        this.veh_no = veh_no;
        this.depo = depo;
        this.division = division;
        this.new_imieno = new_imieno;
        this.old_imie = old_imie;
        this.remarks = remarks;
        this.instal_time = instal_time;
        this.instal_date = instal_date;
        this.lat = lat;
        this.lng = lng;
        this.address = address;
    }

    public String getLast_location() {
        return last_location;
    }

    public void setLast_location(String last_location) {
        this.last_location = last_location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProblem_type() {
        return problem_type;
    }

    public void setProblem_type(String problem_type) {
        this.problem_type = problem_type;
    }

    public String getActon_taken() {
        return acton_taken;
    }

    public void setActon_taken(String acton_taken) {
        this.acton_taken = acton_taken;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVeh_no() {
        return veh_no;
    }

    public void setVeh_no(String veh_no) {
        this.veh_no = veh_no;
    }

    public String getDepo() {
        return depo;
    }

    public void setDepo(String depo) {
        this.depo = depo;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getNew_imieno() {
        return new_imieno;
    }

    public void setNew_imieno(String new_imieno) {
        this.new_imieno = new_imieno;
    }

    public String getOld_imie() {
        return old_imie;
    }

    public void setOld_imie(String old_imie) {
        this.old_imie = old_imie;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getInstal_time() {
        return instal_time;
    }

    public void setInstal_time(String instal_time) {
        this.instal_time = instal_time;
    }

    public String getInstal_date() {
        return instal_date;
    }

    public void setInstal_date(String instal_date) {
        this.instal_date = instal_date;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
