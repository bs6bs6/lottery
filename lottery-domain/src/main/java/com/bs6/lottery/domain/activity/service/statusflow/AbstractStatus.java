package com.bs6.lottery.domain.activity.service.statusflow;


import com.bs6.lottery.common.Constants;
import com.bs6.lottery.common.Result;
import com.bs6.lottery.domain.activity.repository.IActivityRepository;

import javax.annotation.Resource;


public abstract class AbstractStatus {

    @Resource
    protected IActivityRepository activityRepository;

    /**
     * 活动提审
     *
     * @param activityId   活动ID
     * @param currentstatus 当前状态
     * @return 执行结果
     */
    public abstract Result arraignment(Long activityId, Enum<Constants.ActivityStatus> currentstatus);

    /**
     * 审核通过
     *
     * @param activityId   活动ID
     * @param currentstatus 当前状态
     * @return 执行结果
     */
    public abstract Result checkPass(Long activityId, Enum<Constants.ActivityStatus> currentstatus);

    /**
     * 审核拒绝
     *
     * @param activityId   活动ID
     * @param currentstatus 当前状态
     * @return 执行结果
     */
    public abstract Result checkRefuse(Long activityId, Enum<Constants.ActivityStatus> currentstatus);

    /**
     * 撤审撤销
     *
     * @param activityId   活动ID
     * @param currentstatus 当前状态
     * @return 执行结果
     */
    public abstract Result checkRevoke(Long activityId, Enum<Constants.ActivityStatus> currentstatus);

    /**
     * 活动关闭
     *
     * @param activityId   活动ID
     * @param currentstatus 当前状态
     * @return 执行结果
     */
    public abstract Result close(Long activityId, Enum<Constants.ActivityStatus> currentstatus);

    /**
     * 活动开启
     *
     * @param activityId   活动ID
     * @param currentstatus 当前状态
     * @return 执行结果
     */
    public abstract Result open(Long activityId, Enum<Constants.ActivityStatus> currentstatus);

    /**
     * 活动执行
     *
     * @param activityId   活动ID
     * @param currentstatus 当前状态
     * @return 执行结果
     */
    public abstract Result doing(Long activityId, Enum<Constants.ActivityStatus> currentstatus);

}
