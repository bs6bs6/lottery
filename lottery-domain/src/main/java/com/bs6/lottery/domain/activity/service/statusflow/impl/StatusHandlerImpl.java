package com.bs6.lottery.domain.activity.service.statusflow.impl;


import com.bs6.lottery.common.Constants;
import com.bs6.lottery.common.Result;
import com.bs6.lottery.domain.activity.service.statusflow.IStatusHandler;
import com.bs6.lottery.domain.activity.service.statusflow.StatusConfig;
import org.springframework.stereotype.Service;

@Service
public class StatusHandlerImpl extends StatusConfig implements IStatusHandler {

    @Override
    public Result arraignment(Long activityId, Enum<Constants.ActivityStatus> currentStatus) {
        return statusGroup.get(currentStatus).arraignment(activityId, currentStatus);
    }

    @Override
    public Result checkPass(Long activityId, Enum<Constants.ActivityStatus> currentStatus) {
        return statusGroup.get(currentStatus).checkPass(activityId, currentStatus);
    }

    @Override
    public Result checkRefuse(Long activityId, Enum<Constants.ActivityStatus> currentStatus) {
        return statusGroup.get(currentStatus).checkRefuse(activityId, currentStatus);
    }

    @Override
    public Result checkRevoke(Long activityId, Enum<Constants.ActivityStatus> currentStatus) {
        return statusGroup.get(currentStatus).checkRevoke(activityId, currentStatus);
    }

    @Override
    public Result close(Long activityId, Enum<Constants.ActivityStatus> currentStatus) {
        return statusGroup.get(currentStatus).close(activityId, currentStatus);
    }

    @Override
    public Result open(Long activityId, Enum<Constants.ActivityStatus> currentStatus) {
        return statusGroup.get(currentStatus).open(activityId, currentStatus);
    }

    @Override
    public Result doing(Long activityId, Enum<Constants.ActivityStatus> currentStatus) {
        return statusGroup.get(currentStatus).doing(activityId, currentStatus);
    }

}
