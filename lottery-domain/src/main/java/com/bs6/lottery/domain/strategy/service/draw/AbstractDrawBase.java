package com.bs6.lottery.domain.strategy.service.draw;

import com.bs6.lottery.domain.strategy.model.DrawReq;
import com.bs6.lottery.domain.strategy.model.DrawResult;
import com.bs6.lottery.domain.strategy.model.StrategyAgg;
import com.bs6.lottery.domain.strategy.repository.IStrategyRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDrawBase implements IDrawExec{

    @Autowired
    IStrategyRepository strategyRepository;

    @Override
    public DrawResult doDrawExec(DrawReq req) {
        StrategyAgg strategyAgg = strategyRepository.queryStrategyAgg(req.getStrategyId());

        return null;
    }



}
