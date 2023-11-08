package com.bs6.lottery.domain.activity.repository;

import com.bs6.lottery.common.Constants;
import com.bs6.lottery.domain.activity.model.*;

import java.util.List;


public interface IActivityRepository {

    /**
     * 添加活动配置
     * @param activity 活动配置
     */
    void addActivity(ActivityVO activity);

    /**
     * 添加奖品配置集合
     *
     * @param prizeList 奖品配置集合
     */
    void addPrize(List<PrizeVO> prizeList);

    /**
     * 添加策略配置
     *
     * @param strategy 策略配置
     */
    void addStrategy(StrategyVO strategy);

    /**
     * 添加策略明细配置
     *
     * @param strategyPrizeList 策略明细集合
     */
    void addStrategyPrizeList(List<StrategyPrizeVO> strategyPrizeList);

    /**
     * 变更活动状态
     *
     * @param activityId    活动ID
     * @param beforeStatus   修改前状态
     * @param afterStatus    修改后状态
     * @return              更新结果
     */
    boolean alterStatus(Long activityId, Enum<Constants.ActivityStatus> beforeStatus, Enum<Constants.ActivityStatus> afterStatus);

    /**
     * 查询活动账单信息【库存、状态、日期、个人参与次数】
     * @param req 参与活动请求
     * @return    活动账单
     */
    ActivityBillVO queryActivityBill(PartakeReq req);

    /**
     * 扣减活动库存
     * @param activityId   活动ID
     * @return      扣减结果
     */
    int subtractionActivityStock(Long activityId);


    List<ActivityVO> scanToDoActivityList(Long id);

    /**
     * 扣减活动库存，通过Redis
     *
     * @param uid        用户ID
     * @param activityId 活动ID
     * @param stockCount 总库存
     * @return 扣减结果
     */
    StockResult subtractionActivityStockByRedis(String uid, Long activityId, Integer stockCount);

    /**
     * 恢复活动库存，通过Redis 【如果非常异常，则需要进行缓存库存恢复，只保证不超卖的特性，所以不保证一定能恢复占用库存，另外最终可以由任务进行补偿库存】
     *
     * @param activityId    活动ID
     * @param tokenKey      分布式 KEY 用于清理
     * @param code          状态
     */
    void recoverActivityCacheStockByRedis(Long activityId, String tokenKey, String code);

    void updateActivityStock(ActivityPartakeRecordVO activityPartakeRecordVO);
}
