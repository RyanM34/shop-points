package coding.task.shoppoints.domain.reward.service.calculation.impl;

import coding.task.shoppoints.common.exceptions.NegativeTransactionAmountException;
import coding.task.shoppoints.common.exceptions.RewardPointsCalculationOverflowException;
import coding.task.shoppoints.domain.reward.model.entity.RewardPointsEntity;
import coding.task.shoppoints.domain.reward.model.entity.RewardTransactionEntity;
import coding.task.shoppoints.domain.reward.service.calculation.IRewardCalculation;
import coding.task.shoppoints.domain.reward.service.calculation.RewardCalculationBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
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
        // set currentMonthStart to Month/01/00:00:00:000
        LocalDateTime currentMonthStart = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime lastMonthStart = currentMonthStart.minusMonths(1);
        LocalDateTime twoMonthsAgoStart = currentMonthStart.minusMonths(2);
        LocalDateTime threeMonthsAgoStart = currentMonthStart.minusMonths(3);

        long[] points = new long[3];
        transactions.forEach(t -> {
            // calculate last month transaction
            if (t.getIssuedAt().isAfter(lastMonthStart) && t.getIssuedAt().isBefore(currentMonthStart)) {
                points[0] += calculateSingleTransaction(t);
                // calculate two months ago transaction
            } else if (t.getIssuedAt().isAfter(twoMonthsAgoStart) && t.getIssuedAt().isBefore(lastMonthStart)) {
                points[1] += calculateSingleTransaction(t);
                // calculate three months ago transaction
            } else if (t.getIssuedAt().isAfter(threeMonthsAgoStart) && t.getIssuedAt().isBefore(twoMonthsAgoStart)) {
                points[2] += calculateSingleTransaction(t);
            }
            // check if the results exceed Integer.MAX_VALUE
            if (points[0] > Integer.MAX_VALUE || points[1] > Integer.MAX_VALUE ||
                    points[2] > Integer.MAX_VALUE || points[0] + points[1] + points[2] > Integer.MAX_VALUE) {
                throw new RewardPointsCalculationOverflowException(transactions.toString());
            }
        });

        RewardPointsEntity result = RewardPointsEntity.builder()
                .lastMonth((int) points[0])
                .twoMonthsAgo((int) points[1])
                .threeMonthsAgo((int) points[2])
                .total((int) (points[0] + points[1] + points[2]))
                .build();
        log.info("Calculate reward points based on BaseRewardCalculation for customer: {}, result: {}", customerId, result);
        return result;
    }

    private int calculateSingleTransaction(RewardTransactionEntity rewardTransactionEntity) {
        BigDecimal total = rewardTransactionEntity.getAmount();
        if (total.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw new NegativeTransactionAmountException(rewardTransactionEntity.toString());
        }
        BigDecimal points = BigDecimal.valueOf(0);
        if (total.compareTo(SECOND_LEVEL) > 0) { // higher than 100
            BigDecimal second = total.subtract(SECOND_LEVEL).multiply(SECOND_POINT);
            points = points.add(second);
            total = SECOND_LEVEL;
        }

        if (total.compareTo(FIRST_LEVEL) > 0) { // higher than 50
            BigDecimal first = total.subtract(FIRST_LEVEL).multiply(FIRST_POINT);
            points = points.add(first);
        }
        if (points.compareTo(BigDecimal.valueOf(Integer.MAX_VALUE)) > 0) {
            throw new RewardPointsCalculationOverflowException(rewardTransactionEntity.toString());
        }
        return points.intValue();
    }
}
