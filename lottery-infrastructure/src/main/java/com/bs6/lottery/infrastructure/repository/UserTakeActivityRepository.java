package com.bs6.lottery.infrastructure.repository;

import com.bs6.lottery.common.Constants;
import com.bs6.lottery.domain.activity.model.DrawOrderVO;
import com.bs6.lottery.domain.activity.model.InvoiceVO;
import com.bs6.lottery.domain.activity.model.UserTakeActivityVO;
import com.bs6.lottery.domain.activity.repository.IUserTakeActivityRepository;
import com.bs6.lottery.infrastructure.dao.IUserStrategyExportDao;
import com.bs6.lottery.infrastructure.dao.IUserTakeActivityCountDao;
import com.bs6.lottery.infrastructure.dao.IUserTakeActivityDao;
import com.bs6.lottery.infrastructure.po.UserStrategyExport;
import com.bs6.lottery.infrastructure.po.UserTakeActivity;
import com.bs6.lottery.infrastructure.po.UserTakeActivityCount;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserTakeActivityRepository implements IUserTakeActivityRepository {

    @Resource
    private IUserTakeActivityCountDao userTakeActivityCountDao;

    @Resource
    private IUserTakeActivityDao userTakeActivityDao;

    @Resource
    private IUserStrategyExportDao userStrategyExportDao;
    @Override
    public int subtractionLeftCount(Long activityId, String activityName, Integer takeCount, Integer userTakeLeftCount, String uId, Date partakeDate) {
        if (null == userTakeLeftCount) {
            UserTakeActivityCount userTakeActivityCount = new UserTakeActivityCount();
            userTakeActivityCount.setUid(uId);
            userTakeActivityCount.setActivityId(activityId);
            userTakeActivityCount.setTotalCount(takeCount);
            userTakeActivityCount.setLeftCount(takeCount - 1);
            userTakeActivityCountDao.insert(userTakeActivityCount);
            return 1;
        } else {
            UserTakeActivityCount userTakeActivityCount = new UserTakeActivityCount();
            userTakeActivityCount.setUid(uId);
            userTakeActivityCount.setActivityId(activityId);
            return userTakeActivityCountDao.updateLeftCount(userTakeActivityCount);
        }
    }

    @Override
    public void takeActivity(Long activityId, String activityName, Long StrategyId,Integer takeCount, Integer userTakeLeftCount, String uId, Date takeDate, Long takeId) {
        UserTakeActivity userTakeActivity = new UserTakeActivity();
        userTakeActivity.setUid(uId);
        userTakeActivity.setTakeId(takeId);
        userTakeActivity.setActivityId(activityId);
        userTakeActivity.setActivityName(activityName);
        userTakeActivity.setTakeDate(takeDate);
        if (null == userTakeLeftCount) {
            userTakeActivity.setTakeCount(1);
        } else {
            // takeCount of userTakeActivity is actually the serial number of the take record of a specific user
            userTakeActivity.setTakeCount(takeCount - userTakeLeftCount + 1);
        }
        String uuid = uId + "_" + activityId + "_" + userTakeActivity.getTakeCount();
        userTakeActivity.setUuid(uuid);
        userTakeActivity.setStatus(Constants.OrderStatus.NOT_USED.getCode());
        userTakeActivity.setStrategyId(StrategyId);
        userTakeActivityDao.insert(userTakeActivity);
    }

    @Override
    public UserTakeActivityVO queryNoConsumedTakeActivityOrder(Long activityId, String uId) {

        UserTakeActivity userTakeActivity = new UserTakeActivity();
        userTakeActivity.setUid(uId);
        userTakeActivity.setActivityId(activityId);
        UserTakeActivity noConsumedTakeActivityOrder = userTakeActivityDao.queryNoConsumedTakeActivityOrder(userTakeActivity);

        // 未查询到符合的领取单，直接返回 NULL
        if (null == noConsumedTakeActivityOrder) {
            return null;
        }

        UserTakeActivityVO userTakeActivityVO = new UserTakeActivityVO();
        userTakeActivityVO.setActivityId(noConsumedTakeActivityOrder.getActivityId());
        userTakeActivityVO.setTakeId(noConsumedTakeActivityOrder.getTakeId());
        userTakeActivityVO.setStrategyId(noConsumedTakeActivityOrder.getStrategyId());
        userTakeActivityVO.setStatus(noConsumedTakeActivityOrder.getStatus());

        return userTakeActivityVO;
    }

    @Override
    public int lockTackActivity(String uId, Long activityId, Long takeId) {
        UserTakeActivity userTakeActivity = new UserTakeActivity();
        userTakeActivity.setUid(uId);
        userTakeActivity.setActivityId(activityId);
        userTakeActivity.setTakeId(takeId);
        return userTakeActivityDao.lockTackActivity(userTakeActivity);
    }
    @Override
    public void saveUserStrategyExport(DrawOrderVO drawOrder) {
        UserStrategyExport userStrategyExport = new UserStrategyExport();
        userStrategyExport.setUid(drawOrder.getUid());
        userStrategyExport.setActivityId(drawOrder.getActivityId());
        userStrategyExport.setOrderId(drawOrder.getOrderId());
        userStrategyExport.setStrategyId(drawOrder.getStrategyId());
        userStrategyExport.setStrategyMode(drawOrder.getStrategyMode());
        userStrategyExport.setDistributeType(drawOrder.getDistributeType());
        userStrategyExport.setDistributeDate(drawOrder.getDistributeDate());
        userStrategyExport.setDistributeStatus(drawOrder.getDistributeStatus());
        userStrategyExport.setPrizeId(drawOrder.getPrizeId());
        userStrategyExport.setPrizeType(drawOrder.getPrizeType());
        userStrategyExport.setPrizeName(drawOrder.getPrizeName());
        userStrategyExport.setPrizeContent(drawOrder.getPrizeContent());
        userStrategyExport.setUuid(String.valueOf(drawOrder.getOrderId()));

        userStrategyExportDao.insert(userStrategyExport);
    }

    @Override
    public void updateInvoiceMqStatus(String uId, Long orderId, Integer mqStatus) {
        UserStrategyExport userStrategyExport = new UserStrategyExport();
        userStrategyExport.setUid(uId);
        userStrategyExport.setOrderId(orderId);
        userStrategyExport.setMqStatus(mqStatus);
        userStrategyExportDao.updateInvoiceMqStatus(userStrategyExport);
    }

    @Override
    public List<InvoiceVO> scanInvoiceMqState() {
        // 查询发送MQ失败和超时30分钟，未发送MQ的数据
        List<UserStrategyExport> userStrategyExportList = userStrategyExportDao.scanInvoiceMqStatus();
        // 转换对象
        List<InvoiceVO> invoiceVOList = new ArrayList<>(userStrategyExportList.size());
        for (UserStrategyExport userStrategyExport : userStrategyExportList) {
            InvoiceVO invoiceVO = new InvoiceVO();
            invoiceVO.setUid(userStrategyExport.getUid());
            invoiceVO.setOrderId(userStrategyExport.getOrderId());
            invoiceVO.setPrizeId(userStrategyExport.getPrizeId());
            invoiceVO.setPrizeType(userStrategyExport.getPrizeType());
            invoiceVO.setPrizeName(userStrategyExport.getPrizeName());
            invoiceVO.setPrizeContent(userStrategyExport.getPrizeContent());
            invoiceVOList.add(invoiceVO);
        }
        return invoiceVOList;
    }
}
