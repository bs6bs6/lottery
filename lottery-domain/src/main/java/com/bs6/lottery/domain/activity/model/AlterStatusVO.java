package com.bs6.lottery.domain.activity.model;


public class AlterStatusVO {

    /** 活动ID */
    private Long activityId;

    /** 更新前状态 */
    private Integer beforeStatus;

    /** 更新后状态 */
    private Integer afterStatus;

    public AlterStatusVO() {
    }

    public AlterStatusVO(Long activityId, Integer beforeStatus, Integer afterStatus) {
        this.activityId = activityId;
        this.beforeStatus = beforeStatus;
        this.afterStatus = afterStatus;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Integer getBeforeStatus() {
        return beforeStatus;
    }

    public void setBeforeStatus(Integer beforeStatus) {
        this.beforeStatus = beforeStatus;
    }

    public Integer getAfterStatus() {
        return afterStatus;
    }

    public void setAfterStatus(Integer afterStatus) {
        this.afterStatus = afterStatus;
    }

    @Override
    public String toString() {
        return "AlterStatusVO{" +
                "activityId=" + activityId +
                ", beforeStatus=" + beforeStatus +
                ", afterStatus=" + afterStatus +
                '}';
    }
}
