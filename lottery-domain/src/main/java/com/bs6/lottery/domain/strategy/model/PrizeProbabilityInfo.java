package com.bs6.lottery.domain.strategy.model;

import java.math.BigDecimal;

public class PrizeProbabilityInfo {


    private String prizeId;

    private BigDecimal prizeProbability;

    public PrizeProbabilityInfo() {
    }

    public PrizeProbabilityInfo(String prizeId, BigDecimal prizeProbability) {
        this.prizeId = prizeId;
        this.prizeProbability = prizeProbability;
    }

    public String getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(String prizeId) {
        this.prizeId = prizeId;
    }

    public BigDecimal getPrizeProbability() {
        return prizeProbability;
    }

    public void setPrizeProbability(BigDecimal prizeProbability) {
        this.prizeProbability = prizeProbability;
    }
}
