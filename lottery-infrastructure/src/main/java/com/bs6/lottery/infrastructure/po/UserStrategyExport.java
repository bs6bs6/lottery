package com.bs6.lottery.infrastructure.po;

import java.util.Date;


public class UserStrategyExport {

    /** 自增ID */
    private Long id;
    /** 用户ID */
    private String uid;
    /** 活动ID */
    private Long activityId;
    /** 订单ID */
    private Long orderId;
    /** 策略ID */
    private Long strategyId;
    /** 策略方式（1:单项概率、2:总体概率） */
    private Integer strategyMode;
    /** 发放奖品方式（1:即时、2:定时[含活动结束]、3:人工） */
    private Integer distributeType;
    /** 发奖时间 */
    private Date distributeDate;
    /** 发奖状态 */
    private Integer distributeStatus;
    /** 发奖ID */
    private String prizeId;
    /** 奖品类型（1:文字描述、2:兑换码、3:优惠券、4:实物奖品） */
    private Integer prizeType;
    /** 奖品名称 */
    private String prizeName;
    /** 奖品内容「文字描述、Key、码」 */
    private String prizeContent;
    /** 防重ID */
    private String uuid;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
