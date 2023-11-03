package com.bs6.lottery.rpc;

import com.bs6.lottery.rpc.model.DrawPrizeReq;
import com.bs6.lottery.rpc.model.DrawPrizeRes;

public interface ILotteryActivityBooth {

    /**
     * 指定活动抽奖
     * @param drawReq 请求参数
     * @return        抽奖结果
     */
    DrawPrizeRes doDraw(DrawPrizeReq drawReq);


}
