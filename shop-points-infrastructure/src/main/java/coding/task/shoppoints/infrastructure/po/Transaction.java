package coding.task.shoppoints.infrastructure.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @description: Transaction object mapping to transaction table
 * @author: Ryan Mei
 * @date: 4/4/24
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "issued_at")
    private LocalDateTime issuedAt;

}
