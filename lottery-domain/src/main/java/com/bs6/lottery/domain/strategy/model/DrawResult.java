package com.bs6.lottery.domain.strategy.model;

import com.bs6.lottery.common.Constants;
public class DrawResult {

    /**
     * User Id
     */
    private String uid;

    /**
     * Strategy ID
     */
    private Long strategyId;

    /**
     * Draw Result Status：0 lose、1 won、2  Constants.DrawStatus
     */
    private Integer drawState = Constants.DrawStatus.LOSE.getCode();

    /**
     * prize info
     */
    private DrawPrizeInfo drawPrizeInfo;

    public DrawResult() {
    }

    public DrawResult(String uid, Long strategyId, Integer drawState) {
        this.uid = uid;
        this.strategyId = strategyId;
        this.drawState = drawState;
    }

    public DrawResult(String uid, Long strategyId, Integer drawState, DrawPrizeInfo drawPrizeInfo) {
        this.uid = uid;
        this.strategyId = strategyId;
        this.drawState = drawState;
        this.drawPrizeInfo = drawPrizeInfo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uId) {
        this.uid = uid;
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public Integer getDrawState() {
        return drawState;
    }

    public void setDrawState(Integer drawState) {
        this.drawState = drawState;
    }

    public DrawPrizeInfo getDrawPrizeInfo() {
        return drawPrizeInfo;
    }

    public void setDrawPrizeInfo(DrawPrizeInfo drawPrizeInfo) {
        this.drawPrizeInfo = drawPrizeInfo;
    }

}
