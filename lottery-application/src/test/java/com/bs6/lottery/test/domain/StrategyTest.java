package com.bs6.lottery.test.domain;

import com.bs6.lottery.application.process.IActivityProcess;
import com.bs6.lottery.application.process.model.DrawProcessReq;
import com.bs6.lottery.application.process.model.DrawProcessResult;
import com.bs6.lottery.domain.activity.model.PartakeReq;
import com.bs6.lottery.domain.activity.repository.IActivityRepository;
import com.bs6.lottery.domain.activity.service.partake.IActivityPartake;
import com.bs6.lottery.domain.strategy.model.DrawReq;
import com.bs6.lottery.domain.strategy.repository.IStrategyRepository;
import com.bs6.lottery.domain.strategy.service.draw.impl.DrawExecImpl;
import com.bs6.lottery.infrastructure.dao.IStrategyPrizeDao;
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
        System.out.println(activityPartake.doPartake(new PartakeReq("1",1000001L)));
    }

    @Test
    public void test_activity_process(){
        DrawProcessReq drawProcessReq = new DrawProcessReq("1",100001L);
        System.out.println(activityProcess.doDrawProcess(drawProcessReq));
    }
}
