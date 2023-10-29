package com.bs6.lottery.domain.strategy.model;

import java.util.Date;

public class StrategyBriefVO {

    private Long strategyId;

    private String strategyDescription;

    /**
     * StrategyMode「1:static、2:dynamic」
     */
    private Integer strategyMode;

    /**
     * Distribute type「1:immediate、2:scheduled[Can happen After Activity]、3:Manually」
     */
    private Integer distributeType;

    private Date distributeDate;

    private String extraInfo;

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public String getStrategyDescription() {
        return strategyDescription;
    }

    public void setStrategyDescription(String strategyDescription) {
        this.strategyDescription = strategyDescription;
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

    public void setDistributeType(Integer grantType) {
        this.distributeType = grantType;
    }

    public Date getDistributeDateDate() {
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
}
