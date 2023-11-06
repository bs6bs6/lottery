package com.bs6.lottery.domain.activity.model;

import java.util.Date;
import java.util.List;

public class StrategyVO {

    /**
     * 策略ID
     */
    private Long strategyId;

    /**
     * 策略描述
     */
    private String strategyDesc;

    /**
     * 策略方式「1:单项概率、2:总体概率」
     */
    private Integer strategyMode;

    /**
     * 发放奖品方式「1:即时、2:定时[含活动结束]、3:人工」
     */
    private Integer distributeType;

    /**
     * 发放奖品时间
     */
    private Date distributeDate;

    /**
     * 扩展信息
     */
    private String extraInfo;

    /**
     * 策略详情配置
     */
    private List<StrategyPrizeVO> strategyPrizeList;

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public String getStrategyDesc() {
        return strategyDesc;
    }

    public void setStrategyDesc(String strategyDesc) {
        this.strategyDesc = strategyDesc;
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

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public List<StrategyPrizeVO> getStrategyPrizeList() {
        return strategyPrizeList;
    }

    public void setStrategyPrizeList(List<StrategyPrizeVO> strategyPrizeList) {
        this.strategyPrizeList = strategyPrizeList;
    }
}
