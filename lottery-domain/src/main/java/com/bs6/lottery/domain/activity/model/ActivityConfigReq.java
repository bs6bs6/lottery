package com.bs6.lottery.domain.activity.model;

import com.bs6.lottery.domain.activity.model.ActivityVO;


public class ActivityConfigReq {

    /** 活动ID */
    private Long activityId;

    /** 活动配置信息 */
    private ActivityConfigVO activityConfigVO;

    public ActivityConfigReq() {
    }

    public ActivityConfigReq(Long activityId, ActivityConfigVO activityConfigVO) {
        this.activityId = activityId;
        this.activityConfigVO = activityConfigVO;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public ActivityConfigVO getActivityConfigVO() {
        return activityConfigVO;
    }

    public void setActivityConfigVO(ActivityConfigVO activityConfigRich) {
        this.activityConfigVO = activityConfigRich;
    }

}
