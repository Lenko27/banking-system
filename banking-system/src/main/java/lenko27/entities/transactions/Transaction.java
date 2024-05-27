package lenko27.entities.transactions;
import lombok.Getter;
/**
 * This class represents a transaction in the banking system.
 */
@Getter
public class Transaction {

    /**
     * The type of the transaction.
     */
    private final TransactionType transactionType;

    /**
     * The sum involved in the transaction.
     */
    private final double sum;

    /**
     * Constructor for the Transaction class.
     *
     * @param transactionType The type of the transaction.
     * @param sum The sum involved in the transaction.
     */
    public Transaction(TransactionType transactionType, double sum){
        this.transactionType = transactionType;
        this.sum = sum;
    }

    /**
     * Method to check if two Transaction objects are equal.
     *
     * @param o The object to be compared with.
     * @return true if the two Transaction objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o){
        if (o instanceof Transaction){
            return this.transactionType == ((Transaction) o).transactionType && this.sum == ((Transaction) o).sum;
        }
        return false;
    }
}