package com.bs6.lottery.domain.strategy.service.draw;

import com.bs6.lottery.common.Constants;
import com.bs6.lottery.domain.strategy.model.*;
import com.bs6.lottery.domain.strategy.repository.IStrategyRepository;
import com.bs6.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDrawBase extends DrawAlgoConfig implements IDrawExec{
    private Logger logger = LoggerFactory.getLogger(AbstractDrawBase.class);

    @Resource
    protected IStrategyRepository strategyRepository;

    @Override
    public DrawResult doDrawExec(DrawReq req) {
        //step 1. get the lottery strategy
        StrategyAgg strategyAgg = queryStrategyAgg(req.getStrategyId());
        StrategyBriefVO strategyBriefVO = strategyAgg.getStrategyBrief();

        //step 2. check if the strategy is initialized and cached into HashMap
        checkAndInitRateData(strategyAgg.getStrategyId(),strategyBriefVO.getStrategyMode(),strategyAgg.getStrategyPrizeList());

        //step 3. get the excludePrizeList
        List<String> excludePrizeIds = queryExcludePrizeIds(req.getStrategyId());

        //step 4. get the draw result
        String prizeId = drawAlgorithm(req.getStrategyId(), drawAlgorithmGroup.get(strategyBriefVO.getStrategyMode()), excludePrizeIds);


        return buildDrawResult(req.getUid(),strategyBriefVO.getStrategyId(),prizeId);
    }

    private StrategyAgg queryStrategyAgg(Long strategyId){
        return strategyRepository.queryStrategyAgg(strategyId);
    }

    private void checkAndInitRateData(Long strategyId, Integer strategyMode, List<StrategyPrizeBriefVO> strategyPrizeList) {
        logger.info(strategyMode.toString());
        // get the mapping Algorithm(strategy pattern)
        IDrawAlgorithm drawAlgorithm = drawAlgorithmGroup.get(strategyMode);

        //check if the prizeProbabilityInfo list cache exist
        if (drawAlgorithm.probabilityInfoIsExist(strategyId)) {
            return;
        }

        // 解析并初始化中奖概率数据到散列表
        List<PrizeProbabilityInfo> prizeProbabilityInfoList = new ArrayList<>(strategyPrizeList.size());
        for (StrategyPrizeBriefVO strategyPrize : strategyPrizeList) {
            prizeProbabilityInfoList.add(new PrizeProbabilityInfo(strategyPrize.getPrizeId(), strategyPrize.getPrizeProbability()));
        }

        drawAlgorithm.initRateTuple(strategyId, strategyMode, prizeProbabilityInfoList);

    }

    protected abstract List<String> queryExcludePrizeIds(Long strategyId);

    protected abstract String drawAlgorithm(Long strategyId, IDrawAlgorithm drawAlgorithm, List<String> excludePrizeIds);

    private DrawResult buildDrawResult(String uId, Long strategyId, String prizeId) {
        if (null == prizeId) {
            logger.info("执行策略抽奖完成【未中奖】，用户：{} 策略ID：{}", uId, strategyId);
            return new DrawResult(uId, strategyId, Constants.DrawStatus.LOSE.getCode());
        }

        PrizeBriefVO prize = queryPrizeInfoByPrizeId(prizeId);
        DrawPrizeInfo drawPrizeInfo = new DrawPrizeInfo(prize.getPrizeId(), prize.getPrizeType(), prize.getPrizeName(), prize.getPrizeContent());
        logger.info("执行策略抽奖完成【已中奖】，用户：{} 策略ID：{} 奖品ID：{} 奖品名称：{}", uId, strategyId, prizeId, prize.getPrizeName());

        return new DrawResult(uId, strategyId, Constants.DrawStatus.WIN.getCode(), drawPrizeInfo);
    }

    private PrizeBriefVO queryPrizeInfoByPrizeId(String prizeId){
        return strategyRepository.queryPrizeInfo(prizeId);
    }

}
