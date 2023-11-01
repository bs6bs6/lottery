package com.bs6.lottery.domain.prize.service.goods.impl;

import com.bs6.lottery.domain.prize.model.DistributeRes;
import com.bs6.lottery.domain.prize.model.GoodsReq;
import com.bs6.lottery.domain.prize.service.goods.DistributeBase;
import com.bs6.lottery.domain.prize.service.goods.IDistributeGoods;
import com.bs6.lottery.common.Constants;
import org.springframework.stereotype.Component;

@Component
public class Coupon extends DistributeBase implements IDistributeGoods {
    @Override
    public DistributeRes doDistribution(GoodsReq req) {
        logger.info("模拟调用优惠券发放接口 uid：{} prizedContent：{}", req.getUid(), req.getPrizeContent());

        // 更新用户领奖结果
        super.updateUserAwardState(req.getUid(), req.getOrderId(), req.getPrizeId(), Constants.PrizeStatus.SUCCESS.getCode(), Constants.PrizeStatus.SUCCESS.getInfo());

        return new DistributeRes(req.getUid(), Constants.PrizeStatus.SUCCESS.getCode(), Constants.PrizeStatus.SUCCESS.getInfo());
    }
}
