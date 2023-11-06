package com.bs6.lottery.domain.activity.service.statusflow.event;


import com.bs6.lottery.common.Constants;
import com.bs6.lottery.common.Result;
import com.bs6.lottery.domain.activity.service.statusflow.AbstractStatus;
import org.springframework.stereotype.Component;

@Component
public class PassStatus extends AbstractStatus {

    @Override
    public Result arraignment(Long activityId, Enum<Constants.ActivityStatus> currentStatus) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "已审核状态不可重复提审");
    }

    @Override
    public Result checkPass(Long activityId, Enum<Constants.ActivityStatus> currentStatus) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "已审核状态不可重复审核");
    }

    @Override
    public Result checkRefuse(Long activityId, Enum<Constants.ActivityStatus> currentStatus) {
        boolean isSuccess = activityRepository.alterStatus(activityId, currentStatus, Constants.ActivityStatus.REFUSED);
        return isSuccess ? Result.buildResult(Constants.ResponseCode.SUCCESS, "活动审核拒绝完成") : Result.buildErrorResult("活动状态变更失败");
    }

    @Override
    public Result checkRevoke(Long activityId, Enum<Constants.ActivityStatus> currentStatus) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "审核通过不可撤销(可先拒绝审核)");
    }

    @Override
    public Result close(Long activityId, Enum<Constants.ActivityStatus> currentStatus) {
        boolean isSuccess = activityRepository.alterStatus(activityId, currentStatus, Constants.ActivityStatus.CLOSED);
        return isSuccess ? Result.buildResult(Constants.ResponseCode.SUCCESS, "活动审核关闭完成") : Result.buildErrorResult("活动状态变更失败");
    }

    @Override
    public Result open(Long activityId, Enum<Constants.ActivityStatus> currentStatus) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "非关闭活动不可开启");
    }

    @Override
    public Result doing(Long activityId, Enum<Constants.ActivityStatus> currentStatus) {
        boolean isSuccess = activityRepository.alterStatus(activityId, currentStatus, Constants.ActivityStatus.RUNNING);
        return isSuccess ? Result.buildResult(Constants.ResponseCode.SUCCESS, "活动变更活动中完成") : Result.buildErrorResult("活动状态变更失败");
    }

}
