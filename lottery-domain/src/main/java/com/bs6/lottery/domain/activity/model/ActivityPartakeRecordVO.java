package com.bs6.lottery.domain.activity.model;

public class ActivityPartakeRecordVO {

    /** 用户ID */
    private String uid;
    /** activity 活动ID */
    private Long activityId;
    /** 库存 */
    private Integer stockCount;
    /** activity 库存剩余 */
    private Integer stockSurplusCount;

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

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Integer getStockSurplusCount() {
        return stockSurplusCount;
    }

    public void setStockSurplusCount(Integer stockSurplusCount) {
        this.stockSurplusCount = stockSurplusCount;
    }

    @Override
    public String toString() {
        return "ActivityPartakeRecordVO{" +
                "uid='" + uid + '\'' +
                ", activityId=" + activityId +
                ", stockCount=" + stockCount +
                ", stockSurplusCount=" + stockSurplusCount +
                '}';
    }

}
