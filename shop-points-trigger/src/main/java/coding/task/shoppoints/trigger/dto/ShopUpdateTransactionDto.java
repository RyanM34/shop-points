package coding.task.shoppoints.trigger.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @description: single request for creating transaction
 * @author: Ryan Mei
 * @date: 4/6/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopUpdateTransactionDto {
    @NotNull(message = "transactionId can't be null")
    private Long transactionId;
    private BigDecimal amount;
}
