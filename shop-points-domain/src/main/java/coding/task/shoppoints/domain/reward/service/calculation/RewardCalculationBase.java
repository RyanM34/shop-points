package coding.task.shoppoints.domain.reward.service.calculation;

import coding.task.shoppoints.domain.reward.model.entity.RewardTransactionEntity;
import coding.task.shoppoints.domain.reward.repository.IRewardRepository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: provide data access support for Reward Calculation
 * @author: Ryan Mei
 * @date: 4/5/24
 */
public class RewardCalculationBase {

    @Resource
    IRewardRepository rewardRepository;

    protected List<RewardTransactionEntity> getRecentThreeMonthsTransactions(Long customerId) {
        return rewardRepository.getRecentThreeMonthsTransactions(customerId);
    }
}
