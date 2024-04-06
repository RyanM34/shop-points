package coding.task.shoppoints.domain.reward.service.factory;

import coding.task.shoppoints.common.enums.RewardCalculationType;
import coding.task.shoppoints.domain.reward.service.calculation.IRewardCalculation;
import org.springframework.stereotype.Service;

/**
 * @description: Reward calculation factory
 * @author: Ryan Mei
 * @date: 4/4/24
 */
@Service
public class RewardCalculationFactory extends CalculationConfig {

    public IRewardCalculation getRewardCalculationService(RewardCalculationType type) {
        return calculationMap.get(type);
    }
}
