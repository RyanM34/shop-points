package coding.task.shoppoints.domain.shop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @description: Shop Transaction Entity
 * @author: Ryan Mei
 * @date: 4/5/24
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ShopTransactionEntity {
    private Long transactionId;
    private Long customerId;
    private BigDecimal amount;
    private LocalDateTime issuedAt;
}
