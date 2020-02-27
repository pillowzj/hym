package com.hym.project.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Asset {
    private String id;

    private String uid;

    private String frozenToken;

    private String token;

    private Date insertDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getFrozenToken() {
        return frozenToken;
    }

    public void setFrozenToken(String frozenToken) {
        this.frozenToken = frozenToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }
}