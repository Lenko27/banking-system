package lenko27.entities.accounts;

import lenko27.entities.transactions.Transaction;
import lenko27.entities.transactions.TransactionType;
import lenko27.entities.banks.Bank;
import lenko27.entities.client.Client;
import lenko27.exceptions.InsufficientFundsException;
import lenko27.exceptions.UnauthorizedTransactionException;
import lenko27.exceptions.WithdrawalBeforeTermException;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * This class represents a deposit account in the banking system.
 * It extends the abstract Account class and provides implementation for its abstract methods.
 */
@Getter
public class DepositAccount extends Account {

    /**
     * Unique identifier for the account.
     */
    private final UUID id;

    /**
     * List of transactions associated with the account.
     */
    private final List<Transaction> transactions = new ArrayList<>();

    /**
     * Current balance of the account.
     */
    private double balance;

    /**
     * Bank associated with the account.
     */
    private final Bank bank;

    /**
     * Client who owns the account.
     */
    private final Client client;

    /**
     * Constructor for the DepositAccount class.
     *
     * @param id     Unique identifier for the account.
     * @param bank   Bank associated with the account.
     * @param client Client who owns the account.
     */
    public DepositAccount(UUID id, Bank bank, Client client) {
        this.id = id;
        this.bank = bank;
        this.client = client;
    }

    /**
     * Method to refill the account.
     *
     * @param sum   Amount to be refilled.
     * @param toLog Whether to log this operation or not.
     * @throws UnauthorizedTransactionException If the client is suspicious.
     */
    @Override
    public void refill(double sum, boolean toLog) throws UnauthorizedTransactionException {
        if (client.isSuspicious()) {
            throw new UnauthorizedTransactionException();
        }
        if (toLog){
            operationLogging(TransactionType.REFILL, sum);
        }
        balance += sum;
    };
    /**
     * Method to withdraw from the account.
     *
     * @param sum   Amount to be withdrawn.
     * @param toLog Whether to log this operation or not.
     * @throws InsufficientFundsException       If there are insufficient funds.
     * @throws UnauthorizedTransactionException If the client is suspicious.
     * @throws WithdrawalBeforeTermException    If the withdrawal is before the term.
     * @return true if the withdrawal was successful, false otherwise.
     */
    @Override
    public boolean withdraw(double sum, boolean toLog) throws InsufficientFundsException,
            UnauthorizedTransactionException, WithdrawalBeforeTermException {
        if (balance < sum){
            throw new InsufficientFundsException();
        }
        if (client.isSuspicious()){
            throw new UnauthorizedTransactionException();
        }
        if (bank.getTerm()!= 0){
            throw new WithdrawalBeforeTermException();
        }
        if (toLog){
            operationLogging(TransactionType.WITHDRAW, sum);
        }
        balance -= sum;
        return true;
    }
    /**
     * Method to log operations.
     *
     * @param type Transaction type.
     * @param sum  Amount involved in the transaction.
     */
    @Override
    public void operationLogging(TransactionType type, double sum) {
        Transaction transaction = new Transaction(type, sum);
        getTransactions().add(transaction);
    }
}
