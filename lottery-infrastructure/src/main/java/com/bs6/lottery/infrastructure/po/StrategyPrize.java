package com.bs6.lottery.infrastructure.po;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 策略明细
 * @author: 小傅哥，微信：fustack
 * @date: 2021/9/25
 * @github: https://github.com/fuzhengwei
 * @Copyright: 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
public class StrategyPrize {

    /**
     * 自增ID
     */
    private Long id;

    /**
     * 策略ID
     */
    private Long strategyId;

    /**
     * 奖品ID
     */
    private String prizeId;

    /**
     * 奖品名称
     */
    private String prizeName;

    /**
     * 奖品库存
     */
    private Integer stockCount;

    /**
     * 奖品剩余库存
     */
    private Integer remainingStockCount;

    /**
     * 中奖概率
     */
    private BigDecimal prizeProbability;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public String getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(String prizeId) {
        this.prizeId = prizeId;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Integer getRemainingStockCount() {
        return remainingStockCount;
    }

    public void setRemainingStockCount(Integer remainingStockCount) {
        this.remainingStockCount = remainingStockCount;
    }

    public BigDecimal getPrizeProbability() {
        return prizeProbability;
    }

    public void setPrizeProbability(BigDecimal prizeProbability) {
        this.prizeProbability = prizeProbability;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
