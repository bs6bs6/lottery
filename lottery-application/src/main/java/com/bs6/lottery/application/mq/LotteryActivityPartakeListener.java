package com.bs6.lottery.application.mq;

import com.alibaba.fastjson.JSON;
import com.bs6.lottery.domain.activity.model.ActivityPartakeRecordVO;
import com.bs6.lottery.domain.activity.service.partake.IActivityPartake;
import com.bs6.lottery.domain.activity.service.partake.impl.ActivityPartakeImpl;
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
public class LotteryActivityPartakeListener {
    private Logger logger = LoggerFactory.getLogger(LotteryActivityPartakeListener.class);

    @Resource
    private IActivityPartake activityPartake;
    @KafkaListener(topics = "lottery_activity_partake", groupId = "lottery")
    public void onMessage(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<?> message = Optional.ofNullable(record.value());
        // 1. 判断消息是否存在
        if (!message.isPresent()) {
            return;
        }

        ActivityPartakeRecordVO activityPartakeRecordVO = JSON.parseObject((String) message.get(), ActivityPartakeRecordVO.class);
        logger.info("stock deduct asynchronously message：{}", message.get());

        // 3. 更新数据库库存
        activityPartake.updateActivityStock(activityPartakeRecordVO);
    }

}
