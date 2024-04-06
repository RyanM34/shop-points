package coding.task.shoppoints.domain.reward.service.calculation.impl;

import coding.task.shoppoints.domain.reward.model.entity.RewardPointsEntity;
import coding.task.shoppoints.domain.reward.model.entity.RewardTransactionEntity;
import coding.task.shoppoints.domain.reward.service.calculation.IRewardCalculation;
import coding.task.shoppoints.domain.reward.service.calculation.RewardCalculationBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @description: calculate the reward points earned for each customer per month and total(three months).
 * A customer receives 2 points for every dollar spent over $100 in each transaction,
 * plus 1 point for every dollar spent over $50 in each transaction
 * (e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).
 * @author: Ryan Mei
 * @date: 4/4/24
 */
@Component
@Slf4j
public class BaseRewardCalculation extends RewardCalculationBase implements IRewardCalculation {

    private static final BigDecimal FIRST_LEVEL = BigDecimal.valueOf(50);
    private static final BigDecimal FIRST_POINT = BigDecimal.valueOf(1);

    private static final BigDecimal SECOND_LEVEL = BigDecimal.valueOf(100);
    private static final BigDecimal SECOND_POINT = BigDecimal.valueOf(2);

    @Override
    public RewardPointsEntity doCalculation(Long customerId) {
        List<RewardTransactionEntity> transactions = this.getRecentThreeMonthsTransactions(customerId);
        LocalDateTime lastMonth = LocalDateTime.now().minusMonths(1).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime lastLastMonth = LocalDateTime.now().minusMonths(2).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        int[] points = new int[3];
        transactions.forEach(t -> {
            if (t.getIssuedAt().isAfter(lastMonth)) {
                points[0] += calculateSingleTransaction(t);
            } else if (t.getIssuedAt().isAfter(lastLastMonth) && t.getIssuedAt().isBefore(lastMonth)) {
                points[1] += calculateSingleTransaction(t);
            } else if (t.getIssuedAt().isBefore(lastLastMonth)) {
                points[2] += calculateSingleTransaction(t);
            }
        });

        RewardPointsEntity result = RewardPointsEntity.builder()
                .lastMonth(points[0])
                .lastLastMonth(points[1])
                .lastLastLastMonth(points[2])
                .total(points[0] + points[1] + points[2])
                .build();
        log.info("Calculate reward points based on BaseRewardCalculation for customer: {}, result: {}", customerId, result);
        return result;
    }

    private int calculateSingleTransaction(RewardTransactionEntity rewardTransactionEntity) {
        BigDecimal total = rewardTransactionEntity.getAmount();
        int points = 0;
        if (total.compareTo(SECOND_LEVEL) > 0) { // higher than 100
            BigDecimal second = total.subtract(SECOND_LEVEL).multiply(SECOND_POINT);
            points += second.intValue();
            total = SECOND_LEVEL;
        }

        if (total.compareTo(FIRST_LEVEL) > 0) { // higher than 50
            BigDecimal first = total.subtract(FIRST_LEVEL).multiply(FIRST_POINT);
            points += first.intValue();
        }
        return points;
    }
}
