package lenko27.entities.accounts;

import lenko27.entities.client.Client;
import lenko27.exceptions.InsufficientFundsException;
import lenko27.exceptions.UnauthorizedTransactionException;
import lenko27.exceptions.WithdrawalBeforeTermException;
import lenko27.entities.transactions.Transaction;
import lenko27.entities.transactions.TransactionType;
import lombok.SneakyThrows;

import java.util.List;

/**
 * This is the abstract Account class for the banking accounts.
 * It provides the structure for different types of accounts.
 */
public abstract class Account {

    /**
     * Get the balance of the account.
     * @return the balance of the account.
     */
    abstract public double getBalance();

    /**
     * Get the list of transactions associated with the account.
     * @return the list of transactions.
     */
    abstract public List<Transaction> getTransactions();

    /**
     * Get the client associated with the account.
     * @return the client.
     */
    abstract public Client getClient();

    /**
     * Log the operation.
     * @param type the type of transaction.
     * @param sum the amount involved in the transaction.
     */
    abstract public void operationLogging(TransactionType type, double sum);

    /**
     * Refill the account.
     * @param sum the amount to refill.
     * @param toLog whether to log this operation or not.
     * @throws UnauthorizedTransactionException if the transaction is not authorized.
     */
    abstract public void refill(double sum, boolean toLog) throws UnauthorizedTransactionException;

    /**
     * Withdraw from the account.
     * @param sum the amount to withdraw.
     * @param toLog whether to log this operation or not.
     * @throws InsufficientFundsException if there are insufficient funds.
     * @throws UnauthorizedTransactionException if the transaction is not authorized.
     * @throws WithdrawalBeforeTermException if the withdrawal is before the term.
     * @return true if the withdrawal was successful, false otherwise.
     */
    abstract public boolean withdraw(double sum, boolean toLog) throws InsufficientFundsException, UnauthorizedTransactionException, WithdrawalBeforeTermException;

    /**
     * Cancel a transaction.
     * @param account the account to cancel the transaction from.
     * @param transaction the transaction to cancel.
     */
    @SneakyThrows
    public void cancellingTransaction(Account account, Transaction transaction) {
        if (transaction.getTransactionType() == TransactionType.REFILL){
            account.withdraw(transaction.getSum(), true);
        }
        if (transaction.getTransactionType() == TransactionType.WITHDRAW){
            account.refill(transaction.getSum(), true);
        }
        if (transaction.getTransactionType() == TransactionType.TRANSFERSENDER){
            account.refill(transaction.getSum(), true);
        }
        if (transaction.getTransactionType() == TransactionType.TRANSFERACCEPTER){
            account.withdraw(transaction.getSum(), true);
        }
        account.getTransactions().remove(transaction);
    }
}
