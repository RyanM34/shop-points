package coding.task.shoppoints.common.exceptions;

/**
 * @description: Negative Transaction Amount Exception
 * @author: Ryan Mei
 * @date: 4/7/24
 */
public class NegativeTransactionAmountException extends RuntimeException {

    public NegativeTransactionAmountException(String transaction) {
        super("Transaction: [" + transaction + "] amount is negative!");
    }
}
