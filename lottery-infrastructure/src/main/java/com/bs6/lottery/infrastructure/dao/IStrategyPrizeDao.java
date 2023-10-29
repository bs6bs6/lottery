package com.bs6.lottery.infrastructure.dao;

import com.bs6.lottery.infrastructure.po.StrategyPrize;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface IStrategyPrizeDao {

    /**
     * 查询策略表详细配置
     * @param strategyId 策略ID
     * @return           返回结果
     */
    List<StrategyPrize> queryStrategyPrizeList(Long strategyId);

    /**
     * 查询无库存策略奖品ID
     * @param strategyId 策略ID
     * @return           返回结果
     */
    List<String> queryNoStockStrategyPrizeList(Long strategyId);

    /**
     * 扣减库存
     * @param strategyDetailReq 策略ID、奖品ID
     * @return                  返回结果
     */
    int deductStock(StrategyPrize strategyDetailReq);

    /**
     * 插入策略配置组
     *
     * @param list 策略配置组
     */
    void insertList(List<StrategyPrize> list);

}
