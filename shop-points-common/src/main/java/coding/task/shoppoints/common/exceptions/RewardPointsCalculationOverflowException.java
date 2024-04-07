package coding.task.shoppoints.common.exceptions;

/**
 * @description: Reward Points Calculation Overflow Exception
 * @author: Ryan Mei
 * @date: 4/7/24
 */
public class RewardPointsCalculationOverflowException extends RuntimeException{
    public RewardPointsCalculationOverflowException(String transactions) {
        super("Transactions: [" + transactions + "] reward points calculation result exceeds Integer.MAX_VALUE!");
    }
}
