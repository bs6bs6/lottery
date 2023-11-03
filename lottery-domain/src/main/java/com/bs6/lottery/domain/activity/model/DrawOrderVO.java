package com.bs6.lottery.domain.activity.model;

import java.util.Date;

/**
 * @description: 奖品单
 * @author: 小傅哥，微信：fustack
 * @date: 2021/10/3
 * @github: https://github.com/fuzhengwei
 * @Copyright: 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
public class DrawOrderVO {

    /**
     * 用户ID
     */
    private String uid;

    /**
     * 活动领取ID
     */
    private Long takeId;
    /**
     * 活动ID
     */
    private Long activityId;
    /**
     * 订单ID
     */
    private Long orderId;
    /**
     * 策略ID
     */
    private Long strategyId;
    /**
     * 策略方式（1:单项概率、2:总体概率）
     */
    private Integer strategyMode;
    /**
     * 发放奖品方式（1:即时、2:定时[含活动结束]、3:人工）
     */
    private Integer distributeType;
    /**
     * 发奖时间
     */
    private Date distributeDate;
    /**
     * 发奖状态
     */
    private Integer distributeStatus;
    /**
     * 发奖ID
     */
    private String prizeId;
    /**
     * 奖品类型（1:文字描述、2:兑换码、3:优惠券、4:实物奖品）
     */
    private Integer prizeType;
    /**
     * 奖品名称
     */
    private String prizeName;
    /**
     * 奖品内容「文字描述、Key、码」
     */
    private String prizeContent;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Long getTakeId() {
        return takeId;
    }

    public void setTakeId(Long takeId) {
        this.takeId = takeId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public Integer getStrategyMode() {
        return strategyMode;
    }

    public void setStrategyMode(Integer strategyMode) {
        this.strategyMode = strategyMode;
    }

    public Integer getDistributeType() {
        return distributeType;
    }

    public void setDistributeType(Integer distributeType) {
        this.distributeType = distributeType;
    }

    public Date getDistributeDate() {
        return distributeDate;
    }

    public void setDistributeDate(Date distributeDate) {
        this.distributeDate = distributeDate;
    }

    public Integer getDistributeStatus() {
        return distributeStatus;
    }

    public void setDistributeStatus(Integer distributeStatus) {
        this.distributeStatus = distributeStatus;
    }

    public String getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(String prizeId) {
        this.prizeId = prizeId;
    }

    public Integer getPrizeType() {
        return prizeType;
    }

    public void setPrizeType(Integer prizeType) {
        this.prizeType = prizeType;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public String getPrizeContent() {
        return prizeContent;
    }

    public void setPrizeContent(String prizeContent) {
        this.prizeContent = prizeContent;
    }

    @Override
    public String toString() {
        return "DrawOrderVO{" +
                "uid='" + uid + '\'' +
                ", takeId=" + takeId +
                ", activityId=" + activityId +
                ", orderId=" + orderId +
                ", strategyId=" + strategyId +
                ", strategyMode=" + strategyMode +
                ", distributeType=" + distributeType +
                ", distributeDate=" + distributeDate +
                ", distributeStatus=" + distributeStatus +
                ", prizeId='" + prizeId + '\'' +
                ", prizeType=" + prizeType +
                ", prizeName='" + prizeName + '\'' +
                ", prizeContent='" + prizeContent + '\'' +
                '}';
    }
}
