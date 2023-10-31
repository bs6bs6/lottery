package com.bs6.lottery.domain.strategy.service.algorithm;

import com.bs6.lottery.common.Constants;
import com.bs6.lottery.domain.strategy.model.PrizeProbabilityInfo;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseAlgorithm implements IDrawAlgorithm {

    /**
     * 斐波那契散列增量，逻辑：黄金分割点：(√5 - 1) / 2 = 0.6180339887，Math.pow(2, 32) * 0.6180339887 = 0x61c88647
     */
    private final int HASH_INCREMENT = 0x61c88647;

    /**
     * 数组初始化长度 128，保证数据填充时不发生碰撞的最小初始化值
     */
    private final int RATE_TUPLE_LENGTH = 128;

    /**
     * 存放概率与奖品对应的散列结果，strategyId -> rateTuple
     */
    protected Map<Long, String[]> rateTupleMap = new ConcurrentHashMap<>();

    /**
     * 奖品区间概率值，strategyId -> [awardId->begin、awardId->end]
     */
    protected Map<Long, List<PrizeProbabilityInfo>> prizeProbabilityInfoMap = new ConcurrentHashMap<>();

    @Override
    public synchronized void initRateTuple(Long strategyId, Integer strategyMode, List<PrizeProbabilityInfo> prizeProbabilityInfoList) {

        // check if the prizeProbabilityInfo list cache exist
//        if (probabilityInfoIsExist(strategyId)){
//            return;
//        }

        // 保存奖品概率信息
        prizeProbabilityInfoMap.put(strategyId, prizeProbabilityInfoList);

        // dynamic probability tuple doesn't need to be cached into HashMap(Cuz this part is mutable)
        if (Constants.StrategyMode.DYNAMIC.getCode().equals(strategyMode)) {
            return;
        }

        String[] rateTuple = rateTupleMap.computeIfAbsent(strategyId, k -> new String[RATE_TUPLE_LENGTH]);

        int cursorVal = 0;
        for (PrizeProbabilityInfo prizeProbabilityInfo : prizeProbabilityInfoList) {
            int rateVal = prizeProbabilityInfo.getPrizeProbability().multiply(new BigDecimal(100)).intValue();

            // loop and put probability range into rateTuple(map 1 to 128)
            for (int i = cursorVal + 1; i <= (rateVal + cursorVal); i++) {
                rateTuple[hashIdx(i)] = prizeProbabilityInfo.getPrizeId();
            }

            cursorVal += rateVal;

        }
    }

    @Override
    public boolean probabilityInfoIsExist(Long strategyId) {
        return prizeProbabilityInfoMap.containsKey(strategyId);
    }

    /**
     * Fibonacci，calculate hash index
     *
     * @param val value
     * @return index
     */
    protected int hashIdx(int val) {
        int hashCode = val * HASH_INCREMENT + HASH_INCREMENT;
        return hashCode & (RATE_TUPLE_LENGTH - 1);
    }

    /**
     * 生成百位随机抽奖码
     *
     * @return random val
     */
    protected int generateSecureRandomIntCode(int bound) {
        return new SecureRandom().nextInt(bound) + 1;
    }

}
