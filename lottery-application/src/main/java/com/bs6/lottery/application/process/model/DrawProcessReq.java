package com.bs6.lottery.application.process.model;


public class DrawProcessReq {

    /** 用户ID */
    private String uid;
    /** 活动ID */
    private Long activityId;

    public DrawProcessReq() {
    }

    public DrawProcessReq(String uid, Long activityId) {
        this.uid = uid;
        this.activityId = activityId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

}
