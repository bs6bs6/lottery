package com.bs6.lottery.application.facade;

import com.alibaba.fastjson.JSON;
import com.bs6.lottery.application.process.IActivityProcess;
import com.bs6.lottery.application.process.model.DrawProcessReq;
import com.bs6.lottery.application.process.model.DrawProcessResult;
import com.bs6.lottery.common.Constants;
import com.bs6.lottery.rpc.ILotteryActivityBooth;
import com.bs6.lottery.rpc.model.DrawPrizeReq;
import com.bs6.lottery.rpc.model.DrawPrizeRes;
import com.bs6.lottery.rpc.model.PrizeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
public class LotteryActivityBooth implements ILotteryActivityBooth {

    private Logger logger = LoggerFactory.getLogger(LotteryActivityBooth.class);

    @Resource
    private IActivityProcess activityProcess;


    @Override
    public DrawPrizeRes doDraw(DrawPrizeReq drawReq) {
        try {
            logger.info("抽奖，开始 uId：{} activityId：{}", drawReq.getuId(), drawReq.getActivityId());

            // 1. 执行抽奖
            DrawProcessResult drawProcessResult = activityProcess.doDrawProcess(new DrawProcessReq(drawReq.getuId(), drawReq.getActivityId()));
            if (!Constants.ResponseCode.SUCCESS.getCode().equals(drawProcessResult.getCode())) {
                logger.error("Draw failed(抽奖过程异常) uId：{} activityId：{}", drawReq.getuId(), drawReq.getActivityId());
                return new DrawPrizeRes(drawProcessResult.getCode(), drawProcessResult.getInfo());
            }

            PrizeDTO prizeDTO = new PrizeDTO(drawReq.getuId(),drawReq.getActivityId());
            BeanUtils.copyProperties(drawProcessResult.getDrawPrizeInfo(),prizeDTO);

            DrawPrizeRes drawRes = new DrawPrizeRes(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo());
            drawRes.setPrizeDTO(prizeDTO);
            logger.info("Draw Compete! uId：{} activityId：{} drawRes：{}", drawReq.getuId(), drawReq.getActivityId(), JSON.toJSONString(drawRes));

            return drawRes;
        } catch (Exception e) {
            logger.error("Draw failed! uId：{} activityId：{} reqJson：{}", drawReq.getuId(), drawReq.getActivityId(), JSON.toJSONString(drawReq), e);
            return new DrawPrizeRes(Constants.ResponseCode.UN_ERROR.getCode(), Constants.ResponseCode.UN_ERROR.getInfo());
        }
    }
}
