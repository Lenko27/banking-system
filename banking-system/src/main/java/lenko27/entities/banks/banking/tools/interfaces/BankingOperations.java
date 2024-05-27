package lenko27.entities.banks.banking.tools.interfaces;

import lenko27.entities.accounts.Account;
import lenko27.service.ComplexInterestRate;
import lenko27.entities.client.Client;
import lenko27.exceptions.UnauthorizedTransactionException;
import lenko27.exceptions.UnknownAccountTypeException;

import java.util.List;

/**
 * This interface represents the banking operations that can be performed.
 */
public interface BankingOperations {

    /**
     * Method to accrue interest on the bank's accounts.
     * @throws UnauthorizedTransactionException If the transaction is not authorized.
     */
    void interestAccrual() throws UnauthorizedTransactionException;

    /**
     * Method to get the term of an account.
     * @return The term of the account.
     */
    int getTerm();

    /**
     * Method to get the credit limit of an account.
     * @return The credit limit of the account.
     */
    double getCreditLimit();

    /**
     * Method to create a new account.
     * @param account The type of account to be created.
     * @param client The client who will own the account.
     * @return The newly created account.
     * @throws UnknownAccountTypeException If the account type is unknown.
     */
    Account createAccount(Account account, Client client) throws UnknownAccountTypeException;

    /**
     * Method to format the deposit interest.
     * @param depositInterest The list of complex interest rates.
     * @return The formatted deposit interest.
     */
    String formatDepositInterest(List<ComplexInterestRate> depositInterest);
}

