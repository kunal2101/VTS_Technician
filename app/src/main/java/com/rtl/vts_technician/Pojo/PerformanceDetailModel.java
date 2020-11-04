package com.rtl.vts_technician.Pojo;

/**
 * Created by Diwash Choudhary on 31-08-2020.
 */
public class PerformanceDetailModel {
    private String tech_name;
    private String uninstall;
    private String maintaince;
    private String replace;
    private String install;
    private String total;
    private String activity_date;

    public String getTech_name() {
        return tech_name;
    }

    public void setTech_name(String tech_name) {
        this.tech_name = tech_name;
    }

    public String getUninstall() {
        return uninstall;
    }

    public void setUninstall(String uninstall) {
        this.uninstall = uninstall;
    }

    public String getMaintaince() {
        return maintaince;
    }

    public void setMaintaince(String maintaince) {
        this.maintaince = maintaince;
    }

    public String getReplace() {
        return replace;
    }

    public void setReplace(String replace) {
        this.replace = replace;
    }

    public String getInstall() {
        return install;
    }

    public void setInstall(String install) {
        this.install = install;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getActivity_date() {
        return activity_date;
    }

    public void setActivity_date(String activity_date) {
        this.activity_date = activity_date;
    }
}
