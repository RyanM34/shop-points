package coding.task.shoppoints.domain.reward.repository;

import coding.task.shoppoints.domain.reward.model.entity.RewardTransactionEntity;

import java.util.List;

/**
 * @description: reward domain data provider
 * @author: Ryan Mei
 * @date: 4/5/24
 */
public interface IRewardRepository {

    /**
     * get last three months transactions related to customerId
     * @param customerId transaction related customerId
     * @return last three months transactions
     */
    List<RewardTransactionEntity> getRecentThreeMonthsTransactions(Long customerId);

}
