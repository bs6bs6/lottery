package com.bs6.lottery.domain.activity.service.partake.impl;

import com.bs6.lottery.common.Constants;
import com.bs6.lottery.common.Result;
import com.bs6.lottery.domain.activity.model.*;
import com.bs6.lottery.domain.activity.repository.IActivityRepository;
import com.bs6.lottery.domain.activity.repository.IUserTakeActivityRepository;
import com.bs6.lottery.domain.activity.service.partake.BaseActivityPartake;
import com.bs6.lottery.domain.support.ids.IIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ActivityPartakeImpl extends BaseActivityPartake {

    private Logger logger = LoggerFactory.getLogger(ActivityPartakeImpl.class);

    @Resource
    private IActivityRepository activityRepository;
    @Resource
    private IUserTakeActivityRepository userTakeActivityRepository;

    @Resource
    private TransactionTemplate transactionTemplate;


    @Override
    protected Result checkActivityBill(PartakeReq partake, ActivityBillVO bill) {
        // 校验：活动状态
        if (!Constants.ActivityStatus.RUNNING.getCode().equals(bill.getStatus())) {
            logger.warn("活动当前状态非可用 state：{}", bill.getStatus());
            return Result.buildResult(Constants.ResponseCode.UN_ERROR, "活动当前状态非可用");
        }

        // 校验：活动日期
        if (bill.getBeginDateTime().after(partake.getPartakeDate()) || bill.getEndDateTime().before(partake.getPartakeDate())) {
            logger.warn("活动时间范围非可用 beginDateTime：{} endDateTime：{}", bill.getBeginDateTime(), bill.getEndDateTime());
            return Result.buildResult(Constants.ResponseCode.UN_ERROR, "活动时间范围非可用");
        }

        // 校验：活动库存
        if (bill.getStockSurplusCount() <= 0) {
            logger.warn("活动剩余库存非可用 stockSurplusCount：{}", bill.getStockSurplusCount());
            return Result.buildResult(Constants.ResponseCode.UN_ERROR, "活动剩余库存非可用");
        }

        // 校验：个人库存 - 个人活动剩余可领取次数
        if (bill.getUserTakeLeftCount()!=null && bill.getUserTakeLeftCount() <= 0) {
            logger.warn("个人领取次数非可用 userTakeLeftCount：{}", bill.getUserTakeLeftCount());
            return Result.buildResult(Constants.ResponseCode.UN_ERROR, "User don't have ");
        }

        return Result.buildSuccessResult();
    }

    @Override
    protected Result subtractionActivityStock(PartakeReq req) {
        int count = activityRepository.subtractionActivityStock(req.getActivityId());
        if (0 == count) {
            logger.error("扣减活动库存失败 activityId：{}", req.getActivityId());
            return Result.buildResult(Constants.ResponseCode.NO_UPDATE);
        }
        return Result.buildSuccessResult();
    }

    @Override
    public Result grabActivity(PartakeReq partake, ActivityBillVO bill,Long takeId) {
        //explicitly execute transaction
        return transactionTemplate.execute(status -> {
            try {
                // 扣减个人已参与次数
                int updateCount = userTakeActivityRepository.subtractionLeftCount(bill.getActivityId(), bill.getActivityName(), bill.getTakeCount(), bill.getUserTakeLeftCount(), partake.getUid(), partake.getPartakeDate());
                if (0 == updateCount) {
                    status.setRollbackOnly();
                    logger.error("领取活动，扣减个人已参与次数失败 activityId：{} uId：{}", partake.getActivityId(), partake.getUid());
                    return Result.buildResult(Constants.ResponseCode.NO_UPDATE);
                }

                // 写入领取活动记录
                userTakeActivityRepository.takeActivity(bill.getActivityId(), bill.getActivityName(), bill.getStrategyId(),bill.getTakeCount(), bill.getUserTakeLeftCount(), partake.getUid(), partake.getPartakeDate(), takeId);
            } catch (DuplicateKeyException e) {
                status.setRollbackOnly();
                logger.error("领取活动，唯一索引冲突 activityId：{} uId：{}", partake.getActivityId(), partake.getUid(), e);
                return Result.buildResult(Constants.ResponseCode.INDEX_DUP);
            }
            return Result.buildSuccessResult();
        });
    }

    @Override
    protected UserTakeActivityVO queryNoConsumedTakeActivityOrder(Long activityId, String uId) {
        return userTakeActivityRepository.queryNoConsumedTakeActivityOrder(activityId, uId);
    }

    @Override
    public Result recordDrawOrder(DrawOrderVO drawOrder) {
            return transactionTemplate.execute(status -> {
                try {
                    // lock activity, confirm that it is actually taken by user，status 0 -> 1
                    int lockCount = userTakeActivityRepository.lockTackActivity(drawOrder.getUid(), drawOrder.getActivityId(), drawOrder.getTakeId());
                    if (0 == lockCount) {
                        status.setRollbackOnly();
                        logger.error("记录中奖单，个人参与活动抽奖已消耗完 activityId：{} uId：{}", drawOrder.getActivityId(), drawOrder.getUid());
                        return Result.buildResult(Constants.ResponseCode.NO_UPDATE);
                    }

                    // 保存抽奖信息
                    userTakeActivityRepository.saveUserStrategyExport(drawOrder);
                } catch (DuplicateKeyException e) {
                    status.setRollbackOnly();
                    logger.error("记录中奖单，唯一索引冲突 activityId：{} uId：{}", drawOrder.getActivityId(), drawOrder.getUid(), e);
                    return Result.buildResult(Constants.ResponseCode.INDEX_DUP);
                }
                return Result.buildSuccessResult();
            });
    }

    @Override
    public void updateInvoiceMqState(String uId, Long orderId, Integer mqState) {
        userTakeActivityRepository.updateInvoiceMqStatus(uId, orderId, mqState);
    }

    @Override
    public List<InvoiceVO> scanInvoiceMqState() {
        return userTakeActivityRepository.scanInvoiceMqState();
    }

    @Override
    protected StockResult subtractionActivityStockByRedis(String uid, Long activityId, Integer stockCount) {
        return activityRepository.subtractionActivityStockByRedis(uid, activityId, stockCount);

    }

    @Override
    protected void recoverActivityCacheStockByRedis(Long activityId, String tokenKey, String code) {
         activityRepository.recoverActivityCacheStockByRedis(activityId, tokenKey, code);
    }

    @Override
    public void updateActivityStock(ActivityPartakeRecordVO activityPartakeRecordVO) {
         activityRepository.updateActivityStock(activityPartakeRecordVO);
    }
}
