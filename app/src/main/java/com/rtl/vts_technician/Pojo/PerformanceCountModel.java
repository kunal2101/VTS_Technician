package com.rtl.vts_technician.Pojo;

import java.io.Serializable;

/**
 * Created by Diwash Choudhary on 13-08-2020.
 */
public class PerformanceCountModel implements Serializable {
    private int install;
    private int uninstall;
    private int maintenance;
    private int replace;
    private int total;
    private String activityDate;

    public int getInstall() {
        return install;
    }

    public void setInstall(int install) {
        this.install = install;
    }

    public int getUninstall() {
        return uninstall;
    }

    public void setUninstall(int uninstall) {
        this.uninstall = uninstall;
    }

    public int getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(int maintenance) {
        this.maintenance = maintenance;
    }

    public int getReplace() {
        return replace;
    }

    public void setReplace(int replace) {
        this.replace = replace;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate;
    }


}
