package coding.task.shoppoints.domain.reward.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @description: Award Transaction entity
 * @author: Ryan Mei
 * @date: 4/4/24
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RewardTransactionEntity {
    private Long transactionId;
    private Long customerId;
    private BigDecimal amount;
    private LocalDateTime issuedAt;
}
