package com.bs6.lottery.infrastructure.repository;

import com.bs6.lottery.common.Constants;
import com.bs6.lottery.domain.activity.model.*;
import com.bs6.lottery.domain.activity.repository.IActivityRepository;
import com.bs6.lottery.infrastructure.dao.*;
import com.bs6.lottery.infrastructure.po.*;
import com.bs6.lottery.infrastructure.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Component
public class ActivityRepository implements IActivityRepository {
    private Logger logger = LoggerFactory.getLogger(ActivityRepository.class);

    @Resource
    private IActivityDao activityDao;
    @Resource
    private IPrizeDao prizeDao;
    @Resource
    private IStrategyDao strategyDao;
    @Resource
    private IStrategyPrizeDao strategyPrizeDao;
    @Resource
    private IUserTakeActivityCountDao userTakeActivityCountDao;
    @Resource
    RedisUtil redisUtil;

    @Override
    public void addActivity(ActivityVO activity) {
        Activity req = new Activity();
        BeanUtils.copyProperties(activity, req);
        activityDao.insert(req);
    }

    @Override
    public void addPrize(List<PrizeVO> awardList) {
        List<Prize> req = new ArrayList<>();
        for (PrizeVO awardVO : awardList) {
            Prize prize = new Prize();
            BeanUtils.copyProperties(awardVO, prize);
            req.add(prize);
        }
        prizeDao.insertList(req);
    }

    @Override
    public void addStrategy(StrategyVO strategy) {
        Strategy req = new Strategy();
        BeanUtils.copyProperties(strategy, req);
        strategyDao.insert(req);
    }

    @Override
    public void addStrategyPrizeList(List<StrategyPrizeVO> strategyDetailList) {
        List<StrategyPrize> req = new ArrayList<>();
        for (StrategyPrizeVO strategyDetailVO : strategyDetailList) {
            StrategyPrize strategyDetail = new StrategyPrize();
            BeanUtils.copyProperties(strategyDetailVO, strategyDetail);
            req.add(strategyDetail);
        }
        strategyPrizeDao.insertList(req);
    }

    @Override
    public boolean alterStatus(Long activityId, Enum<Constants.ActivityStatus> beforestatus, Enum<Constants.ActivityStatus> afterstatus) {
        AlterStatusVO alterStatusVO = new AlterStatusVO(activityId, ((Constants.ActivityStatus) beforestatus).getCode(), ((Constants.ActivityStatus) afterstatus).getCode());
        int count = activityDao.alterStatus(alterStatusVO);
        return 1 == count;
    }

    @Override
    public ActivityBillVO queryActivityBill(PartakeReq req) {

        // 查询活动信息
        Activity activity = activityDao.queryActivityById(req.getActivityId());
        if(null == activity){
            return null;
        }

        // 从缓存中获取库存
        Object usedStockCountObj =  redisUtil.get(Constants.RedisKey.KEY_LOTTERY_ACTIVITY_STOCK_COUNT(req.getActivityId()));


        // 查询领取次数
        UserTakeActivityCount userTakeActivityCountReq = new UserTakeActivityCount();
        userTakeActivityCountReq.setUid(req.getUid());
        userTakeActivityCountReq.setActivityId(req.getActivityId());
        UserTakeActivityCount userTakeActivityCount = userTakeActivityCountDao.queryUserTakeActivityCount(userTakeActivityCountReq);

        // 封装结果信息
        ActivityBillVO activityBillVO = new ActivityBillVO();
        activityBillVO.setUid(req.getUid());
        activityBillVO.setActivityId(req.getActivityId());
        activityBillVO.setActivityName(activity.getActivityName());
        activityBillVO.setBeginDateTime(activity.getBeginDateTime());
        activityBillVO.setEndDateTime(activity.getEndDateTime());
        activityBillVO.setStockCount(activity.getStockCount());
        activityBillVO.setTakeCount(activity.getTakeCount());
        activityBillVO.setStockSurplusCount(null == usedStockCountObj?activity.getStockSurplusCount() : activity.getStockCount() - Integer.parseInt(String.valueOf(usedStockCountObj)));
        activityBillVO.setStrategyId(activity.getStrategyId());
        activityBillVO.setStatus(activity.getStatus());
        activityBillVO.setUserTakeLeftCount(null == userTakeActivityCount ? null : userTakeActivityCount.getLeftCount());

        return activityBillVO;
    }

    @Override
    public int subtractionActivityStock(Long activityId) {
        return activityDao.subtractionActivityStock(activityId);
    }

    @Override
    public List<ActivityVO> scanToDoActivityList(Long id) {
        List<Activity> activityList = activityDao.scanToDoActivityList(id);
        List<ActivityVO> activityVOList = new ArrayList<>(activityList.size());
        for (Activity activity : activityList) {
            ActivityVO activityVO = new ActivityVO();
            activityVO.setId(activity.getId());
            activityVO.setActivityId(activity.getActivityId());
            activityVO.setActivityName(activity.getActivityName());
            activityVO.setBeginDateTime(activity.getBeginDateTime());
            activityVO.setEndDateTime(activity.getEndDateTime());
            activityVO.setStatus(activity.getStatus());
            activityVOList.add(activityVO);
        }
        return activityVOList;
    }

    @Override
    public StockResult subtractionActivityStockByRedis(String uid, Long activityId, Integer stockCount) {
        String stockKey = Constants.RedisKey.KEY_LOTTERY_ACTIVITY_STOCK_COUNT_TOKEN(activityId,stockCount);
        Integer stockUsedCount = (int) redisUtil.incr(stockKey,1);
        if (stockUsedCount > stockCount) {
            redisUtil.decr(stockKey, 1);
            return new StockResult(Constants.ResponseCode.OUT_OF_STOCK.getCode(), Constants.ResponseCode.OUT_OF_STOCK.getInfo());
        }
        String stockTokenKey = Constants.RedisKey.KEY_LOTTERY_ACTIVITY_STOCK_COUNT_TOKEN(activityId, stockUsedCount);
        // 5. 使用 Redis.setNx 加一个分布式锁
        boolean lockToken = redisUtil.setNx(stockTokenKey, 350L);
        if (!lockToken) {
            logger.info("抽奖活动{}用户秒杀{}扣减库存，分布式锁失败：{}", activityId, uid, stockTokenKey);
            return new StockResult(Constants.ResponseCode.ERR_TOKEN.getCode(), Constants.ResponseCode.ERR_TOKEN.getInfo());
        }

        return new StockResult(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo(), stockTokenKey, stockCount - stockUsedCount);

    }

    @Override
    public void recoverActivityCacheStockByRedis(Long activityId, String tokenKey, String code) {
        // 删除分布式锁 Key
        redisUtil.del(tokenKey);
    }

    @Override
    public void updateActivityStock(ActivityPartakeRecordVO activityPartakeRecordVO) {
        Activity activity = new Activity();
        activity.setActivityId(activityPartakeRecordVO.getActivityId());
        activity.setStockSurplusCount(activityPartakeRecordVO.getStockSurplusCount());
        activityDao.updateActivityStock(activity);
    }
}
