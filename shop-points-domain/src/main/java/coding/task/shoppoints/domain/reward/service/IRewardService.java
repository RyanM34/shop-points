package coding.task.shoppoints.domain.reward.service;

import coding.task.shoppoints.common.enums.RewardCalculationType;
import coding.task.shoppoints.domain.reward.model.entity.RewardPointsEntity;

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
}
