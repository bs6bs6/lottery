package com.bs6.lottery.domain.strategy.service.draw;

import com.bs6.lottery.domain.strategy.model.DrawReq;
import com.bs6.lottery.domain.strategy.model.DrawResult;

interface IDrawExec {
    DrawResult doDrawExec(DrawReq req);

}
