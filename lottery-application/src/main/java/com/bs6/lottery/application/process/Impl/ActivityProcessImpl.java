package com.bs6.lottery.application.process.Impl;

import com.bs6.lottery.application.process.IActivityProcess;
import com.bs6.lottery.application.process.model.DrawProcessReq;
import com.bs6.lottery.application.process.model.DrawProcessResult;
import com.bs6.lottery.common.Constants;
import com.bs6.lottery.domain.activity.model.DrawOrderVO;
import com.bs6.lottery.domain.activity.model.PartakeReq;
import com.bs6.lottery.domain.activity.model.PartakeResult;
import com.bs6.lottery.domain.activity.service.partake.IActivityPartake;
import com.bs6.lottery.domain.strategy.model.DrawPrizeInfo;
import com.bs6.lottery.domain.strategy.model.DrawReq;
import com.bs6.lottery.domain.strategy.model.DrawResult;
import com.bs6.lottery.domain.strategy.service.draw.IDrawExec;
import com.bs6.lottery.domain.support.ids.IIdGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;


@Service
public class ActivityProcessImpl implements IActivityProcess {

    @Resource
    IActivityPartake activityPartake;
    @Resource
    IDrawExec drawExec;
    @Resource
    private Map<Constants.Ids, IIdGenerator> idGeneratorMap;
    @Override
    public DrawProcessResult doDrawProcess(DrawProcessReq req) {

        // 1. partake activity
        PartakeReq partakeReq = new PartakeReq(req.getUid(), req.getActivityId(),new Date());
        PartakeResult partakeResult = activityPartake.doPartake(partakeReq);
        if(!partakeResult.getCode().equals(Constants.ResponseCode.SUCCESS.getCode())){
            return new DrawProcessResult(partakeResult.getCode(),partakeResult.getInfo());
        }

        Long strategyId = partakeResult.getStrategyId();
        Long takeId = partakeResult.getTakeId();

        // 2. draw prize
        DrawReq drawReq = new DrawReq(req.getUid(),strategyId);
        DrawResult drawResult = drawExec.doDrawExec(drawReq);
        if(!drawResult.getDrawStatus().equals(Constants.DrawStatus.WIN.getCode())){
            return new DrawProcessResult(Constants.ResponseCode.UN_ERROR.getCode(),"You lose, haha!");
        }

        DrawPrizeInfo drawPrizeInfo = drawResult.getDrawPrizeInfo();

        // 3. 结果落库
        activityPartake.recordDrawOrder(buildDrawOrderVO(req, strategyId, takeId, drawPrizeInfo));

        // 4. 发送MQ，触发发奖流程

        // 5. 返回结果

        return new DrawProcessResult(Constants.ResponseCode.SUCCESS.getCode(),Constants.ResponseCode.SUCCESS.getInfo(),drawPrizeInfo);
    }

    private DrawOrderVO buildDrawOrderVO(DrawProcessReq req, Long strategyId, Long takeId, DrawPrizeInfo drawPrizeInfo) {
        long orderId = idGeneratorMap.get(Constants.Ids.SnowFlake).nextId();
        DrawOrderVO drawOrderVO = new DrawOrderVO();
        drawOrderVO.setUid(req.getUid());
        drawOrderVO.setTakeId(takeId);
        drawOrderVO.setActivityId(req.getActivityId());
        drawOrderVO.setOrderId(orderId);
        drawOrderVO.setStrategyId(strategyId);
        drawOrderVO.setStrategyMode(drawPrizeInfo.getStrategyMode());
        drawOrderVO.setDistributeType(drawPrizeInfo.getDistributeType());
        drawOrderVO.setDistributeDate(drawPrizeInfo.getDistributeDate());
        drawOrderVO.setDistributeStatus(Constants.DistributeStatus.INIT.getCode());
        drawOrderVO.setPrizeId(drawPrizeInfo.getPrizeId());
        drawOrderVO.setPrizeType(drawPrizeInfo.getPrizeType());
        drawOrderVO.setPrizeName(drawPrizeInfo.getPrizeName());
        drawOrderVO.setPrizeContent(drawPrizeInfo.getPrizeContent());
        return drawOrderVO;
    }


}
