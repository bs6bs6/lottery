package com.bs6.lottery.domain.strategy.repository;


import com.bs6.lottery.domain.strategy.model.PrizeBriefVO;
import com.bs6.lottery.domain.strategy.model.StrategyAgg;

import java.util.List;


public interface IStrategyRepository {

    StrategyAgg queryStrategyAgg(Long strategyId);

    PrizeBriefVO queryPrizeInfo(String prizeId);

    List<String> queryNoStockStrategyPrizeList(Long strategyId);

    /**
     * 扣减库存
     * @param strategyId 策略ID
     * @param prizeId    奖品ID
     * @return           扣减结果
     */
    boolean deductStock(Long strategyId, String prizeId);

}
