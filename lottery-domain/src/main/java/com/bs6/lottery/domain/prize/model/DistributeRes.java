package com.bs6.lottery.domain.prize.model;


public class DistributeRes {

    /** 用户ID */
    private String uid;

    /** 编码 */
    private Integer code;
    /** 描述 */
    private String info;

    /** 结算单ID，如：发券后有券码、发货后有单号等，用于存根查询 */
    private String statementId;

    public DistributeRes() {
    }

    /**
     * 构造函数
     *
     * @param uid   用户ID
     * @param code  编码
     * @param info  描述
     */
    public DistributeRes(String uid, Integer code, String info) {
        this.uid = uid;
        this.code = code;
        this.info = info;
    }

    public DistributeRes(String uid, Integer code, String info, String statementId) {
        this.uid = uid;
        this.code = code;
        this.info = info;
        this.statementId = statementId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getStatementId() {
        return statementId;
    }

    public void setStatementId(String statementId) {
        this.statementId = statementId;
    }

}
