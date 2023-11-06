package com.bs6.lottery.domain.activity.model;

import com.bs6.lottery.domain.prize.model.ShippingAddress;

public class InvoiceVO {

    /** 用户ID */
    private String uid;

    /** 抽奖单号 ID */
    private Long orderId;

    /** 奖品ID */
    private String prizeId;

    /**
     * 奖品类型（1:文字描述、2:兑换码、3:优惠券、4:实物奖品）
     */
    private Integer prizeType;

    /** 奖品名称 */
    private String prizeName;

    /** 奖品内容「描述、奖品码、sku」 */
    private String prizeContent;

    /** 四级送货地址（只有实物类商品需要地址） */
    private ShippingAddress shippingAddress;

    /** 扩展信息，用于一些个性商品发放所需要的透传字段内容 */
    private String extraInfo;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(String prizeId) {
        this.prizeId = prizeId;
    }

    public Integer getPrizeType() {
        return prizeType;
    }

    public void setPrizeType(Integer prizeType) {
        this.prizeType = prizeType;
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

    @Override
    public String toString() {
        return "InvoiceVO{" +
                "uid='" + uid + '\'' +
                ", orderId=" + orderId +
                ", prizeId='" + prizeId + '\'' +
                ", prizeType=" + prizeType +
                ", prizeName='" + prizeName + '\'' +
                ", prizeContent='" + prizeContent + '\'' +
                ", shippingAddress=" + shippingAddress +
                ", extraInfo='" + extraInfo + '\'' +
                '}';
    }
}
