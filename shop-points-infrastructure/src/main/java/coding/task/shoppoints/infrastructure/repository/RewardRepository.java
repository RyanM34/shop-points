package coding.task.shoppoints.infrastructure.repository;

import coding.task.shoppoints.domain.reward.model.entity.RewardTransactionEntity;
import coding.task.shoppoints.domain.reward.repository.IRewardRepository;
import coding.task.shoppoints.infrastructure.dao.TransactionDao;
import coding.task.shoppoints.infrastructure.po.Transaction;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: implementation of reward domain repository
 * @author: Ryan Mei
 * @date: 4/4/24
 */
@Repository
public class RewardRepository implements IRewardRepository {

    @Resource
    TransactionDao transactionDao;

    @Override
    public List<RewardTransactionEntity> getRecentThreeMonthsTransactions(Long customerId) {
        LocalDateTime startDate = LocalDateTime.now().minusMonths(3).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endDate = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0).minusNanos(1);
        List<Transaction> transactions = transactionDao.findByCustomerIdAndIssuedAtBetween(customerId, startDate, endDate);
        return transactions.stream().map(t -> RewardTransactionEntity.builder()
                        .transactionId(t.getTransactionId())
                        .amount(t.getAmount())
                        .customerId(t.getCustomerId())
                        .issuedAt(t.getIssuedAt())
                        .build())
                .collect(Collectors.toList());
    }

}
