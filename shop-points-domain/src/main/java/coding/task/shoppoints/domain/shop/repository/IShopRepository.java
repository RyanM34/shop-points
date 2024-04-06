package coding.task.shoppoints.domain.shop.repository;

import coding.task.shoppoints.domain.shop.model.entity.ShopTransactionEntity;

import java.util.List;

/**
 * @description: interface for manipulating user shopping activity data
 * @author: Ryan Mei
 * @date: 4/5/24
 */
public interface IShopRepository {

    /**
     * create one transaction for a customer shopping
     * @param shopTransactionEntity transaction info
     * @return persisted translation
     */
    ShopTransactionEntity createTransaction(ShopTransactionEntity shopTransactionEntity);

    /**
     * update one transaction related to a customer
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
}
