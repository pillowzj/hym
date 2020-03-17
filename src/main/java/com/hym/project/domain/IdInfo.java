package com.hym.project.domain;

import java.util.Date;

public class IdInfo {
    private String id;

    private String uid;

    private String idNumber;

    private String name;

    private String gender;

    private String nation;

    private String birthday;

    private String address;

    private String idPositive;

    private String idOpposite;

    private String idHand;

    private String idAuthoIssue;

    private String idExpirDate;

    private Date insertTime;

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

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber == null ? null : idNumber.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getIdPositive() {
        return idPositive;
    }

    public void setIdPositive(String idPositive) {
        this.idPositive = idPositive == null ? null : idPositive.trim();
    }

    public String getIdOpposite() {
        return idOpposite;
    }

    public void setIdOpposite(String idOpposite) {
        this.idOpposite = idOpposite == null ? null : idOpposite.trim();
    }

    public String getIdHand() {
        return idHand;
    }

    public void setIdHand(String idHand) {
        this.idHand = idHand == null ? null : idHand.trim();
    }

    public String getIdAuthoIssue() {
        return idAuthoIssue;
    }

    public void setIdAuthoIssue(String idAuthoIssue) {
        this.idAuthoIssue = idAuthoIssue == null ? null : idAuthoIssue.trim();
    }

    public String getIdExpirDate() {
        return idExpirDate;
    }

    public void setIdExpirDate(String idExpirDate) {
        this.idExpirDate = idExpirDate == null ? null : idExpirDate.trim();
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
}