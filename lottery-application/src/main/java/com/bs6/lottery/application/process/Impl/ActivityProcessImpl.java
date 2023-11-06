package com.bs6.lottery.application.process.Impl;

import com.bs6.lottery.application.mq.KafkaProducer;
import com.bs6.lottery.application.process.IActivityProcess;
import com.bs6.lottery.application.process.model.DrawProcessReq;
import com.bs6.lottery.application.process.model.DrawProcessResult;
import com.bs6.lottery.common.Constants;
import com.bs6.lottery.domain.activity.model.DrawOrderVO;
import com.bs6.lottery.domain.activity.model.InvoiceVO;
import com.bs6.lottery.domain.activity.model.PartakeReq;
import com.bs6.lottery.domain.activity.model.PartakeResult;
import com.bs6.lottery.domain.activity.service.partake.IActivityPartake;
import com.bs6.lottery.domain.strategy.model.DrawPrizeInfo;
import com.bs6.lottery.domain.strategy.model.DrawReq;
import com.bs6.lottery.domain.strategy.model.DrawResult;
import com.bs6.lottery.domain.strategy.service.draw.IDrawExec;
import com.bs6.lottery.domain.support.ids.IIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;


@Service
public class ActivityProcessImpl implements IActivityProcess {

    private Logger logger = LoggerFactory.getLogger(IActivityProcess.class);

    @Resource
    IActivityPartake activityPartake;
    @Resource
    IDrawExec drawExec;
    @Resource
    private Map<Constants.Ids, IIdGenerator> idGeneratorMap;
    @Resource
    KafkaProducer kafkaProducer;
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
        DrawOrderVO drawOrderVO = buildDrawOrderVO(req, strategyId, takeId, drawPrizeInfo);
        activityPartake.recordDrawOrder(drawOrderVO);

        // 4. 发送MQ，触发发奖流程
        InvoiceVO invoiceVO = buildInvoiceVO(drawOrderVO);
        ListenableFuture<SendResult<String,Object>> future = kafkaProducer.sendLotteryInvoice(invoiceVO);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable throwable) {
                // wait for scheduled task to compensate for it
                activityPartake.updateInvoiceMqState(invoiceVO.getUid(), invoiceVO.getOrderId(), Constants.MQStatus.FAIL.getCode());
                logger.info("failureeeeeeeeee");

            }

            @Override
            public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                activityPartake.updateInvoiceMqState(invoiceVO.getUid(), invoiceVO.getOrderId(), Constants.MQStatus.COMPLETE.getCode());
                logger.info("Sucessssssssssss");
            }
        });

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

    private InvoiceVO buildInvoiceVO(DrawOrderVO drawOrderVO) {
        InvoiceVO invoiceVO = new InvoiceVO();
        invoiceVO.setUid(drawOrderVO.getUid());
        invoiceVO.setOrderId(drawOrderVO.getOrderId());
        invoiceVO.setPrizeId(drawOrderVO.getPrizeId());
        invoiceVO.setPrizeType(drawOrderVO.getPrizeType());
        invoiceVO.setPrizeName(drawOrderVO.getPrizeName());
        invoiceVO.setPrizeContent(drawOrderVO.getPrizeContent());
        invoiceVO.setShippingAddress(null);
        invoiceVO.setExtraInfo(null);
        return invoiceVO;
    }


}
