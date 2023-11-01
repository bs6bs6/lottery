package com.bs6.lottery.application.process.model;

import com.bs6.lottery.common.Result;
import com.bs6.lottery.domain.strategy.model.DrawPrizeInfo;

public class DrawProcessResult extends Result {

    private DrawPrizeInfo drawPrizeInfo;

    public DrawProcessResult(String code, String info) {
        super(code, info);
    }

    public DrawProcessResult(String code, String info, DrawPrizeInfo drawPrizeInfo) {
        super(code, info);
        this.drawPrizeInfo = drawPrizeInfo;
    }

    public DrawPrizeInfo getDrawPrizeInfo() {
        return drawPrizeInfo;
    }

    public void setDrawPrizeInfo(DrawPrizeInfo drawPrizeInfo) {
        this.drawPrizeInfo = drawPrizeInfo;
    }
}
