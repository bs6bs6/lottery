package com.bs6.lottery.domain.strategy.service.algorithm.impl;

import com.bs6.lottery.domain.strategy.model.PrizeProbabilityInfo;
import com.bs6.lottery.domain.strategy.service.algorithm.BaseAlgorithm;
import com.bs6.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class StaticAlgorithm extends BaseAlgorithm {

    @Override
    public String randomDraw(Long strategyId, List<String> excludeAwardIds) {

        // 获取策略对应的元祖
        String[] rateTuple = super.rateTupleMap.get(strategyId);
        assert rateTuple != null;

        // use Secure Random to get a real random number
        int randomVal = this.generateSecureRandomIntCode(100);
        int idx = super.hashIdx(randomVal);

        // 返回结果
        String prizeId = rateTuple[idx];

        // 如果中奖ID命中排除奖品列表，则返回NULL
        if (excludeAwardIds.contains(prizeId)) {
            return null;
        }
        return prizeId;
    }

}
