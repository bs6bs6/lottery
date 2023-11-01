package com.bs6.lottery.domain.prize.service.goods.factory;

import com.bs6.lottery.domain.prize.service.goods.IDistributeGoods;
import com.bs6.lottery.domain.prize.service.goods.impl.Coupon;
import com.bs6.lottery.domain.prize.service.goods.impl.Description;
import com.bs6.lottery.domain.prize.service.goods.impl.Physical;
import com.bs6.lottery.domain.prize.service.goods.impl.RedemptionCode;
import com.bs6.lottery.common.Constants;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GoodsConfig {
    protected static Map<Integer, IDistributeGoods> goodsMap = new ConcurrentHashMap<>();

    @Resource
    private Description description;

    @Resource
    private RedemptionCode redeemCode;

    @Resource
    private Coupon coupon;

    @Resource
    private Physical physical;

    @PostConstruct
    public void init() {
        goodsMap.put(Constants.PrizeType.Description.getCode(), description);
        goodsMap.put(Constants.PrizeType.RedemptionCode.getCode(), redeemCode);
        goodsMap.put(Constants.PrizeType.Coupon.getCode(), coupon);
        goodsMap.put(Constants.PrizeType.Physical.getCode(), physical);
    }
}
