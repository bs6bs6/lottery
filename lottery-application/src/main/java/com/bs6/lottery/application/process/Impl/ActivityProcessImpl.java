package com.bs6.lottery.application.process.Impl;

import com.bs6.lottery.application.process.IActivityProcess;
import com.bs6.lottery.application.process.model.DrawProcessReq;
import com.bs6.lottery.application.process.model.DrawProcessResult;
import com.bs6.lottery.common.Constants;
import com.bs6.lottery.domain.activity.model.PartakeReq;
import com.bs6.lottery.domain.activity.model.PartakeResult;
import com.bs6.lottery.domain.activity.service.partake.IActivityPartake;
import com.bs6.lottery.domain.strategy.model.DrawPrizeInfo;
import com.bs6.lottery.domain.strategy.model.DrawReq;
import com.bs6.lottery.domain.strategy.model.DrawResult;
import com.bs6.lottery.domain.strategy.service.draw.IDrawExec;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;


@Service
public class ActivityProcessImpl implements IActivityProcess {

    @Resource
    IActivityPartake activityPartake;
    @Resource
    IDrawExec drawExec;
    @Override
    public DrawProcessResult doDrawProcess(DrawProcessReq req) {
        PartakeReq partakeReq = new PartakeReq(req.getUid(), req.getActivityId(),new Date());
        PartakeResult partakeResult = activityPartake.doPartake(partakeReq);

        if(partakeResult.getCode().equals(Constants.ResponseCode.UN_ERROR.getCode())){
            return new DrawProcessResult(Constants.ResponseCode.UN_ERROR.getCode(),"No activity in stock, sorry~");
        }

        DrawReq drawReq = new DrawReq(req.getUid(),partakeResult.getStrategyId());

        DrawResult drawResult = drawExec.doDrawExec(drawReq);
        if(drawResult.getDrawStatus().equals(Constants.DrawStatus.LOSE.getCode())){
            return new DrawProcessResult(Constants.ResponseCode.UN_ERROR.getCode(),"You lose, haha!");
        }

        DrawPrizeInfo drawPrizeInfo = drawResult.getDrawPrizeInfo();

        return new DrawProcessResult(Constants.ResponseCode.SUCCESS.getCode(),Constants.ResponseCode.SUCCESS.getInfo(),drawPrizeInfo);
    }
}
