package com.hym.project.domain;

import java.util.Date;

public class Task {
    private String id;

    private String uid;

    private String title;

    private String industry;

    private String description;

    private String icon;

    private Integer count;

    private Integer people;

    private String rmbSum;

    private String price;

    private String token;

    private String tokenTotalSum;

    private Date beginTime;

    private Date endTime;

    private Integer status;

    private Integer isReceived;

    private Integer commission;

    private String insertUser;

    private Date insertDate;

    private String checker;

    private String releaseUser;

    private Date releaseTime;

    public Task(){}

    public Task(String id,String uid,String title,String industry, String description, String icon, Integer status, Integer people,String rmbSum, String price,  String token, String tokenTotalSum,Date insertDate) {
        this.id = id;
        this.uid = uid;
        this.title = title;
        this.industry = industry;
        this.description = description;
        this.icon = icon;
        this.status = status;
        this.people = people;
        this.rmbSum =rmbSum;
        this.price = price;
        this.token = token;
        this.tokenTotalSum = tokenTotalSum;
        this.insertDate = insertDate;
    }


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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry == null ? null : industry.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPeople() {
        return people;
    }

    public void setPeople(Integer people) {
        this.people = people;
    }

    public String getRmbSum() {
        return rmbSum;
    }

    public void setRmbSum(String rmbSum) {
        this.rmbSum = rmbSum == null ? null : rmbSum.trim();
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenTotalSum() {
        return tokenTotalSum;
    }

    public void setTokenTotalSum(String tokenTotalSum) {
        this.tokenTotalSum = tokenTotalSum == null ? null : tokenTotalSum.trim();
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsReceived() {
        return isReceived;
    }

    public void setIsReceived(Integer isReceived) {
        this.isReceived = isReceived;
    }

    public Integer getCommission() {
        return commission;
    }

    public void setCommission(Integer commission) {
        this.commission = commission;
    }

    public String getInsertUser() {
        return insertUser;
    }

    public void setInsertUser(String insertUser) {
        this.insertUser = insertUser == null ? null : insertUser.trim();
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker == null ? null : checker.trim();
    }

    public String getReleaseUser() {
        return releaseUser;
    }

    public void setReleaseUser(String releaseUser) {
        this.releaseUser = releaseUser == null ? null : releaseUser.trim();
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }
}