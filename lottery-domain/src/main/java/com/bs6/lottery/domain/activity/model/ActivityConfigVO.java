package com.bs6.lottery.domain.activity.model;

import java.util.List;

public class ActivityConfigVO {
    /** 活动配置 */
    private ActivityVO activity;

    /** 策略配置(含策略明细) */
    private StrategyVO strategy;

    /** 奖品配置 */
    private List<PrizeVO> prizeList;

    public ActivityConfigVO() {
    }

    public ActivityConfigVO(ActivityVO activity, StrategyVO strategy, List<PrizeVO> prizeList) {
        this.activity = activity;
        this.strategy = strategy;
        this.prizeList = prizeList;
    }

    public ActivityVO getActivity() {
        return activity;
    }

    public void setActivity(ActivityVO activity) {
        this.activity = activity;
    }

    public StrategyVO getStrategy() {
        return strategy;
    }

    public void setStrategy(StrategyVO strategy) {
        this.strategy = strategy;
    }

    public List<PrizeVO> getPrizeList() {
        return prizeList;
    }

    public void setPrizeListList(List<PrizeVO> prizeList) {
        this.prizeList = prizeList;
    }
}
