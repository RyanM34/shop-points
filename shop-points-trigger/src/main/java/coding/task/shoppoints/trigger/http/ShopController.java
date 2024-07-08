package coding.task.shoppoints.trigger.http;

import coding.task.shoppoints.common.enums.RewardCalculationType;
import coding.task.shoppoints.domain.reward.model.entity.RewardPointsEntity;
import coding.task.shoppoints.domain.reward.service.IRewardService;
import coding.task.shoppoints.domain.shop.model.entity.ShopTransactionEntity;
import coding.task.shoppoints.domain.shop.service.IShopService;
import coding.task.shoppoints.trigger.dto.ShopCreateTransactionDto;
import coding.task.shoppoints.trigger.dto.ShopUpdateTransactionDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: shopping service apis endpoint
 * @author: Ryan Mei
 * @date: 4/5/24
 */
@RestController
@RequestMapping("/${app.config.api-version}/shop")
@Slf4j
@Validated
@Api(tags = "ShopController", description = "ShopController API")
public class ShopController {
    private String ryanTest2;

    @Resource
    private IShopService shopService;

    @Resource
    private IRewardService rewardService;


    @PostMapping("transaction")
    @ApiOperation("Create an transaction")
    public ResponseEntity createTransaction(@RequestBody @Valid ShopCreateTransactionDto transactionDto) {
        // build query params
        ShopTransactionEntity shopTransactionEntity = ShopTransactionEntity.builder()
                .customerId(transactionDto.getCustomerId())
                .amount(transactionDto.getAmount())
                .issuedAt(transactionDto.getIssuedAt())
                .build();
        ShopTransactionEntity transaction = shopService.createTransaction(shopTransactionEntity);
        return ResponseEntity.ok(transaction);
    }

    @PatchMapping("transaction")
    public ResponseEntity updateTransaction(@RequestBody @Valid ShopUpdateTransactionDto transactionDto) {
        // build query params
        ShopTransactionEntity shopTransactionEntity = ShopTransactionEntity.builder()
                .amount(transactionDto.getAmount())
                .transactionId(transactionDto.getTransactionId())
                .build();
        ShopTransactionEntity transaction = shopService.updateTransaction(shopTransactionEntity);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("transaction/batch")
    public ResponseEntity createTransactions(@RequestBody
                                             @NotEmpty(message = "transactions can't be empty")
                                             List<@Valid ShopCreateTransactionDto> transactionDtos) {
        // build query params
        List<ShopTransactionEntity> transactionEntities = transactionDtos.stream().map(
                        transaction -> ShopTransactionEntity.builder()
                                .customerId(transaction.getCustomerId())
                                .issuedAt(transaction.getIssuedAt())
                                .amount(transaction.getAmount())
                                .build())
                .collect(Collectors.toList());
        List<ShopTransactionEntity> transactions = shopService.createTransactions(transactionEntities);
        return ResponseEntity.ok(transactions);
    }

    @PatchMapping("transaction/batch")
    public ResponseEntity updateTransactions(@RequestBody
                                             @NotEmpty(message = "transactions can't be empty")
                                             List<@Valid ShopUpdateTransactionDto> transactionDtos) {
        // build query params
        List<ShopTransactionEntity> transactionEntities = transactionDtos.stream().map(
                        transaction -> ShopTransactionEntity.builder()
                                .transactionId(transaction.getTransactionId())
                                .amount(transaction.getAmount())
                                .build())
                .collect(Collectors.toList());
        List<ShopTransactionEntity> transactions = shopService.updateTransactions(transactionEntities);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("reward/{customerId}")
    public ResponseEntity getRewardPoints(@PathVariable("customerId")
                                          @NotNull(message = "customerId can't be null")
                                          Long customerId) {
        RewardPointsEntity rewardPoints = rewardService.getRewardPoints(customerId, RewardCalculationType.BASE_STRATEGY);
        return ResponseEntity.ok(rewardPoints);
    }

    @GetMapping("reward/summary")
    public ResponseEntity getRewardPointsSummary() {
        List<Long> customerIds = shopService.getAllUniqueCustomerIds();
        List<RewardPointsEntity> summary =  rewardService.getRewardPointsSummary(customerIds, RewardCalculationType.BASE_STRATEGY);
        return ResponseEntity.ok(summary);
    }

}

