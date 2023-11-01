package com.bs6.lottery.domain.prize.model;




public class GoodsReq {

    /** 用户ID */
    private String uid;

    /** 抽奖单号 ID */
    private String orderId;

    /** 奖品ID */
    private String prizeId;

    /**
     * 奖品名称
     */
    private String prizeName;

    /**
     * 奖品内容「描述、奖品码、sku」
     */
    private String prizeContent;

    /** 四级送货地址（只有实物类商品需要地址） */
    private ShippingAddress shippingAddress;

    /** 扩展信息，用于一些个性商品发放所需要的透传字段内容 */
    private String extraInfo;

    public GoodsReq() {
    }

    public GoodsReq(String uid, String orderId, String prizeId, String prizeName, String prizeContent) {
        this.uid = uid;
        this.orderId = orderId;
        this.prizeId = prizeId;
        this.prizeName = prizeName;
        this.prizeContent = prizeContent;
    }

    public GoodsReq(String uid, String orderId, String prizeId, String prizeName, String prizeContent, ShippingAddress shippingAddress) {
        this.uid = uid;
        this.orderId = orderId;
        this.prizeId = prizeId;
        this.prizeName = prizeName;
        this.prizeContent = prizeContent;
        this.shippingAddress = shippingAddress;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getPrizeContent() {
        return prizeContent;
    }

    public void setPrizeContent(String prizeContent) {
        this.prizeContent = prizeContent;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }
}
