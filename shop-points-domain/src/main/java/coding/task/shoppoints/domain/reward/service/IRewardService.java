package coding.task.shoppoints.domain.reward.service;

import coding.task.shoppoints.common.enums.RewardCalculationType;
import coding.task.shoppoints.domain.reward.model.entity.RewardPointsEntity;

import java.util.List;

/**
 * @description: provide reward service
 * @author: Ryan Mei
 * @date: 4/5/24
 */
public interface IRewardService {

    /**
     * calculate customer reward points
     * @param customerId
     * @param type
     * @return three months reward details
     */
    RewardPointsEntity getRewardPoints(Long customerId, RewardCalculationType type);

    /**
     * get all customer reward points
     * @param customerIds
     * @param type
     * @return summary
     */
    List<RewardPointsEntity> getRewardPointsSummary(List<Long> customerIds, RewardCalculationType type);

}
