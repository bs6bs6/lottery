package com.bs6.lottery.domain.strategy.service.draw;

import com.bs6.lottery.common.Constants;
import com.bs6.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import com.bs6.lottery.domain.strategy.service.algorithm.impl.StaticAlgorithm;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DrawAlgoConfig {
    @Resource
    private StaticAlgorithm staticAlgorithm;

    protected static Map<Integer, IDrawAlgorithm> drawAlgorithmGroup = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        drawAlgorithmGroup.put(Constants.StrategyMode.STATIC.getCode(), staticAlgorithm);
    }
}
