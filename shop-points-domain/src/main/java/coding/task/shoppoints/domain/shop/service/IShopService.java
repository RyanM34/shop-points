package coding.task.shoppoints.domain.shop.service;

import coding.task.shoppoints.domain.shop.model.entity.ShopTransactionEntity;

import java.util.List;

/**
 * @description: providing shopping service
 * @author: Ryan Mei
 * @date: 4/5/24
 */
public interface IShopService {

    /**
     * create a transaction related to a customer
     * @param shopTransactionEntity transaction info
     * @return persisted translation
     */
    ShopTransactionEntity createTransaction(ShopTransactionEntity shopTransactionEntity);

    /**
     * update a transaction related to a customer
     * @param shopTransactionEntity transaction info
     * @return updated translation
     */
    ShopTransactionEntity updateTransaction(ShopTransactionEntity shopTransactionEntity);

    /**
     * create a series of transactions
     * @param transactionEntities transactions info
     * @return persisted translations
     */
    List<ShopTransactionEntity> createTransactions(List<ShopTransactionEntity> transactionEntities);

    /**
     * update a series of transactions
     * @param transactionEntities transactions info
     * @return updated translations
     */
    List<ShopTransactionEntity> updateTransactions(List<ShopTransactionEntity> transactionEntities);

    /**
     * get all customer ids
     * @return ids
     */
    List<Long> getAllUniqueCustomerIds();
}
