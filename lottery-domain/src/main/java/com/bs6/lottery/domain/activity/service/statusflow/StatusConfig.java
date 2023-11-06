package com.bs6.lottery.domain.activity.service.statusflow;


import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.bs6.lottery.common.Constants;
import com.bs6.lottery.domain.activity.service.statusflow.event.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StatusConfig {

    @Resource
    private ArraignmentStatus arraignmentstatus;
    @Resource
    private CloseStatus closestatus;
    @Resource
    private DoingStatus doingstatus;
    @Resource
    private EditingStatus editingstatus;
    @Resource
    private OpenStatus openstatus;
    @Resource
    private PassStatus passstatus;
    @Resource
    private RefuseStatus refusestatus;

    protected Map<Enum<Constants.ActivityStatus>, AbstractStatus> statusGroup = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        statusGroup.put(Constants.ActivityStatus.REVIEW, arraignmentstatus);
        statusGroup.put(Constants.ActivityStatus.CANCELED, closestatus);
        statusGroup.put(Constants.ActivityStatus.RUNNING, doingstatus);
        statusGroup.put(Constants.ActivityStatus.EDITING, editingstatus);
        statusGroup.put(Constants.ActivityStatus.OPENED, openstatus);
        statusGroup.put(Constants.ActivityStatus.APPROVED,passstatus);
        statusGroup.put(Constants.ActivityStatus.REFUSED, refusestatus);
    }

}
