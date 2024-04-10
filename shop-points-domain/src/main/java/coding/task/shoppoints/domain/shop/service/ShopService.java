package coding.task.shoppoints.domain.shop.service;

import coding.task.shoppoints.domain.shop.model.entity.ShopTransactionEntity;
import coding.task.shoppoints.domain.shop.repository.IShopRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: providing concrete implementation of shopping service
 * @author: Ryan Mei
 * @date: 4/5/24
 */
@Service
public class ShopService implements IShopService {

    @Resource
    private IShopRepository shopRepository;

    @Override
    public ShopTransactionEntity createTransaction(ShopTransactionEntity shopTransactionEntity) {
        return shopRepository.createTransaction(shopTransactionEntity);
    }

    @Override
    public ShopTransactionEntity updateTransaction(ShopTransactionEntity shopTransactionEntity) {
        return shopRepository.updateTransaction(shopTransactionEntity);
    }

    @Override
    @Transactional
    public List<ShopTransactionEntity> createTransactions(List<ShopTransactionEntity> transactionEntities) {
        return shopRepository.createTransactions(transactionEntities);
    }

    @Override
    public List<ShopTransactionEntity> updateTransactions(List<ShopTransactionEntity> transactionEntities) {
        return shopRepository.updateTransactions(transactionEntities);
    }

    @Override
    public List<Long> getAllUniqueCustomerIds() {
        return shopRepository.getAllUniqueCustomerIds();
    }

}
