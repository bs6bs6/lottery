package com.bs6.lottery.domain.prize.service.goods;

import com.bs6.lottery.domain.prize.model.DistributeRes;
import com.bs6.lottery.domain.prize.model.GoodsReq;

public interface IDistributeGoods {
    DistributeRes doDistribution(GoodsReq req);

}
