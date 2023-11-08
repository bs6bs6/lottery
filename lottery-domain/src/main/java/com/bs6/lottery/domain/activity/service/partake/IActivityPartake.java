package com.bs6.lottery.domain.activity.service.partake;

import com.bs6.lottery.common.Result;
import com.bs6.lottery.domain.activity.model.*;

import java.util.List;

public interface IActivityPartake {

    /**
     * 参与活动
     * @param req 入参
     * @return    领取结果
     */
    PartakeResult doPartake(PartakeReq req);

    Result recordDrawOrder(DrawOrderVO drawOrder);

    void updateInvoiceMqState(String uId, Long orderId, Integer mqState);

    List<InvoiceVO> scanInvoiceMqState();

    void updateActivityStock(ActivityPartakeRecordVO activityPartakeRecordVO);

}
