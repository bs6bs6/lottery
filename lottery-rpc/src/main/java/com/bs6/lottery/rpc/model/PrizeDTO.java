package com.bs6.lottery.rpc.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 奖品信息
 * @author: 小傅哥，微信：fustack
 * @date: 2021/10/16
 * @github: https://github.com/fuzhengwei
 * @Copyright: 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
public class PrizeDTO implements Serializable {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 奖品ID
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
     * 奖品内容「描述、奖品码、sku」
     */
    private String prizeContent;

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

    public PrizeDTO(String userId,Long activityId) {
        this.userId = userId;
        this.activityId = activityId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
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

    @Override
    public String toString() {
        return "PrizeDTO{" +
                "userId='" + userId + '\'' +
                ", activityId=" + activityId +
                ", prizeId='" + prizeId + '\'' +
                ", prizeType=" + prizeType +
                ", prizeName='" + prizeName + '\'' +
                ", prizeContent='" + prizeContent + '\'' +
                ", strategyMode=" + strategyMode +
                ", distributeType=" + distributeType +
                ", distributeDate=" + distributeDate +
                '}';
    }
}
