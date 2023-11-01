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
    private Integer drawStatus = Constants.DrawStatus.LOSE.getCode();

    /**
     * prize info
     */
    private DrawPrizeInfo drawPrizeInfo;

    public DrawResult() {
    }

    public DrawResult(String uid, Long strategyId, Integer drawState) {
        this.uid = uid;
        this.strategyId = strategyId;
        this.drawStatus = drawState;
    }

    public DrawResult(String uid, Long strategyId, Integer drawState, DrawPrizeInfo drawPrizeInfo) {
        this.uid = uid;
        this.strategyId = strategyId;
        this.drawStatus = drawState;
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

    public Integer getDrawStatus() {
        return drawStatus;
    }

    public void setDrawStatus(Integer drawStatus) {
        this.drawStatus = drawStatus;
    }

    public DrawPrizeInfo getDrawPrizeInfo() {
        return drawPrizeInfo;
    }

    public void setDrawPrizeInfo(DrawPrizeInfo drawPrizeInfo) {
        this.drawPrizeInfo = drawPrizeInfo;
    }

    @Override
    public String toString() {
        return "DrawResult{" +
                "uid='" + uid + '\'' +
                ", strategyId=" + strategyId +
                ", drawStatus=" + drawStatus +
                ", drawPrizeInfo=" + drawPrizeInfo +
                '}';
    }
}
