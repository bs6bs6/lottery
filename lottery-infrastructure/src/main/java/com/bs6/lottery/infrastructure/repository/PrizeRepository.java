package com.bs6.lottery.infrastructure.repository;

import com.bs6.lottery.domain.prize.repository.IPrizeRepository;
import com.bs6.lottery.infrastructure.dao.IPrizeDao;
import com.bs6.lottery.infrastructure.dao.IUserStrategyExportDao;
import com.bs6.lottery.infrastructure.po.UserStrategyExport;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class PrizeRepository implements IPrizeRepository {
    @Resource
    IUserStrategyExportDao userStrategyExportDao;
    @Override
    public void updateUserPrizeStatus(String uid, Long orderId, String prizeId, Integer distributeStatus) {
        UserStrategyExport userStrategyExport = new UserStrategyExport();
        userStrategyExport.setUid(uid);
        userStrategyExport.setOrderId(orderId);
        userStrategyExport.setPrizeId(prizeId);
        userStrategyExport.setDistributeStatus(distributeStatus);
        userStrategyExportDao.updateUserPrizeStatus(userStrategyExport);
    }
}
