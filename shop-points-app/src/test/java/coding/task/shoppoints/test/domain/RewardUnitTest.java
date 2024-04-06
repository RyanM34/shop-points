package coding.task.shoppoints.test.domain;

import coding.task.shoppoints.domain.reward.model.entity.RewardPointsEntity;
import coding.task.shoppoints.domain.reward.model.entity.RewardTransactionEntity;
import coding.task.shoppoints.domain.reward.service.calculation.impl.BaseRewardCalculation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @description: reward domain unit test
 * @author: Ryan Mei
 * @date: 4/5/24
 */
@Slf4j
public class RewardUnitTest {

    class MockBaseRewardCalculation extends BaseRewardCalculation {
        // override protected method
        @Override
        public List<RewardTransactionEntity> getRecentThreeMonthsTransactions(Long customerId) {
            return new ArrayList<>();
        }
    }

    @Test
    public void testRewardCalculation_BASE_STRATEGY_SOME_DATA() {
        // Create a spy of MockBaseRewardCalculation
        MockBaseRewardCalculation baseRewardCalculation = spy(new MockBaseRewardCalculation());

        // Create mock transactions for testing
        RewardTransactionEntity transaction1 = mock(RewardTransactionEntity.class);
        when(transaction1.getIssuedAt()).thenReturn(LocalDateTime.now().minusDays(10));
        when(transaction1.getAmount()).thenReturn(BigDecimal.valueOf(60));
        RewardTransactionEntity transaction2 = mock(RewardTransactionEntity.class);
        when(transaction2.getIssuedAt()).thenReturn(LocalDateTime.now().minusMonths(1).minusDays(10));
        when(transaction2.getAmount()).thenReturn(BigDecimal.valueOf(120));
        RewardTransactionEntity transaction3 = mock(RewardTransactionEntity.class);
        when(transaction3.getIssuedAt()).thenReturn(LocalDateTime.now().minusMonths(2).minusDays(10));
        when(transaction3.getAmount()).thenReturn(BigDecimal.valueOf(220));

        // Create a list of mock transactions
        List<RewardTransactionEntity> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);

        // Stub the behavior of getRecentThreeMonthsTransactions to return a mock list
        when(baseRewardCalculation.getRecentThreeMonthsTransactions(anyLong())).thenReturn(transactions);

        // Call the real implementation of doCalculation method
        RewardPointsEntity rewardPointsEntity = baseRewardCalculation.doCalculation(1L);

        // Create an expected object
        RewardPointsEntity expectedEntity = RewardPointsEntity.builder()
                .lastMonth(10)
                .lastLastMonth(90)
                .lastLastLastMonth(290)
                .total(390)
                .build();

        assertEquals(expectedEntity, rewardPointsEntity);
    }

    @Test
    public void testRewardCalculation_BASE_STRATEGY_EMPTY_DATA() {
        // Create a spy of MockBaseRewardCalculation
        MockBaseRewardCalculation baseRewardCalculation = spy(new MockBaseRewardCalculation());

        // Create empty list of mock transactions
        List<RewardTransactionEntity> transactions = new ArrayList<>();

        // Stub the behavior of getRecentThreeMonthsTransactions to return a mock list
        when(baseRewardCalculation.getRecentThreeMonthsTransactions(anyLong())).thenReturn(transactions);

        // Call the real implementation of doCalculation method
        RewardPointsEntity rewardPointsEntity = baseRewardCalculation.doCalculation(1L);

        // Create an empty expected object
        RewardPointsEntity expectedEntity = RewardPointsEntity.builder()
                .build();

        assertEquals(expectedEntity, rewardPointsEntity);
    }

}
