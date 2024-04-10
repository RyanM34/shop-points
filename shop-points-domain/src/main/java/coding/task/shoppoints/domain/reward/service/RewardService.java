package coding.task.shoppoints.domain.reward.service;

import coding.task.shoppoints.common.enums.RewardCalculationType;
import coding.task.shoppoints.domain.reward.model.entity.RewardPointsEntity;
import coding.task.shoppoints.domain.reward.service.calculation.IRewardCalculation;
import coding.task.shoppoints.domain.reward.service.factory.RewardCalculationFactory;
import coding.task.shoppoints.domain.shop.service.IShopService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: implementation of reward service
 * @author: Ryan Mei
 * @date: 4/5/24
 */
@Service
public class RewardService implements IRewardService {

    @Resource
    RewardCalculationFactory rewardCalculationFactory;

    @Override
    public RewardPointsEntity getRewardPoints(Long customerId, RewardCalculationType type) {
        IRewardCalculation rewardCalculation = rewardCalculationFactory.getRewardCalculationService(type);
        return rewardCalculation.doCalculation(customerId);
    }

    @Override
    public List<RewardPointsEntity> getRewardPointsSummary(List<Long> customerIds, RewardCalculationType type) {
        IRewardCalculation rewardCalculation = rewardCalculationFactory.getRewardCalculationService(type);
        List<RewardPointsEntity> summary = new ArrayList<>();
        customerIds.forEach(id -> summary.add(rewardCalculation.doCalculation(id)));
        return summary;
    }
}
