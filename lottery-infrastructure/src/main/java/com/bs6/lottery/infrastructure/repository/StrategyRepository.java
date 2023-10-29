package com.bs6.lottery.infrastructure.repository;



import com.bs6.lottery.domain.strategy.model.PrizeBriefVO;
import com.bs6.lottery.domain.strategy.model.StrategyAgg;
import com.bs6.lottery.domain.strategy.model.StrategyBriefVO;
import com.bs6.lottery.domain.strategy.model.StrategyPrizeBriefVO;
import com.bs6.lottery.domain.strategy.repository.IStrategyRepository;
import com.bs6.lottery.infrastructure.dao.IPrizeDao;
import com.bs6.lottery.infrastructure.dao.IStrategyDao;
import com.bs6.lottery.infrastructure.dao.IStrategyPrizeDao;
import com.bs6.lottery.infrastructure.po.Prize;
import com.bs6.lottery.infrastructure.po.Strategy;
import com.bs6.lottery.infrastructure.po.StrategyPrize;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class StrategyRepository implements IStrategyRepository {

    @Resource
    private IStrategyDao strategyDao;

    @Resource
    private IStrategyPrizeDao strategyPrizeDao;

    @Resource
    private IPrizeDao prizeDao;

    @Override
    public StrategyAgg queryStrategyAgg(Long strategyId) {
        Strategy strategy = strategyDao.queryStrategy(strategyId);
        List<StrategyPrize> strategyPrizeList = strategyPrizeDao.queryStrategyPrizeList(strategyId);

        StrategyBriefVO strategyBriefVO = new StrategyBriefVO();
        BeanUtils.copyProperties(strategy, strategyBriefVO);

        List<StrategyPrizeBriefVO> strategyPrizeBriefVOList = new ArrayList<>();
        for (StrategyPrize strategyPrize : strategyPrizeList) {
            StrategyPrizeBriefVO strategyPrizeBriefVO = new StrategyPrizeBriefVO();
            BeanUtils.copyProperties(strategyPrize, strategyPrizeBriefVO);
            strategyPrizeBriefVOList.add(strategyPrizeBriefVO);
        }

        return new StrategyAgg(strategyId, strategyBriefVO, strategyPrizeBriefVOList);
    }

    @Override
    public PrizeBriefVO queryPrizeInfo(String prizeId) {

        Prize prize = prizeDao.queryPrizeInfo(prizeId);

        // 可以使用 BeanUtils.copyProperties(prize, prizeBriefVO)、或者基于ASM实现的Bean-Mapping，但在效率上最好的依旧是硬编码
        PrizeBriefVO prizeBriefVO = new PrizeBriefVO();
        prizeBriefVO.setPrizeId(prize.getPrizeId());
        prizeBriefVO.setPrizeType(prize.getPrizeType());
        prizeBriefVO.setPrizeName(prize.getPrizeName());
        prizeBriefVO.setPrizeContent(prize.getPrizeContent());

        return prizeBriefVO;
    }

    @Override
    public List<String> queryNoStockStrategyPrizeList(Long strategyId) {
        return strategyPrizeDao.queryNoStockStrategyPrizeList(strategyId);
    }

    @Override
    public boolean deductStock(Long strategyId, String prizeId) {
        StrategyPrize req = new StrategyPrize();
        req.setStrategyId(strategyId);
        req.setPrizeId(prizeId);
        int count = strategyPrizeDao.deductStock(req);
        return count == 1;
    }

}
