package com.bs6.lottery.application.worker;

import com.alibaba.fastjson.JSON;
import com.bs6.lottery.application.mq.KafkaProducer;
import com.bs6.lottery.common.Constants;
import com.bs6.lottery.common.Result;
import com.bs6.lottery.domain.activity.model.ActivityVO;
import com.bs6.lottery.domain.activity.model.InvoiceVO;
import com.bs6.lottery.domain.activity.service.deploy.IActivityDeploy;
import com.bs6.lottery.domain.activity.service.partake.IActivityPartake;
import com.bs6.lottery.domain.activity.service.statusflow.IStatusHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component
public class LotteryScheduledJob {

    private static final Logger logger = LoggerFactory.getLogger(LotteryScheduledJob.class);


    @Resource
    IActivityDeploy activityDeploy;
    @Resource
    IActivityPartake activityPartake;
    @Resource
    KafkaProducer kafkaProducer;
    @Resource
    IStatusHandler statusHandler;

    @Scheduled(fixedRate =1000)
    public void InActiveJob(){
        List<ActivityVO> activityVOList = activityDeploy.scanToDoActivityList(0L);
        if (activityVOList.isEmpty()){
            logger.info("扫描活动状态 End 暂无符合需要扫描的活动列表");
            return;
        }

        while (!activityVOList.isEmpty()) {
            for (ActivityVO activityVO : activityVOList) {
                Integer status = activityVO.getStatus();
                switch (status) {
                    // 活动状态为审核通过，在临近活动开启时间前，审核活动为活动中。在使用活动的时候，需要依照活动状态核时间两个字段进行判断和使用。
                    case 4:
                        Result state4Result = statusHandler.doing(activityVO.getActivityId(), Constants.ActivityStatus.APPROVED);
//                        logger.info("扫描活动状态为活动中  activityId：{} activityName：{} creator：{}",  activityVO.getActivityId(), activityVO.getActivityName(), activityVO.getCreator());
                        logger.info("扫描活动状态为活动中 结果：{} activityId：{} activityName：{} creator：{}", JSON.toJSONString(state4Result), activityVO.getActivityId(), activityVO.getActivityName(), activityVO.getCreator());
                        break;
                    // 扫描时间已过期的活动，从活动中状态变更为关闭状态
                    case 5:
                        if (activityVO.getEndDateTime().before(new Date())){
                            Result state5Result = statusHandler.close(activityVO.getActivityId(), Constants.ActivityStatus.RUNNING);
//                            logger.info("扫描活动状态为关闭  activityId：{} activityName：{} creator：{}",  activityVO.getActivityId(), activityVO.getActivityName(), activityVO.getCreator());
                            logger.info("扫描活动状态为关闭 结果：{} activityId：{} activityName：{} creator：{}", JSON.toJSONString(state5Result), activityVO.getActivityId(), activityVO.getActivityName(), activityVO.getCreator());
                        }
                        break;
                    default:
                        break;
                }
            }

            // 获取集合中最后一条记录，继续扫描后面10条记录
            ActivityVO activityVO = activityVOList.get(activityVOList.size() - 1);
            activityVOList = activityDeploy.scanToDoActivityList(activityVO.getId());
        }

        logger.info("扫描活动状态 End");
    }

    @Scheduled(fixedRate = 1000)
    public void OrderMQStateJob(){
        List<InvoiceVO> invoiceVOList = activityPartake.scanInvoiceMqState();
        logger.info("扫描用户抽奖奖品发放MQ状态 扫描数：{}", invoiceVOList.size());
        // 补偿 MQ 消息
        for (InvoiceVO invoiceVO : invoiceVOList) {
            ListenableFuture<SendResult<String, Object>> future = kafkaProducer.sendLotteryInvoice(invoiceVO);
            future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
                @Override
                public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                    // MQ 消息发送完成，更新数据库表 user_strategy_export.mq_state = 1
                    activityPartake.updateInvoiceMqState(invoiceVO.getUid(), invoiceVO.getOrderId(), Constants.MQStatus.COMPLETE.getCode());
                }
                @Override
                public void onFailure(Throwable throwable) {
                    // MQ 消息发送失败，更新数据库表 user_strategy_export.mq_state = 2 【等待定时任务扫码补偿MQ消息】
                    activityPartake.updateInvoiceMqState(invoiceVO.getUid(), invoiceVO.getOrderId(), Constants.MQStatus.FAIL.getCode());
                }
            });
        }

    }

}
