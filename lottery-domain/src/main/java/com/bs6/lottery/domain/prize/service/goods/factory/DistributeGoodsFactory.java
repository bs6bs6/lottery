package com.bs6.lottery.domain.prize.service.goods.factory;

import com.bs6.lottery.domain.prize.service.goods.IDistributeGoods;
import org.springframework.stereotype.Service;

@Service
public class DistributeGoodsFactory extends GoodsConfig {

    public IDistributeGoods getDistributionGoodsService(Integer awardType){
        return goodsMap.get(awardType);
    }

}
