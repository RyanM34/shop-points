package coding.task.shoppoints.trigger.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @description: single request for creating transaction
 * @author: Ryan Mei
 * @date: 4/5/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopCreateTransactionDto implements Serializable {
    @NotNull(message = "customerId can't be null")
    private Long customerId;

    private BigDecimal amount;

    @NotNull(message = "issuedAt can't be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime issuedAt;
}
