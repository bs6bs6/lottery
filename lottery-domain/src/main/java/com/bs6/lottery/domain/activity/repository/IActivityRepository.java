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

}
