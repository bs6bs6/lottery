package com.bs6.lottery.domain.activity.model;

import java.util.Date;

public class PartakeReq {
    /** 用户ID */
    private String uid;
    /** 活动ID */
    private Long activityId;
    /** 活动领取时间 */
    private Date partakeDate;

    public PartakeReq() {
    }

    public PartakeReq(String uid, Long activityId) {
        this.uid = uid;
        this.activityId = activityId;
        this.partakeDate = new Date();
    }
    public PartakeReq(String uid, Long activityId, Date partakeDate) {
        this.uid = uid;
        this.activityId = activityId;
        this.partakeDate = partakeDate;
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

    public Date getPartakeDate() {
        return partakeDate;
    }

    public void setPartakeDate(Date partakeDate) {
        this.partakeDate = partakeDate;
    }

    @Override
    public String toString() {
        return "PartakeReq{" +
                "uid='" + uid + '\'' +
                ", activityId=" + activityId +
                ", partakeDate=" + partakeDate +
                '}';
    }
}
