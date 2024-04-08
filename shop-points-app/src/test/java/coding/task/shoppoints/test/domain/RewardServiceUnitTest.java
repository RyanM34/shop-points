package coding.task.shoppoints.test.domain;

import coding.task.shoppoints.common.enums.RewardCalculationType;
import coding.task.shoppoints.domain.reward.model.entity.RewardPointsEntity;
import coding.task.shoppoints.domain.reward.service.RewardService;
import coding.task.shoppoints.domain.reward.service.calculation.IRewardCalculation;
import coding.task.shoppoints.domain.reward.service.factory.RewardCalculationFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @description: Reward Service Unit Test
 * @author: Ryan Mei
 * @date: 4/8/24
 */
@ExtendWith(MockitoExtension.class)
public class RewardServiceUnitTest {

    @Mock
    RewardCalculationFactory rewardCalculationFactory;

    @Mock
    IRewardCalculation rewardCalculation;

    @InjectMocks
    RewardService rewardService;

    @Test
    public void testGetRewardPoints() {
        Long customerId = 1L;
        RewardCalculationType type = RewardCalculationType.BASE_STRATEGY;
        RewardPointsEntity rewardPointsEntity = RewardPointsEntity.builder()
                .lastMonth(50)
                .total(50).build();

        // Create mock behavior
        when(rewardCalculationFactory.getRewardCalculationService(type)).thenReturn(rewardCalculation);
        when(rewardCalculation.doCalculation(1L)).thenReturn(rewardPointsEntity);

        RewardPointsEntity actualPoints = rewardService.getRewardPoints(1L, type);

        verify(rewardCalculationFactory).getRewardCalculationService(type);
        verify(rewardCalculation).doCalculation(customerId);
        assertEquals(rewardPointsEntity, actualPoints);

    }
}
