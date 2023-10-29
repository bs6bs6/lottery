package com.bs6.lottery.domain.strategy.model;

import java.math.BigDecimal;

public class StrategyPrizeBriefVO {

    private Long strategyId;

    private String prizeId;


    private String prizeName;

    private Integer stockCount;

    /**
     * remaining stock
     */
    private Integer remainingStockCount;

    private BigDecimal prizeProbability;

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public String getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(String prizeId) {
        this.prizeId = prizeId;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Integer getRemainingStockCount() {
        return remainingStockCount;
    }

    public void setRemainingStockCount(Integer remainingStockCount) {
        this.remainingStockCount = remainingStockCount;
    }

    public BigDecimal getPrizeProbability() {
        return prizeProbability;
    }

    public void setPrizeProbability(BigDecimal prizeProbability) {
        this.prizeProbability = prizeProbability;
    }

    @Override
    public String toString() {
        return "StrategyPrizeBriefVO{" +
                "strategyId=" + strategyId +
                ", prizeId='" + prizeId + '\'' +
                ", prizeName='" + prizeName + '\'' +
                ", stockCount=" + stockCount +
                ", remainingStockCount=" + remainingStockCount +
                ", prizeProbability=" + prizeProbability +
                '}';
    }
}

