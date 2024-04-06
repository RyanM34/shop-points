package coding.task.shoppoints.infrastructure.dao;

import coding.task.shoppoints.infrastructure.po.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description: Data access Object of Transaction table
 * @author: Ryan Mei
 * @date: 4/5/24
 */
@Component
public interface TransactionDao extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCustomerIdAndIssuedAtBetween(Long customerId, LocalDateTime startDate, LocalDateTime endDate);

}
