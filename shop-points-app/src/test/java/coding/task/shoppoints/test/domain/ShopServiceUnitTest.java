package coding.task.shoppoints.test.domain;

import coding.task.shoppoints.domain.shop.model.entity.ShopTransactionEntity;
import coding.task.shoppoints.domain.shop.repository.IShopRepository;
import coding.task.shoppoints.domain.shop.service.ShopService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @description: Shop Service Unit Test
 * @author: Ryan Mei
 * @date: 4/8/24
 */
@ExtendWith(MockitoExtension.class)
public class ShopServiceUnitTest {

    @Mock
    IShopRepository shopRepository;

    @InjectMocks
    ShopService shopService;

    @Test
    public void testCreateTransaction() {
        ShopTransactionEntity expected = ShopTransactionEntity.builder()
                .transactionId(1L)
                .customerId(1L)
                .amount(BigDecimal.valueOf(100))
                .issuedAt(LocalDateTime.now()).build();
        ShopTransactionEntity input = ShopTransactionEntity.builder()
                .customerId(1L)
                .amount(BigDecimal.valueOf(100))
                .issuedAt(LocalDateTime.now()).build();
        // Create mock behavior
        when(shopRepository.createTransaction(input)).thenReturn(expected);
        // Call the method
        ShopTransactionEntity actual = shopService.createTransaction(input);

        assertEquals(expected, actual);
    }

    @Test
    public void testUpdateTransaction() {
        ShopTransactionEntity expected = ShopTransactionEntity.builder()
                .transactionId(1L)
                .customerId(1L)
                .amount(BigDecimal.valueOf(50))
                .issuedAt(LocalDateTime.now()).build();

        ShopTransactionEntity input = ShopTransactionEntity.builder()
                .transactionId(1L)
                .amount(BigDecimal.valueOf(50))
                .issuedAt(LocalDateTime.now()).build();
        // Create mock behavior
        when(shopRepository.createTransaction(input)).thenReturn(expected);
        // Call the method
        ShopTransactionEntity actual = shopService.createTransaction(input);

        assertEquals(actual, expected);
    }

    @Test
    public void testCreateTransactions() {
        // Create expected result
        List<ShopTransactionEntity> expected = new ArrayList<>();
        ShopTransactionEntity expectedEntity1 = ShopTransactionEntity.builder()
                .transactionId(1L)
                .customerId(1L)
                .amount(BigDecimal.valueOf(100))
                .issuedAt(LocalDateTime.now()).build();
        ShopTransactionEntity expectedEntity2 = ShopTransactionEntity.builder()
                .transactionId(2L)
                .customerId(1L)
                .amount(BigDecimal.valueOf(50))
                .issuedAt(LocalDateTime.now()).build();
        expected.add(expectedEntity1);
        expected.add(expectedEntity2);

        // Create expected input
        List<ShopTransactionEntity> input = new ArrayList<>();
        ShopTransactionEntity inputEntity1 = ShopTransactionEntity.builder()
                .customerId(1L)
                .amount(BigDecimal.valueOf(100))
                .issuedAt(LocalDateTime.now()).build();
        ShopTransactionEntity inputEntity2 = ShopTransactionEntity.builder()
                .customerId(1L)
                .amount(BigDecimal.valueOf(50))
                .issuedAt(LocalDateTime.now()).build();
        input.add(inputEntity1);
        input.add(inputEntity2);

        // Create mock behavior
        when(shopRepository.createTransactions(input)).thenReturn(expected);
        // Call the method
        List<ShopTransactionEntity> actual = shopService.createTransactions(input);

        assertEquals(expected, actual);
    }

    @Test
    public void testUpdateTransactions() {
        // Create expected result
        List<ShopTransactionEntity> expected = new ArrayList<>();
        ShopTransactionEntity expectedEntity1 = ShopTransactionEntity.builder()
                .transactionId(1L)
                .customerId(1L)
                .amount(BigDecimal.valueOf(100))
                .issuedAt(LocalDateTime.now()).build();
        ShopTransactionEntity expectedEntity2 = ShopTransactionEntity.builder()
                .transactionId(2L)
                .customerId(1L)
                .amount(BigDecimal.valueOf(50))
                .issuedAt(LocalDateTime.now()).build();
        expected.add(expectedEntity1);
        expected.add(expectedEntity2);

        // Create expected input
        List<ShopTransactionEntity> input = new ArrayList<>();
        ShopTransactionEntity inputEntity1 = ShopTransactionEntity.builder()
                .transactionId(1L)
                .amount(BigDecimal.valueOf(100))
                .issuedAt(LocalDateTime.now()).build();
        ShopTransactionEntity inputEntity2 = ShopTransactionEntity.builder()
                .transactionId(2L)
                .amount(BigDecimal.valueOf(50))
                .issuedAt(LocalDateTime.now()).build();
        input.add(inputEntity1);
        input.add(inputEntity2);

        // Create mock behavior
        when(shopRepository.createTransactions(input)).thenReturn(expected);
        // Call the method
        List<ShopTransactionEntity> actual = shopService.createTransactions(input);

        assertEquals(actual, expected);
    }

}
