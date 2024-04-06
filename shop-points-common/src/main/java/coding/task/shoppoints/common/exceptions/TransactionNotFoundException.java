package coding.task.shoppoints.common.exceptions;

/**
 * @description: TransactionNotFoundException
 * @author: Ryan Mei
 * @date: 4/5/24
 */
public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(String transactionId) {
        super("Transaction: [" + transactionId + "] not found!");
    }
}
