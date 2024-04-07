package coding.task.shoppoints.test.domain;

import coding.task.shoppoints.common.exceptions.NegativeTransactionAmountException;
import coding.task.shoppoints.common.exceptions.RewardPointsCalculationOverflowException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    public void testRewardCalculation_BASE_STRATEGY_LESS_THAN_THREE_TRANSACTIONS() {
        // Create a spy of MockBaseRewardCalculation
        MockBaseRewardCalculation baseRewardCalculation = spy(new MockBaseRewardCalculation());

        // Create mock transactions for testing
        RewardTransactionEntity transaction1 = mock(RewardTransactionEntity.class);
        when(transaction1.getIssuedAt()).thenReturn(LocalDateTime.now().minusMonths(1));
        when(transaction1.getAmount()).thenReturn(BigDecimal.valueOf(100));
        RewardTransactionEntity transaction2 = mock(RewardTransactionEntity.class);
        when(transaction2.getIssuedAt()).thenReturn(LocalDateTime.now().minusMonths(2));
        when(transaction2.getAmount()).thenReturn(BigDecimal.valueOf(200));

        // Create a list of mock transactions
        List<RewardTransactionEntity> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);

        // Stub the behavior of getRecentThreeMonthsTransactions to return a mock list
        when(baseRewardCalculation.getRecentThreeMonthsTransactions(anyLong())).thenReturn(transactions);

        // Call the real implementation of doCalculation method
        RewardPointsEntity rewardPointsEntity = baseRewardCalculation.doCalculation(1L);

        // Create an expected object
        RewardPointsEntity expectedEntity = RewardPointsEntity.builder()
                .lastMonth(50)
                .twoMonthsAgo(250)
                .total(300)
                .build();

        assertEquals(expectedEntity, rewardPointsEntity);
    }

    @Test
    public void testRewardCalculation_BASE_STRATEGY_MORE_THAN_THREE_TRANSACTIONS() {
        // Create a spy of MockBaseRewardCalculation
        MockBaseRewardCalculation baseRewardCalculation = spy(new MockBaseRewardCalculation());

        // Create mock transactions for testing
        RewardTransactionEntity transaction1 = mock(RewardTransactionEntity.class);
        when(transaction1.getIssuedAt()).thenReturn(LocalDateTime.now().minusMonths(1));
        when(transaction1.getAmount()).thenReturn(BigDecimal.valueOf(60));
        RewardTransactionEntity transaction2 = mock(RewardTransactionEntity.class);
        when(transaction2.getIssuedAt()).thenReturn(LocalDateTime.now().minusMonths(2));
        when(transaction2.getAmount()).thenReturn(BigDecimal.valueOf(120));
        RewardTransactionEntity transaction3 = mock(RewardTransactionEntity.class);
        when(transaction3.getIssuedAt()).thenReturn(LocalDateTime.now().minusMonths(3));
        when(transaction3.getAmount()).thenReturn(BigDecimal.valueOf(220));
        RewardTransactionEntity transaction4 = mock(RewardTransactionEntity.class);
        when(transaction4.getIssuedAt()).thenReturn(LocalDateTime.now().minusMonths(1));
        when(transaction4.getAmount()).thenReturn(BigDecimal.valueOf(60));

        // Create a list of mock transactions
        List<RewardTransactionEntity> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);
        transactions.add(transaction4);

        // Stub the behavior of getRecentThreeMonthsTransactions to return a mock list
        when(baseRewardCalculation.getRecentThreeMonthsTransactions(anyLong())).thenReturn(transactions);

        // Call the real implementation of doCalculation method
        RewardPointsEntity rewardPointsEntity = baseRewardCalculation.doCalculation(1L);

        // Create an expected object
        RewardPointsEntity expectedEntity = RewardPointsEntity.builder()
                .lastMonth(20)
                .twoMonthsAgo(90)
                .threeMonthsAgo(290)
                .total(400)
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

    @Test
    public void testRewardCalculation_BASE_STRATEGY_WITH_TRANSACTIONS_BEFORE_THREE_MONTHS() {
        // Create a spy of MockBaseRewardCalculation
        MockBaseRewardCalculation baseRewardCalculation = spy(new MockBaseRewardCalculation());

        // Create mock transactions for testing
        RewardTransactionEntity transaction1 = mock(RewardTransactionEntity.class);
        when(transaction1.getIssuedAt()).thenReturn(LocalDateTime.now().minusMonths(4));
        when(transaction1.getAmount()).thenReturn(BigDecimal.valueOf(60));
        RewardTransactionEntity transaction2 = mock(RewardTransactionEntity.class);
        when(transaction2.getIssuedAt()).thenReturn(LocalDateTime.now().minusMonths(5));
        when(transaction2.getAmount()).thenReturn(BigDecimal.valueOf(120));
        RewardTransactionEntity transaction3 = mock(RewardTransactionEntity.class);
        when(transaction3.getIssuedAt()).thenReturn(LocalDateTime.now().minusMonths(6));
        when(transaction3.getAmount()).thenReturn(BigDecimal.valueOf(220));
        RewardTransactionEntity transaction4 = mock(RewardTransactionEntity.class);
        when(transaction4.getIssuedAt()).thenReturn(LocalDateTime.now().minusMonths(7));
        when(transaction4.getAmount()).thenReturn(BigDecimal.valueOf(60));

        // Create a list of mock transactions
        List<RewardTransactionEntity> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);
        transactions.add(transaction4);

        // Stub the behavior of getRecentThreeMonthsTransactions to return a mock list
        when(baseRewardCalculation.getRecentThreeMonthsTransactions(anyLong())).thenReturn(transactions);

        // Call the real implementation of doCalculation method
        RewardPointsEntity rewardPointsEntity = baseRewardCalculation.doCalculation(1L);

        // Create an expected object
        RewardPointsEntity expectedEntity = RewardPointsEntity.builder()
                .build();

        assertEquals(expectedEntity, rewardPointsEntity);
    }

    @Test
    public void testRewardCalculation_BASE_STRATEGY_SINGLE_TRANSACTION_EXCEED_INT_MAX() {
        // Create a spy of MockBaseRewardCalculation
        MockBaseRewardCalculation baseRewardCalculation = spy(new MockBaseRewardCalculation());

        // Create mock transactions for testing
        RewardTransactionEntity transaction1 = mock(RewardTransactionEntity.class);
        when(transaction1.getIssuedAt()).thenReturn(LocalDateTime.now().minusMonths(1));
        when(transaction1.getAmount()).thenReturn(BigDecimal.valueOf(Integer.MAX_VALUE));

        // Create a list of mock transactions
        List<RewardTransactionEntity> transactions = new ArrayList<>();
        transactions.add(transaction1);

        // Stub the behavior of getRecentThreeMonthsTransactions to return a mock list
        when(baseRewardCalculation.getRecentThreeMonthsTransactions(anyLong())).thenReturn(transactions);

        // Assert that the doCalculation method throws RewardPointsCalculationOverflowException
        assertThrows(RewardPointsCalculationOverflowException.class, () -> {
            baseRewardCalculation.doCalculation(1L);
        });
    }

    @Test
    public void testRewardCalculation_BASE_STRATEGY_MULTIPLE_TRANSACTIONS_EXCEED_INT_MAX() {
        // Create a spy of MockBaseRewardCalculation
        MockBaseRewardCalculation baseRewardCalculation = spy(new MockBaseRewardCalculation());

        // Create mock transactions for testing
        RewardTransactionEntity transaction1 = mock(RewardTransactionEntity.class);
        when(transaction1.getIssuedAt()).thenReturn(LocalDateTime.now().minusMonths(1));
        when(transaction1.getAmount()).thenReturn(BigDecimal.valueOf(Integer.MAX_VALUE / 3));
        RewardTransactionEntity transaction2 = mock(RewardTransactionEntity.class);
        when(transaction2.getIssuedAt()).thenReturn(LocalDateTime.now().minusMonths(1));
        when(transaction2.getAmount()).thenReturn(BigDecimal.valueOf(Integer.MAX_VALUE / 3));


        // Create a list of mock transactions
        List<RewardTransactionEntity> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);

        // Stub the behavior of getRecentThreeMonthsTransactions to return a mock list
        when(baseRewardCalculation.getRecentThreeMonthsTransactions(anyLong())).thenReturn(transactions);

        // Assert that the doCalculation method throws RewardPointsCalculationOverflowException
        assertThrows(RewardPointsCalculationOverflowException.class, () -> {
            baseRewardCalculation.doCalculation(1L);
        });
    }

    @Test
    public void testRewardCalculation_BASE_STRATEGY_WITH_THRESHOLD_NUMBERS() {
        // Create a spy of MockBaseRewardCalculation
        MockBaseRewardCalculation baseRewardCalculation = spy(new MockBaseRewardCalculation());

        // Create mock transactions for testing
        RewardTransactionEntity transaction1 = mock(RewardTransactionEntity.class);
        when(transaction1.getIssuedAt()).thenReturn(LocalDateTime.now().minusMonths(1));
        when(transaction1.getAmount()).thenReturn(BigDecimal.valueOf(50));
        RewardTransactionEntity transaction2 = mock(RewardTransactionEntity.class);
        when(transaction2.getIssuedAt()).thenReturn(LocalDateTime.now().minusMonths(2));
        when(transaction2.getAmount()).thenReturn(BigDecimal.valueOf(100));


        // Create a list of mock transactions
        List<RewardTransactionEntity> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);

        // Stub the behavior of getRecentThreeMonthsTransactions to return a mock list
        when(baseRewardCalculation.getRecentThreeMonthsTransactions(anyLong())).thenReturn(transactions);

        // Call the real implementation of doCalculation method
        RewardPointsEntity rewardPointsEntity = baseRewardCalculation.doCalculation(1L);

        // Create an expected object
        RewardPointsEntity expectedEntity = RewardPointsEntity.builder()
                .twoMonthsAgo(50)
                .total(50)
                .build();
        assertEquals(expectedEntity, rewardPointsEntity);
    }

    @Test
    public void testRewardCalculation_BASE_STRATEGY_WITH_NEGATIVE_TRANSACTION_AMOUNT() {
        // Create a spy of MockBaseRewardCalculation
        MockBaseRewardCalculation baseRewardCalculation = spy(new MockBaseRewardCalculation());

        // Create mock transactions for testing
        RewardTransactionEntity transaction1 = mock(RewardTransactionEntity.class);
        when(transaction1.getIssuedAt()).thenReturn(LocalDateTime.now().minusMonths(1));
        when(transaction1.getAmount()).thenReturn(BigDecimal.valueOf(-0.10));

        // Create a list of mock transactions
        List<RewardTransactionEntity> transactions = new ArrayList<>();
        transactions.add(transaction1);

        // Stub the behavior of getRecentThreeMonthsTransactions to return a mock list
        when(baseRewardCalculation.getRecentThreeMonthsTransactions(anyLong())).thenReturn(transactions);

        // Assert that the doCalculation method throws NegativeTransactionAmountException
        assertThrows(NegativeTransactionAmountException.class, () -> {
            baseRewardCalculation.doCalculation(1L);
        });
    }

}
