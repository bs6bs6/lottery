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

    protected void updateUserPrizeStatus(String uid, Long orderId, String prizeId, Integer distributeStatus) {
        prizeRepository.updateUserPrizeStatus(uid,orderId,prizeId,distributeStatus);
    }
}
