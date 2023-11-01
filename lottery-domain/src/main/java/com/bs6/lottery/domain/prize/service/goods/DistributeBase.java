package com.bs6.lottery.domain.prize.service.goods;

import com.bs6.lottery.domain.prize.repository.IPrizeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

public class DistributeBase{
    protected Logger logger = LoggerFactory.getLogger(DistributeBase.class);

    @Resource
    private IPrizeRepository prizeRepository;

    protected void updateUserAwardState(String uId, String orderId, String prizeId, Integer prizeStatus, String prizeStatusInfo) {
        // TODO 后期添加更新分库分表中，用户个人的抽奖记录表中奖品发奖状态
        logger.info("TODO 后期添加更新分库分表中，用户个人的抽奖记录表中奖品发奖状态 uId：{}", uId);
    }
}
