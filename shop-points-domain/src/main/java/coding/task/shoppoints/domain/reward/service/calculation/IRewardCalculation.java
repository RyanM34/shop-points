package coding.task.shoppoints.domain.reward.service.calculation;

import coding.task.shoppoints.domain.reward.model.entity.RewardPointsEntity;
import coding.task.shoppoints.domain.reward.model.entity.RewardTransactionEntity;

/**
 * @description: Interface for defining reward calculation process
 * @author: Ryan Mei
 * @date: 4/4/24
 */
public interface IRewardCalculation {
    RewardPointsEntity doCalculation(Long customerId);
}
