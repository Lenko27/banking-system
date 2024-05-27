package lenko27.entities.banks;

import lenko27.entities.accounts.Account;
import lenko27.entities.banks.banking.tools.interfaces.BankingOperations;
import lenko27.entities.transactions.TransactionType;
import lenko27.service.ComplexInterestRate;
import lenko27.exceptions.InsufficientFundsException;
import lenko27.exceptions.UnauthorizedTransactionException;
import lenko27.exceptions.WithdrawalBeforeTermException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * This class represents a central bank in the banking system.
 * It follows the Singleton design pattern to ensure only one instance of CentralBank exists.
 */
@Getter
public class CentralBank {

    /**
     * The single instance of the CentralBank class.
     */
    private static CentralBank instance;

    /**
     * List of banks managed by the central bank.
     */
    private final List<Bank> bankList;

    /**
     * Private constructor for the CentralBank class.
     * Initializes the list of banks.
     */
    private CentralBank() {
        this.bankList = new ArrayList<>();
    }

    /**
     * Method to get the single instance of the CentralBank class.
     * If the instance does not exist, it is created.
     *
     * @return The single instance of the CentralBank class.
     */
    public static CentralBank getInstance() {
        if (instance == null) {
            instance = new CentralBank();
        }
        return instance;
    }

    /**
     * Method to create a new bank and add it to the list of banks.
     *
     * @param interest The debit interest rate of the bank.
     * @param depositInterest The deposit interest rates of the bank.
     * @param creditLimit The credit limit of the bank.
     * @param creditCommission The credit commission of the bank.
     * @param term The term of the bank.
     * @return The newly created bank.
     */
    public Bank createBank(double interest, List<ComplexInterestRate> depositInterest,
                           double creditLimit, double creditCommission, int term) {
        Bank bank = new Bank(interest, depositInterest, creditLimit, creditCommission, UUID.randomUUID(), term);
        bankList.add(bank);
        return bank;
    }

    /**
     * Method to transfer a sum of money from a sender account to an accepter account.
     *
     * @param sender The account from which the sum is withdrawn.
     * @param accepter The account to which the sum is deposited.
     * @param sum The sum of money to be transferred.
     * @throws UnauthorizedTransactionException If the client is suspicious.
     * @throws WithdrawalBeforeTermException If the withdrawal is before the term.
     * @throws InsufficientFundsException If there are insufficient funds.
     */
    public void doTransfer(Account sender, Account accepter, double sum) throws UnauthorizedTransactionException, WithdrawalBeforeTermException, InsufficientFundsException {
        if (sender.getClient().isSuspicious()) {
            throw new UnauthorizedTransactionException(sender.getClient());
        }
        if (accepter.getClient().isSuspicious()) {
            throw new UnauthorizedTransactionException(accepter.getClient());
        }
        if (sender.withdraw(sum, false)) {
            accepter.refill(sum, false);
            sender.operationLogging(TransactionType.TRANSFERSENDER, sum);
            accepter.operationLogging(TransactionType.TRANSFERACCEPTER, sum);
        }
    }

    /**
     * Method to accrue interest on all accounts in all banks.
     *
     * @throws UnauthorizedTransactionException If the transaction is not authorized.
     */
    public void doAccrual() throws UnauthorizedTransactionException {
        for (BankingOperations bank : bankList) {
            bank.interestAccrual();
        }
    }
}
