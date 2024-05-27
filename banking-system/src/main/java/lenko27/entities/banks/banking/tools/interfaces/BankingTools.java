package lenko27.entities.banks.banking.tools.interfaces;

import lenko27.service.ComplexInterestRate;

import java.util.List;
/**
 * This interface represents the banking tools that can be used to modify various parameters of a bank account.
 */
public interface BankingTools {

    /**
     * Method to change the credit commission of accounts.
     *
     * @param newCreditCommission The new credit commission to be set.
     */
    void changeCreditCommission(double newCreditCommission);

    /**
     * Method to change the credit limit of the accounts.
     *
     * @param newCreditLimit The new credit limit to be set.
     */
    void changeCreditLimit(double newCreditLimit);

    /**
     * Method to change the debit interest of the accounts.
     *
     * @param newDebitInterest The new debit interest to be set.
     */
    void changeDebitInterest(double newDebitInterest);

    /**
     * Method to change the deposit interest of the accounts.
     *
     * @param newDepositInterest The new list of complex interest rates to be set.
     */
    void changeDepositInterest(List<ComplexInterestRate> newDepositInterest);

    /**
     * Method to change the term of the accounts.
     *
     * @param newTerm The new term to be set.
     */
    void changeTerm(int newTerm);
}
