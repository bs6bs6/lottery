package com.bs6.lottery.domain.activity.service.deploy.impl;


import com.alibaba.fastjson.JSON;
import com.bs6.lottery.domain.activity.model.*;
import com.bs6.lottery.domain.activity.repository.IActivityRepository;
import com.bs6.lottery.domain.activity.service.deploy.IActivityDeploy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
public class ActivityDeployImpl implements IActivityDeploy {

    private Logger logger = LoggerFactory.getLogger(ActivityDeployImpl.class);

    @Resource
    private IActivityRepository activityRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createActivity(ActivityConfigReq req) {
        logger.info("创建活动配置开始，activityId：{}", req.getActivityId());
        ActivityConfigVO activityConfigVO = req.getActivityConfigVO();
        try {
            // 添加活动配置
            ActivityVO activity = activityConfigVO.getActivity();
            activityRepository.addActivity(activity);

            // 添加奖品配置
            List<PrizeVO> awardList = activityConfigVO.getPrizeList();
            activityRepository.addPrize(awardList);

            // 添加策略配置
            StrategyVO strategy = activityConfigVO.getStrategy();
            activityRepository.addStrategy(strategy);

            // 添加策略明细配置
            List<StrategyPrizeVO> strategyPrizeList = activityConfigVO.getStrategy().getStrategyPrizeList();
            activityRepository.addStrategyPrizeList(strategyPrizeList);

            logger.info("创建活动配置完成，activityId：{}", req.getActivityId());
        } catch (DuplicateKeyException e) {
            logger.error("创建活动配置失败，唯一索引冲突 activityId：{} reqJson：{}", req.getActivityId(), JSON.toJSONString(req), e);
            throw e;
        }
    }

    @Override
    public void updateActivity(ActivityConfigReq req) {
        // TODO: 非核心功能后续补充
    }

    @Override
    public List<ActivityVO> scanToDoActivityList(Long id) {
        return activityRepository.scanToDoActivityList(id);
    }

}
