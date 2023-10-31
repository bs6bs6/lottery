package com.bs6.lottery.test.domain;

import com.bs6.lottery.domain.strategy.model.DrawReq;
import com.bs6.lottery.domain.strategy.repository.IStrategyRepository;
import com.bs6.lottery.domain.strategy.service.draw.impl.DrawExecImpl;
import com.bs6.lottery.infrastructure.dao.IStrategyDao;
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

    @Test
    public void test_strategy() {

        System.out.println(strategyPrizeDao.queryNoStockStrategyPrizeList(10001L));
    }

    @Test
    public void test_draw(){
        DrawReq req = new DrawReq("bs6",10001L);
        System.out.println(drawExec.doDrawExec(req));
    }
}
