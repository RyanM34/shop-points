package coding.task.shoppoints.infrastructure.repository;

import coding.task.shoppoints.common.exceptions.TransactionNotFoundException;
import coding.task.shoppoints.domain.shop.model.entity.ShopTransactionEntity;
import coding.task.shoppoints.domain.shop.repository.IShopRepository;
import coding.task.shoppoints.infrastructure.dao.TransactionDao;
import coding.task.shoppoints.infrastructure.po.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: implementation of shop domain repository
 * @author: Ryan Mei
 * @date: 4/5/24
 */
@Repository
@Slf4j
public class ShopRepository implements IShopRepository {

    @Resource
    private TransactionDao transactionDao;

    @Override
    public ShopTransactionEntity createTransaction(ShopTransactionEntity shopTransactionEntity) {
        log.info("Repository begins to create transaction: {}", shopTransactionEntity);
        // save a single transaction
        Transaction save = transactionDao.save(Transaction.builder()
                .customerId(shopTransactionEntity.getCustomerId())
                .issuedAt(shopTransactionEntity.getIssuedAt())
                .amount(shopTransactionEntity.getAmount())
                .build());
        // construct return entity
        return ShopTransactionEntity.builder()
                .customerId(save.getCustomerId())
                .transactionId(save.getTransactionId())
                .amount(save.getAmount())
                .issuedAt(save.getIssuedAt())
                .build();
    }

    @Override
    public ShopTransactionEntity updateTransaction(ShopTransactionEntity shopTransactionEntity) {
        log.info("Repository begins to update transaction: {}", shopTransactionEntity);
        Transaction transaction = transactionDao.findById(shopTransactionEntity.getTransactionId())
                // if transaction exists, update and save
                .map(existingTransaction -> {
                    existingTransaction.setAmount(shopTransactionEntity.getAmount());
                    return transactionDao.save(existingTransaction);
                })
                // transaction does not exist, throw an error
                .orElseThrow(() -> new TransactionNotFoundException(shopTransactionEntity.getTransactionId().toString()));
        // construct return entity
        return ShopTransactionEntity.builder()
                .customerId(transaction.getCustomerId())
                .transactionId(transaction.getTransactionId())
                .amount(transaction.getAmount())
                .issuedAt(transaction.getIssuedAt())
                .build();
    }

    @Override
    public List<ShopTransactionEntity> createTransactions(List<ShopTransactionEntity> transactionEntities) {
        log.info("Repository begins to create transactions: {}", transactionEntities);
        // save list of transactions
        List<Transaction> transactions = transactionDao.saveAll(
                transactionEntities.stream().map(
                                entity -> Transaction.builder()
                                        .customerId(entity.getCustomerId())
                                        .amount(entity.getAmount())
                                        .issuedAt(entity.getIssuedAt())
                                        .build())
                        .collect(Collectors.toList()));
        // construct return entities
        return transactions.stream().map(
                        transaction -> ShopTransactionEntity.builder()
                                .customerId(transaction.getCustomerId())
                                .transactionId(transaction.getTransactionId())
                                .amount(transaction.getAmount())
                                .issuedAt(transaction.getIssuedAt())
                                .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<ShopTransactionEntity> updateTransactions(List<ShopTransactionEntity> transactionEntities) {
        log.info("Repository begins to update transactions: {}", transactionEntities);
        // extract transaction ids
        List<Long> transactionIds = transactionEntities.stream().map(ShopTransactionEntity::getTransactionId)
                .collect(Collectors.toList());

        // get all existing transactions
        Map<Long, Transaction> existingTransactionsMap = transactionDao.findAllById(transactionIds).stream()
                .collect(Collectors.toMap(Transaction::getTransactionId, transaction -> transaction));

        // update existing transactions with new data
        List<Transaction> updatedTransactions = transactionEntities.stream()
                .map(entity -> {
                    Transaction existingTransaction = existingTransactionsMap.get(entity.getTransactionId());
                    if (existingTransaction != null) {
                        // update the existing transaction
                        existingTransaction.setAmount(entity.getAmount());
                        return existingTransaction;
                    } else {
                        // handle case where the provided transaction ID does not exist
                        throw new TransactionNotFoundException(entity.getTransactionId().toString());
                    }
                })
                .collect(Collectors.toList());

        // save the updated transactions
        List<Transaction> savedTransactions = transactionDao.saveAll(updatedTransactions);

        // construct return entities
        return savedTransactions.stream()
                .map(transaction -> ShopTransactionEntity.builder()
                        .customerId(transaction.getCustomerId())
                        .transactionId(transaction.getTransactionId())
                        .amount(transaction.getAmount())
                        .issuedAt(transaction.getIssuedAt())
                        .build())
                .collect(Collectors.toList());
    }

}
