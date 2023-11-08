package com.bs6.lottery.domain.activity.service.partake;

import com.bs6.lottery.common.Constants;
import com.bs6.lottery.common.Result;
import com.bs6.lottery.domain.activity.model.*;
import com.bs6.lottery.domain.activity.repository.IActivityRepository;
import com.bs6.lottery.domain.support.ids.IIdGenerator;

import javax.annotation.Resource;
import java.util.Map;


public abstract class BaseActivityPartake implements IActivityPartake {
    @Resource
    protected IActivityRepository activityRepository;


    @Resource
    private Map<Constants.Ids, IIdGenerator> idGeneratorMap;
    @Override
    public PartakeResult doPartake(PartakeReq req) {
        // 1. check if there is any non-executed order[FROM user_take_activity WHERE state = 0]
        // That's because the request succeed in taking activity but failed when write down the order into DB
        UserTakeActivityVO userTakeActivityVO = this.queryNoConsumedTakeActivityOrder(req.getActivityId(), req.getUid());
        if (null != userTakeActivityVO) {
            return buildPartakeResult(userTakeActivityVO.getStrategyId(), userTakeActivityVO.getTakeId(),Constants.ResponseCode.NOT_CONSUMED_TAKE);
        }

        // 2.查询活动账单
        ActivityBillVO activityBillVO = queryActivityBill(req);
        if(null == activityBillVO){
            return new PartakeResult(Constants.ResponseCode.UN_ERROR.getCode(), "Activity does not exist");
        }

        // 3.活动信息校验处理【活动库存、状态、日期、个人参与次数】
        Result checkResult = this.checkActivityBill(req, activityBillVO);
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(checkResult.getCode())) {
            return new PartakeResult(checkResult.getCode(), checkResult.getInfo());
        }

        StockResult subtractionActivityResult = this.subtractionActivityStockByRedis(req.getUid(), req.getActivityId(), activityBillVO.getStockCount());

        // 4.扣减活动库存
//        Result subtractionActivityResult = this.subtractionActivityStock(req);
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(subtractionActivityResult.getCode())) {
            this.recoverActivityCacheStockByRedis(req.getActivityId(), subtractionActivityResult.getStockKey(), subtractionActivityResult.getCode());
            return new PartakeResult(subtractionActivityResult.getCode(), subtractionActivityResult.getInfo());
        }


        // 5. 插入领取活动信息【个人用户把活动信息写入到用户表】
        Long takeId = idGeneratorMap.get(Constants.Ids.SnowFlake).nextId();
        Result grabResult = this.grabActivity(req, activityBillVO, takeId);
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(grabResult.getCode())) {
            this.recoverActivityCacheStockByRedis(req.getActivityId(), subtractionActivityResult.getStockKey(), grabResult.getCode());
            return new PartakeResult(grabResult.getCode(), grabResult.getInfo());
        }

        // 6. 扣减活动库存，通过Redis End
        this.recoverActivityCacheStockByRedis(req.getActivityId(), subtractionActivityResult.getStockKey(), Constants.ResponseCode.SUCCESS.getCode());


        return buildPartakeResult(activityBillVO.getStrategyId(), takeId, activityBillVO.getStockCount(), activityBillVO.getStockSurplusCount(), Constants.ResponseCode.SUCCESS);
    }

    private PartakeResult buildPartakeResult(Long strategyId, Long takeId, Constants.ResponseCode code) {
        PartakeResult partakeResult = new PartakeResult(code.getCode(), code.getInfo());
        partakeResult.setStrategyId(strategyId);
        partakeResult.setTakeId(takeId);
        return partakeResult;
    }
    private PartakeResult buildPartakeResult(Long strategyId, Long takeId, Integer stockCount, Integer stockSurplusCount, Constants.ResponseCode code) {
        PartakeResult partakeResult = new PartakeResult(code.getCode(), code.getInfo());
        partakeResult.setStrategyId(strategyId);
        partakeResult.setTakeId(takeId);
        partakeResult.setStockCount(stockCount);
        partakeResult.setStockSurplusCount(stockSurplusCount);
        return partakeResult;
    }


    private ActivityBillVO queryActivityBill(PartakeReq req){
        return activityRepository.queryActivityBill(req);
    }

    protected abstract UserTakeActivityVO queryNoConsumedTakeActivityOrder(Long activityId, String uId);


    /**
     * 活动信息校验处理，把活动库存、状态、日期、个人参与次数
     *
     * @param partake 参与活动请求
     * @param bill    活动账单
     * @return 校验结果
     */
    protected abstract Result checkActivityBill(PartakeReq partake, ActivityBillVO bill);

    /**
     * 扣减活动库存
     *
     * @param req 参与活动请求
     * @return 扣减结果
     */
    protected abstract Result subtractionActivityStock(PartakeReq req);

    protected abstract StockResult subtractionActivityStockByRedis(String uid, Long activityId, Integer stockCount);

    protected abstract void recoverActivityCacheStockByRedis(Long activityId, String tokenKey, String code);

    /**
     * 领取活动
     *
     * @param partake 参与活动请求
     * @param bill    活动账单
     * @return 领取结果
     */
    protected abstract Result grabActivity(PartakeReq partake, ActivityBillVO bill,Long takeId);

}
