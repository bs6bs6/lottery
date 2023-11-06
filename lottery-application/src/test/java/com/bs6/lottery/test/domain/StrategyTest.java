package com.bs6.lottery.test.domain;

import com.bs6.lottery.application.mq.KafkaProducer;
import com.bs6.lottery.application.process.IActivityProcess;
import com.bs6.lottery.application.process.model.DrawProcessReq;
import com.bs6.lottery.domain.activity.model.InvoiceVO;
import com.bs6.lottery.domain.activity.model.PartakeReq;
import com.bs6.lottery.domain.activity.repository.IActivityRepository;
import com.bs6.lottery.domain.activity.service.partake.IActivityPartake;
import com.bs6.lottery.domain.strategy.model.DrawReq;
import com.bs6.lottery.domain.strategy.repository.IStrategyRepository;
import com.bs6.lottery.domain.strategy.service.draw.impl.DrawExecImpl;
import com.bs6.lottery.infrastructure.dao.IStrategyPrizeDao;
import com.bs6.lottery.infrastructure.dao.IUserTakeActivityCountDao;
import com.bs6.lottery.infrastructure.po.UserTakeActivityCount;

import com.bs6.lottery.rpc.ILotteryActivityBooth;
import com.bs6.lottery.rpc.model.DrawPrizeReq;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyTest {
    @Autowired
    IStrategyRepository strategyRepository;
    @Autowired
    IStrategyPrizeDao strategyPrizeDao;
    @Autowired
    DrawExecImpl drawExec;
    @Autowired
    IActivityRepository activityRepository;
    @Autowired
    IActivityPartake activityPartake;
    @Autowired
    IActivityProcess activityProcess;
    @Autowired
    IUserTakeActivityCountDao userTakeActivityCountDao;
    @Autowired
    ILotteryActivityBooth lotteryActivityBooth;
    @Autowired
    KafkaProducer kafkaProducer;

    @Test
    public void test_strategy() {
        System.out.println(strategyRepository.queryStrategyAgg(10001L));
    }

    @Test
    public void test_draw(){
        DrawReq req = new DrawReq("bs6",10001L);
        System.out.println(drawExec.doDrawExec(req));
    }
    @Test
    public void test_activity(){
        System.out.println(activityPartake.doPartake(new PartakeReq("1",100001L)));
    }

    @Test
    public void test_activity_count(){
        UserTakeActivityCount userTakeActivityCount = new UserTakeActivityCount();
        userTakeActivityCount.setUid("1");
        userTakeActivityCount.setActivityId(100001L);
        int n = userTakeActivityCountDao.updateLeftCount(userTakeActivityCount);
        System.out.println(n);
    }

    @Test
    public void test_activity_process(){
        DrawProcessReq drawProcessReq = new DrawProcessReq("Uhdgkw766120d",100001L);
        System.out.println(activityProcess.doDrawProcess(drawProcessReq));
    }

    @Test
    public void test_controller() throws InterruptedException {
        DrawPrizeReq req = new DrawPrizeReq("bs6",100002L);
        System.out.println(lotteryActivityBooth.doDraw(req));
        while (true){
            Thread.sleep(10000);
        }
    }
    @Test
    public void test_send() throws InterruptedException {
        InvoiceVO invoice = new InvoiceVO();
        invoice.setUid("shiqi");
        invoice.setOrderId(1444540456057864192L);
        invoice.setPrizeId("3");
        invoice.setPrizeType(1);
        invoice.setPrizeName("Mac");
        invoice.setPrizeContent("Code");
        invoice.setShippingAddress(null);
        invoice.setExtraInfo(null);
        kafkaProducer.sendLotteryInvoice(invoice);

        while (true){
            Thread.sleep(10000);
        }
    }



}
