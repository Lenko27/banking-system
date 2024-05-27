package lenko27.entities.banks;

import lenko27.entities.accounts.Account;
import lenko27.entities.accounts.CreditAccount;
import lenko27.entities.accounts.DebitAccount;
import lenko27.entities.accounts.DepositAccount;
import lenko27.entities.banks.banking.tools.interfaces.BankingOperations;
import lenko27.entities.banks.banking.tools.interfaces.BankingTools;
import lenko27.entities.banks.banking.tools.interfaces.SubscribingOperations;
import lenko27.entities.client.Client;
import lenko27.service.ComplexInterestRate;
import lenko27.exceptions.UnauthorizedTransactionException;
import lenko27.exceptions.UnknownAccountTypeException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * This class represents a bank in the banking system.
 * It implements the BankingOperations, BankingTools, and SubscribingOperations interfaces.
 * Pattern Observer realized for work with subscribers
 */
@Getter
public class Bank implements BankingOperations, BankingTools, SubscribingOperations {

    /**
     * Unique identifier for the bank.
     */
    private final UUID id;

    /**
     * List of accounts associated with the bank.
     */
    private final List<Account> accountList = new ArrayList<>();

    /**
     * List of clients who have subscribed to the bank.
     */
    private final List<Client> subscribedCustomerList = new ArrayList<>();

    /**
     * Debit interest rate of the bank.
     */
    @Setter private double debitInterest;

    /**
     * Credit limit of the bank.
     */
    @Setter private double creditLimit;

    /**
     * Credit commission of the bank.
     */
    @Setter private double creditCommission;

    /**
     * Deposit interest rates of the bank.
     */
    @Setter private List<ComplexInterestRate> depositInterest;

    /**
     * Term of the bank.
     */
    @Setter private int term;

    /**
     * Constructor for the Bank class.
     *
     * @param interest The debit interest rate of the bank.
     * @param depositInterest The deposit interest rates of the bank.
     * @param creditLimit The credit limit of the bank.
     * @param creditCommission The credit commission of the bank.
     * @param id The unique identifier for the bank.
     * @param term The term of the bank.
     */
    protected Bank(double interest, List<ComplexInterestRate> depositInterest,
                   double creditLimit, double creditCommission, UUID id, int term){
        this.debitInterest = interest;
        this.depositInterest = depositInterest;
        this.creditLimit = creditLimit;
        this.creditCommission = creditCommission;
        this.id = id;
        this.term = term;
    }

    /**
     * Changes the credit commission of the bank and notifies the subscribers.
     *
     * @param newCreditCommission The new credit commission.
     */
    @Override
    public void changeCreditCommission(double newCreditCommission){
        setCreditCommission(newCreditCommission);
        notifySubscribers("Dear customer, credit commission has been changed. " +
                "New credit commission: " + newCreditCommission);
    }

    /**
     * Changes the credit limit of the bank and notifies the subscribers.
     *
     * @param newCreditLimit The new credit limit.
     */
    @Override
    public void changeCreditLimit(double newCreditLimit){
        setCreditLimit(newCreditLimit);
        notifySubscribers("Dear customer, credit limit has been changed. " +
                "New credit limit: " + newCreditLimit);
    }

    /**
     * Changes the debit interest of the bank and notifies the subscribers.
     *
     * @param newDebitInterest The new debit interest.
     */
    @Override
    public void changeDebitInterest(double newDebitInterest){
        setDebitInterest(newDebitInterest);
        notifySubscribers("Dear customer, debit interest has been changed. New debit interest: " + newDebitInterest);
    }

    /**
     * Changes the deposit interest of the bank and notifies the subscribers.
     *
     * @param newDepositInterest The new deposit interest.
     */
    @Override
    public void changeDepositInterest(List<ComplexInterestRate> newDepositInterest){
        setDepositInterest(newDepositInterest);
        notifySubscribers("Dear customer, deposit interest has been changed. New debit interest:\n" + formatDepositInterest(newDepositInterest));
    }

    /**
     * Changes the term of the bank and notifies the subscribers.
     *
     * @param newTerm The new term.
     */
    @Override
    public void changeTerm(int newTerm){
        setTerm(newTerm);
        notifySubscribers("Dear customer, deposit term has been changed. " +
                "New deposit term: " + newTerm);
    }

    /**
     * Accrues interest on all accounts in the bank.
     *
     * @throws UnauthorizedTransactionException If the transaction is not authorized.
     */
    @Override
    public void interestAccrual() throws UnauthorizedTransactionException {
        for(Account account : accountList) {
            account.refill(account.getBalance() * debitInterest, true);
        }
    }

    /**
     * Creates a new account in the bank.
     *
     * @param account The type of account to be created.
     * @param client The client who will own the account.
     * @return The newly created account.
     * @throws UnknownAccountTypeException If the account type is unknown.
     */
    @Override
    public Account createAccount(Account account, Client client) throws UnknownAccountTypeException {
        accountList.add(account);
        if (account instanceof CreditAccount){
            return new CreditAccount(UUID.randomUUID(), this, client);
        }
        if (account instanceof DebitAccount){
            return new DebitAccount(UUID.randomUUID(), this, client);
        }
        if (account instanceof DepositAccount){
            return new DepositAccount(UUID.randomUUID(), this, client);
        }
        throw new UnknownAccountTypeException();
    }

    /**
     * Formats the deposit interest rates of the bank.
     *
     * @param depositInterest The deposit interest rates.
     * @return The formatted deposit interest rates.
     */
    @Override
    public String formatDepositInterest(List<ComplexInterestRate> depositInterest) {
        StringBuilder formattedInterest = new StringBuilder();
        for (ComplexInterestRate rate : depositInterest) {
            formattedInterest.append("From ").append(rate.balanceThreshold()).append(":").append(rate.interestRate()).append("\n");
        }
        return formattedInterest.toString();
    }

    /**
     * Subscribes a client to the bank.
     *
     * @param client The client to be subscribed.
     */
    @Override
    public void subscribe(Client client){
        getSubscribedCustomerList().add(client);
    }

    /**
     * Unsubscribes a client from the bank.
     *
     * @param client The client to be unsubscribed.
     */
    @Override
    public void unsubscribe(Client client){
        getSubscribedCustomerList().remove(client);
    }

    /**
     * Notifies all subscribers of the bank with a message.
     *
     * @param message The message to be sent to the subscribers.
     */
    @Override
    public void notifySubscribers(String message){
        for (Client client : subscribedCustomerList) {
            client.update(message);
        }
    }
}
