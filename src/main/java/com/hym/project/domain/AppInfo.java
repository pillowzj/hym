package com.hym.project.domain;

import java.util.Date;

public class AppInfo {
    private String id;

    private String tid;

    private String appName;

    private String appid;

    private String pname;

    private String action;

    private String remark;

    private Date insertDate;

    public AppInfo() {
    }

    public AppInfo(String id, String tid, String appName, String appid, String pname, String action) {
        this.id = id;
        this.tid = tid;
        this.appName = appName;
        this.appid = appid;
        this.pname = pname;
        this.action = action;
        this.insertDate = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid == null ? null : tid.trim();
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname == null ? null : pname.trim();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action == null ? null : action.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }
}