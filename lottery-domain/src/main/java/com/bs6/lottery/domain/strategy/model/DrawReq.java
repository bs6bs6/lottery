package com.bs6.lottery.domain.strategy.model;

public class DrawReq {
    private String uid;

    private Long strategyId;

    public DrawReq() {
    }

    public DrawReq(String uid, Long strategyId) {
        this.uid = uid;
        this.strategyId = strategyId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uId) {
        this.uid = uId;
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

}
