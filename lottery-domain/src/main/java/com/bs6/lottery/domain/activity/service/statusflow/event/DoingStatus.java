package com.bs6.lottery.domain.activity.service.statusflow.event;


import com.bs6.lottery.common.Constants;
import com.bs6.lottery.common.Result;
import com.bs6.lottery.domain.activity.service.statusflow.AbstractStatus;
import org.springframework.stereotype.Component;

@Component
public class DoingStatus extends AbstractStatus {

    @Override
    public Result arraignment(Long activityId, Enum<Constants.ActivityStatus> currentStatus) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "活动中不可提审");
    }

    @Override
    public Result checkPass(Long activityId, Enum<Constants.ActivityStatus> currentStatus) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "活动中不可审核通过");
    }

    @Override
    public Result checkRefuse(Long activityId, Enum<Constants.ActivityStatus> currentStatus) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "活动中不可审核拒绝");
    }

    @Override
    public Result checkRevoke(Long activityId, Enum<Constants.ActivityStatus> currentStatus) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "活动中不可撤销审核");
    }

    @Override
    public Result close(Long activityId, Enum<Constants.ActivityStatus> currentStatus) {
        boolean isSuccess = activityRepository.alterStatus(activityId, currentStatus, Constants.ActivityStatus.CLOSED);
        return isSuccess ? Result.buildResult(Constants.ResponseCode.SUCCESS, "活动关闭成功") : Result.buildErrorResult("活动状态变更失败");
    }

    @Override
    public Result open(Long activityId, Enum<Constants.ActivityStatus> currentStatus) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "活动中不可开启");
    }

    @Override
    public Result doing(Long activityId, Enum<Constants.ActivityStatus> currentStatus) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "活动中不可重复执行");
    }

}
