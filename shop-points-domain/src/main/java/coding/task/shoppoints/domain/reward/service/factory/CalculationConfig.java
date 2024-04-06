package coding.task.shoppoints.domain.reward.service.factory;

import coding.task.shoppoints.common.enums.RewardCalculationType;
import coding.task.shoppoints.domain.reward.service.calculation.IRewardCalculation;
import coding.task.shoppoints.domain.reward.service.calculation.impl.BaseRewardCalculation;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: Reward calculation configuration
 * @author: Ryan Mei
 * @date: 4/4/24
 */
public class CalculationConfig {
    protected static Map<RewardCalculationType, IRewardCalculation> calculationMap = new ConcurrentHashMap<>();

    @Resource
    private BaseRewardCalculation baseRewardCalculation;

    @PostConstruct
    public void init() {
        calculationMap.put(RewardCalculationType.BASE_STRATEGY, baseRewardCalculation);
    }
}
