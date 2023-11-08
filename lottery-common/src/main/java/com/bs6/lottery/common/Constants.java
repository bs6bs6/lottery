package com.bs6.lottery.common;

public class Constants {

    public enum ResponseCode {
        SUCCESS("0000", "Success"),
        UN_ERROR("0001", "Error"),
        ILLEGAL_PARAMETER("0002", "Illegal Parameters"),
        INDEX_DUP("0003", "Primary Key Duplicates"),
        NO_UPDATE("0004","SQL No Updates"),

        LOSING_DRAW("D001", "lose"),

        NOT_CONSUMED_TAKE("D003", "unconsumed record"),
        OUT_OF_STOCK("D004", "out of stock"),
        ERR_TOKEN("D005", "distributed lock duplicate");

        private String code;
        private String info;

        ResponseCode(String code, String info) {
            this.code = code;
            this.info = info;
        }

        public String getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }

    }

    public static final class RedisKey {

        // 抽奖活动库存 Key
        private static final String LOTTERY_ACTIVITY_STOCK_COUNT = "lottery_activity_stock_count_";

        public static String KEY_LOTTERY_ACTIVITY_STOCK_COUNT(Long activityId) {
            return LOTTERY_ACTIVITY_STOCK_COUNT + activityId;
        }

        // 抽奖活动库存锁 Key
        private static final String LOTTERY_ACTIVITY_STOCK_COUNT_TOKEN = "lottery_activity_stock_count_token_";

        public static String KEY_LOTTERY_ACTIVITY_STOCK_COUNT_TOKEN(Long activityId, Integer stockUsedCount) {
            return LOTTERY_ACTIVITY_STOCK_COUNT_TOKEN + activityId + "_" + stockUsedCount;
        }

    }

    /**
     * activity status：1 Editing、2 request a review、3 request canceled、4 approved、5 running(After censoring and being scanned by worker)、6 refused、7 closed、8 opened
     */
    public enum ActivityStatus {

        /**
         * 1：edit
         */
        EDITING(1, "editing"),
        /**
         * 2：request a review
         */
        REVIEW(2, "review requested"),
        /**
         * 3：cancel the review request
         */
        CANCELED(3, "request canceled"),
        /**
         * 4：通过
         */
        APPROVED(4, "approved"),
        /**
         * 5：运行(活动中)
         */
        RUNNING(5, "运行(活动中)"),
        /**
         * 6：拒绝
         */
        REFUSED(6, "拒绝"),
        /**
         * 7：关闭
         */
        CLOSED(7, "关闭"),
        /**
         * 8：开启
         */
        OPENED(8, "开启");

        private Integer code;
        private String info;

        ActivityStatus(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }

    /**
     * Draw Strategy: static probability、dynamic  probability
     * Scenario：A:20%、B:30%、C:50%
     * static：lets say we run out of prize A, but the probability of wining prize B and C will be static. If we win a prize A based on the calculated result at this time, the server will respond with "storage ran out"
     * dynamic：After running out of A，the probability of B and C are distributed as 3:5，the probability of B will rise from 0.3 to 0.375
     */
    public enum StrategyMode {


        STATIC(1, "static"),


        DYNAMIC(2, "dynamic");

        private Integer code;
        private String info;

        StrategyMode(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }

    /**
     * DrawStatus：0 lose、1 win、2 fallback
     */
    public enum DrawStatus {

        LOSE(0, "lose"),

        /**
         * 已中奖
         */
        WIN(1, "win"),

        /**
         * 兜底奖
         */
        FALLBACK(2, "fall back");

        private Integer code;
        private String info;

        DrawStatus(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }

    /**
     * Prize Status：0 wait for distributing、1 success、2 fail
     */
    public enum PrizeStatus {

        WAIT(0, "waiting"),
        SUCCESS(1, "success"),
        FAILURE(2, "failure");

        private Integer code;
        private String info;

        PrizeStatus(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }

    /**
     * prize type（1:description、2:redemption code、3:coupon、4:physical）
     */
    public enum PrizeType {

        Description(1, "text description"),

        RedemptionCode(2, "redemption code"),

        Coupon(3, "coupon"),

        Physical(4, "physical prize");

        private Integer code;
        private String info;

        PrizeType(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }

    /**
     * Ids Strategy
     */
    public enum Ids {
        SnowFlake,
        ShortCode,
        RandomNumeric;
    }
    public enum DistributeStatus{

        INIT(0, "初始"),
        COMPLETE(1, "完成"),
        FAIL(2, "失败");

        private Integer code;
        private String info;

        DistributeStatus(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }

    /**
     * 活动单使用状态 0未使用、1已使用
     */
    public enum OrderStatus {

        NOT_USED(0, "未使用"),
        USED(1, "已使用");

        private Integer code;
        private String info;

        OrderStatus(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
    /**
     * 消息发送状态（0未发送、1发送成功、2发送失败）
     */
    public enum MQStatus {
        INIT(0, "初始"),
        COMPLETE(1, "完成"),
        FAIL(2, "失败");

        private Integer code;
        private String info;

        MQStatus(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }

}
