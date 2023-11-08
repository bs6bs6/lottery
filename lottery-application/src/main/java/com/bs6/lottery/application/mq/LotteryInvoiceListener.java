package com.bs6.lottery.application.mq;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.bs6.lottery.common.Constants;
import com.bs6.lottery.domain.activity.model.InvoiceVO;
import com.bs6.lottery.domain.prize.model.DistributeRes;
import com.bs6.lottery.domain.prize.model.GoodsReq;
import com.bs6.lottery.domain.prize.service.goods.IDistributeGoods;
import com.bs6.lottery.domain.prize.service.goods.factory.DistributeGoodsFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

@Component
public class LotteryInvoiceListener {
    private Logger logger = LoggerFactory.getLogger(LotteryInvoiceListener.class);

    @Resource
    private DistributeGoodsFactory distributeGoodsFactory;
    @KafkaListener(topics = "lottery_invoice", groupId = "lottery")
    public void onMessage(ConsumerRecord<?,?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic){
        Optional<?> message = Optional.ofNullable(record.value());
        if (!message.isPresent()) {
            return;
        }
        try{
            // 1. 转化对象
            InvoiceVO invoiceVO = JSON.parseObject((String) message.get(), InvoiceVO.class);

            // 2. 获取发送奖品工厂，执行发奖
            IDistributeGoods distributionGoodsService = distributeGoodsFactory.getDistributionGoodsService(invoiceVO.getPrizeType());
            DistributeRes distributionRes = distributionGoodsService.doDistribution(new GoodsReq(invoiceVO.getUid(), invoiceVO.getOrderId(), invoiceVO.getPrizeId(), invoiceVO.getPrizeName(), invoiceVO.getPrizeContent()));

            Assert.isTrue(Constants.PrizeStatus.SUCCESS.getCode().equals(distributionRes.getCode()), distributionRes.getInfo());

            // 3. 打印日志
            logger.info("consumption complete topic：{} bizId：{} result：{}", topic, invoiceVO.getUid(), JSON.toJSONString(distributionRes));

            // 4. 消息消费完成
            ack.acknowledge();
        }catch(Exception e){
            logger.error("consumption failed topic：{} message：{}", topic, message.get());
            throw e;
        }
    }




}
