package com.bs6.lottery.application.process;

import com.bs6.lottery.application.process.model.DrawProcessReq;
import com.bs6.lottery.application.process.model.DrawProcessResult;


public interface IActivityProcess {

    /**
     * 执行抽奖流程
     * @param req 抽奖请求
     * @return    抽奖结果
     */
    DrawProcessResult doDrawProcess(DrawProcessReq req);

}
