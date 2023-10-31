package com.bs6.lottery.domain.strategy.service.draw.impl;

import com.alibaba.fastjson.JSON;
import com.bs6.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import com.bs6.lottery.domain.strategy.service.draw.AbstractDrawBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("drawExec")
public class DrawExecImpl extends AbstractDrawBase {

    private Logger logger = LoggerFactory.getLogger(DrawExecImpl.class);

    //This method can be customized based on the specific Draw implementation.
    @Override
    protected List<String> queryExcludePrizeIds(Long strategyId) {
        List<String> prizeList = strategyRepository.queryNoStockStrategyPrizeList(strategyId);
        logger.info("执行抽奖策略 strategyId：{}，无库存排除奖品列表ID集合 awardList：{}", strategyId, JSON.toJSONString(prizeList));
        return prizeList;
    }

    @Override
    protected String drawAlgorithm(Long strategyId, IDrawAlgorithm drawAlgorithm, List<String> excludePrizeIds) {
        // 执行抽奖
        String prizeId = drawAlgorithm.randomDraw(strategyId, excludePrizeIds);

        // 判断抽奖结果
        if (null == prizeId) {
            return null;
        }

        /*
         * 扣减库存，暂时采用数据库行级锁的方式进行扣减库存，后续优化为 Redis 分布式锁扣减 decr/incr
         * 注意：通常数据库直接锁行记录的方式并不能支撑较大体量的并发，但在分库分表下的正常数据流量下的个人数据记录中，是可以使用行级锁的，因为他只影响到自己的记录，不会影响到其他人
         */
        boolean isSuccess = strategyRepository.deductStock(strategyId, prizeId);

        // 返回结果，库存扣减成功返回奖品ID，否则返回NULL 「在实际的业务场景中，如果中奖奖品库存为空，则会发送兜底奖品，比如各类券」
        return isSuccess ? prizeId : null;
    }
}
