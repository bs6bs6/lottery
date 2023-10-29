package com.bs6.lottery.domain.strategy.model;

import java.util.List;

public class StrategyAgg {

    private Long strategyId;

    private StrategyBriefVO strategyBrief;

    private List<StrategyPrizeBriefVO> strategyPrizeList;

    public StrategyAgg() {
    }

    public StrategyAgg(Long strategyId, StrategyBriefVO strategyBrief, List<StrategyPrizeBriefVO> strategyPrizeList) {
        this.strategyId = strategyId;
        this.strategyBrief = strategyBrief;
        this.strategyPrizeList = strategyPrizeList;
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public StrategyBriefVO getStrategyBrief() {
        return strategyBrief;
    }

    public void setStrategyBrief(StrategyBriefVO strategy) {
        this.strategyBrief = strategy;
    }

    public List<StrategyPrizeBriefVO> getStrategyPrizeList() {
        return strategyPrizeList;
    }

    public void setStrategyDetailList(List<StrategyPrizeBriefVO> strategyPrizeList) {
        this.strategyPrizeList = strategyPrizeList;
    }

}
