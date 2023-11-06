package com.bs6.lottery.infrastructure.repository;

import com.bs6.lottery.common.Constants;
import com.bs6.lottery.domain.activity.model.*;
import com.bs6.lottery.domain.activity.repository.IActivityRepository;
import com.bs6.lottery.infrastructure.dao.*;
import com.bs6.lottery.infrastructure.po.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Component
public class ActivityRepository implements IActivityRepository {

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
        activityBillVO.setTakeCount(activity.getTakeCount());
        activityBillVO.setStockSurplusCount(activity.getStockSurplusCount());
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
}
