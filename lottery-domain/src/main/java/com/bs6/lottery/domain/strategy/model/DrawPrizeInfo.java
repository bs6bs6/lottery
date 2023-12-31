package com.bs6.lottery.domain.strategy.model;

import java.util.Date;

public class DrawPrizeInfo {

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

    private Integer strategyMode;

    /**
     * 发放奖品方式（1:即时、2:定时[含活动结束]、3:人工）
     */
    private Integer distributeType;
    /**
     * 发奖时间
     */
    private Date distributeDate;

    public DrawPrizeInfo() {
    }

    public DrawPrizeInfo(String prizeId, Integer prizeType, String prizeName,String prizeContent) {
        this.prizeId = prizeId;
        this.prizeType = prizeType;
        this.prizeName = prizeName;
        this.prizeContent = prizeContent;
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
}
