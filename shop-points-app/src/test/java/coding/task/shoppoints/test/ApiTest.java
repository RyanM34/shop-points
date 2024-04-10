package coding.task.shoppoints.test;

import coding.task.shoppoints.trigger.dto.ShopCreateTransactionDto;
import coding.task.shoppoints.trigger.dto.ShopUpdateTransactionDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @description: Api test
 * @author: Ryan Mei
 * @date: 4/5/24
 */

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Fix order based on @Order annotations
public class ApiTest {

    @Autowired
    MockMvc mvc;

    ObjectMapper mapper = new ObjectMapper();

    @Value("${app.config.api-version}")
    private String apiVersion;

    private String urlPrefix;

    @PostConstruct
    public void init() {
        urlPrefix = "/" + apiVersion;
    }

    // Create a single transaction test
    @Test
    @Order(1)
    public void testCreateTransaction_Success() throws Exception {
        ShopCreateTransactionDto transactionDto = ShopCreateTransactionDto.builder()
                .customerId(1L)
                .issuedAt(LocalDateTime.now().minusMonths(1))
                .amount(BigDecimal.valueOf(120))
                .build();

        mvc.perform(MockMvcRequestBuilders
                        .post(urlPrefix + "/shop/transaction")
                        .content(mapper.writeValueAsString(transactionDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    // Create a single invalid transaction test
    @Test
    @Order(2)
    public void testCreateTransaction_InvalidTransaction() throws Exception {
        // miss customerId
        ShopCreateTransactionDto transactionDto1 = ShopCreateTransactionDto.builder()
                .issuedAt(LocalDateTime.now().minusMonths(1))
                .amount(BigDecimal.valueOf(120)).build();

        mvc.perform(MockMvcRequestBuilders
                        .post(urlPrefix + "/shop/transaction")
                        .content(mapper.writeValueAsString(transactionDto1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        // miss issuedAt
        ShopCreateTransactionDto transactionDto2 = ShopCreateTransactionDto.builder()
                .customerId(1L)
                .amount(BigDecimal.valueOf(120)).build();

        mvc.perform(MockMvcRequestBuilders
                        .post(urlPrefix + "/shop/transaction")
                        .content(mapper.writeValueAsString(transactionDto2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    // // Update a single transaction test
    // Careful!
    // When solely testing this api, need real data(customer and transaction) in database
    // Since this demo use h2, h2 has not preload data, this test will fail
    @Test
    @Order(3)
    public void testUpdateTransaction_Success() throws Exception {
        ShopUpdateTransactionDto transactionDto = ShopUpdateTransactionDto.builder()
                .transactionId(1L)
                .amount(BigDecimal.valueOf(220)).build();

        mvc.perform(MockMvcRequestBuilders
                        .patch(urlPrefix + "/shop/transaction")
                        .content(mapper.writeValueAsString(transactionDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // Update a single transaction test with null transactionId
    @Test
    @Order(4)
    public void testUpdateTransaction_NullTransactionId() throws Exception {
        ShopUpdateTransactionDto transactionDto = ShopUpdateTransactionDto.builder()
                .amount(BigDecimal.valueOf(220)).build();

        mvc.perform(MockMvcRequestBuilders
                        .patch(urlPrefix + "/shop/transaction")
                        .content(mapper.writeValueAsString(transactionDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    // Update a single transaction test with invalid transactionId
    @Test
    @Order(4)
    public void testUpdateTransaction_InvalidTransactionId() throws Exception {
        ShopUpdateTransactionDto transactionDto = ShopUpdateTransactionDto.builder()
                .transactionId(100L)
                .amount(BigDecimal.valueOf(220)).build();

        mvc.perform(MockMvcRequestBuilders
                        .patch(urlPrefix + "/shop/transaction")
                        .content(mapper.writeValueAsString(transactionDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    // Create a list of transactions test
    @Test
    @Order(5)
    public void testCreateTransactionBatch_Success() throws Exception {
        ShopCreateTransactionDto transactionDto1 = ShopCreateTransactionDto.builder()
                .customerId(1L)
                .issuedAt(LocalDateTime.now().minusMonths(1))
                .amount(BigDecimal.valueOf(120)).build();
        ShopCreateTransactionDto transactionDto2 = ShopCreateTransactionDto.builder()
                .customerId(2L)
                .issuedAt(LocalDateTime.now().minusMonths(2))
                .amount(BigDecimal.valueOf(60)).build();
        List<ShopCreateTransactionDto> transactions = new ArrayList<>();
        transactions.add(transactionDto1);
        transactions.add(transactionDto2);

        mvc.perform(MockMvcRequestBuilders
                        .post(urlPrefix + "/shop/transaction/batch")
                        .content(mapper.writeValueAsString(transactions))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // Create a list of transactions test including an invalid transaction
    @Test
    @Order(6)
    public void testCreateTransactionBatch_InvalidTransaction() throws Exception {
        ShopCreateTransactionDto transactionDto1 = ShopCreateTransactionDto.builder()
                .issuedAt(LocalDateTime.now().minusMonths(1))
                .amount(BigDecimal.valueOf(120)).build(); // miss customerId
        ShopCreateTransactionDto transactionDto2 = ShopCreateTransactionDto.builder()
                .customerId(1L) // miss issuedAt
                .amount(BigDecimal.valueOf(60)).build();
        List<ShopCreateTransactionDto> transactions = new ArrayList<>();
        transactions.add(transactionDto1);
        transactions.add(transactionDto2);

        mvc.perform(MockMvcRequestBuilders
                        .post(urlPrefix + "/shop/transaction/batch")
                        .content(mapper.writeValueAsString(transactions))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    // Create an empty list of transactions test
    @Test
    @Order(7)
    public void testCreateTransactionBatch_EmptyTransactions() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .post(urlPrefix + "/shop/transaction/batch")
                        .content(mapper.writeValueAsString(new ArrayList<>()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    // Update a list of transactions test
    // Careful!
    // When solely testing this api, need real transaction data in database
    // Since this demo use h2, h2 has not preload data, this test will fail
    @Test
    @Order(8)
    public void testUpdateTransactionBatch_Success() throws Exception {
        ShopUpdateTransactionDto transactionDto1 = ShopUpdateTransactionDto.builder()
                .transactionId(1L)
                .amount(BigDecimal.valueOf(220)).build();
        ShopUpdateTransactionDto transactionDto2 = ShopUpdateTransactionDto.builder()
                .transactionId(2L)
                .amount(BigDecimal.valueOf(160)).build();
        List<ShopUpdateTransactionDto> transactions = new ArrayList<>();
        transactions.add(transactionDto1);
        transactions.add(transactionDto2);

        mvc.perform(MockMvcRequestBuilders
                        .patch(urlPrefix + "/shop/transaction/batch")
                        .content(mapper.writeValueAsString(transactions))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // Update a list of transactions including one null transaction test
    @Test
    @Order(9)
    public void testUpdateTransactionBatch_NullTransactionId() throws Exception {
        ShopUpdateTransactionDto transactionDto1 = ShopUpdateTransactionDto.builder()
                .amount(BigDecimal.valueOf(220)).build(); // invalid transaction
        ShopUpdateTransactionDto transactionDto2 = ShopUpdateTransactionDto.builder()
                .transactionId(1L)
                .amount(BigDecimal.valueOf(160)).build();
        List<ShopUpdateTransactionDto> transactions = new ArrayList<>();
        transactions.add(transactionDto1);
        transactions.add(transactionDto2);

        mvc.perform(MockMvcRequestBuilders
                        .patch(urlPrefix + "/shop/transaction/batch")
                        .content(mapper.writeValueAsString(transactions))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    // Update a list of transactions including one invalid transactionId test
    @Test
    @Order(9)
    public void testUpdateTransactionBatch_InvalidTransactionId() throws Exception {
        ShopUpdateTransactionDto transactionDto1 = ShopUpdateTransactionDto.builder()
                .transactionId(100L)
                .amount(BigDecimal.valueOf(220)).build(); // invalid transaction
        ShopUpdateTransactionDto transactionDto2 = ShopUpdateTransactionDto.builder()
                .transactionId(1L)
                .amount(BigDecimal.valueOf(160)).build();
        List<ShopUpdateTransactionDto> transactions = new ArrayList<>();
        transactions.add(transactionDto1);
        transactions.add(transactionDto2);

        mvc.perform(MockMvcRequestBuilders
                        .patch(urlPrefix + "/shop/transaction/batch")
                        .content(mapper.writeValueAsString(transactions))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    // Update an empty list of transactions test
    @Test
    @Order(10)
    public void testUpdateTransactionBatch_EmptyTransactions() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post(urlPrefix + "/shop/transaction/batch")
                        .content(mapper.writeValueAsString(new ArrayList<>()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    // Get reward points test (customerId = 1)
    @Test
    @Order(11)
    public void testGetRewardPoints() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get(urlPrefix + "/shop/reward/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // Get reward points test (customerId = 1)
    @Test
    @Order(12)
    public void testGetRewardPointsSummary() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get(urlPrefix + "/shop/reward/summary")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


}
