package lenko27.Transactions;

import lenko27.entities.accounts.Account;
import lenko27.entities.accounts.CreditAccount;
import lenko27.entities.accounts.DebitAccount;
import lenko27.entities.accounts.DepositAccount;
import lenko27.service.Analyzers.Analyzer;
import lenko27.service.Analyzers.CreditAnalyzer;
import lenko27.service.Analyzers.DebitAnalyzer;
import lenko27.service.Analyzers.DepositAnalyzer;
import lenko27.service.ComplexInterestRate;
import lenko27.entities.banks.Bank;
import lenko27.entities.banks.CentralBank;
import lenko27.entities.client.Client;
import lenko27.entities.transactions.Transaction;
import lenko27.entities.transactions.TransactionType;
import lenko27.exceptions.InsufficientFundsException;
import lenko27.exceptions.UnauthorizedTransactionException;
import lenko27.exceptions.UnknownAccountTypeException;
import lenko27.exceptions.WithdrawalBeforeTermException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

class JUnitTests {
    CentralBank centralBank;
    Bank bank;
    Account debitAccount, debitAccount1, debitAccount2, susDebitAccount;
    Account depositAccount, creditAccount;
    Client client, susClient, subscribedClient;
    @SneakyThrows
    @BeforeEach
    public void initEntities() {
        susClient = Client.builder("Kolya", "Petrov").build();
        client = Client.builder("Kolya", "Petrov")
                .passportData("45 19 661355")
                .address("Pionerskaya")
                .build();

        List<ComplexInterestRate> depositInterest = new ArrayList<ComplexInterestRate>(
                Arrays.asList(
                        new ComplexInterestRate(0, 0.05),
                        new ComplexInterestRate(104, 0.1),
                        new ComplexInterestRate(1000, 0.2)
                )
        );
        centralBank = CentralBank.getInstance();
        bank = centralBank.createBank(0.1, depositInterest, 1000, 0.2, 2);
        debitAccount = bank.createAccount(new DebitAccount(UUID.randomUUID(), bank, client), client);
        depositAccount = bank.createAccount(new DepositAccount(UUID.randomUUID(), bank, client), client);
        creditAccount = bank.createAccount(new CreditAccount(UUID.randomUUID(), bank, client), client);
    }
    @Test
    public void tryDebitAccountGoNegative() throws UnauthorizedTransactionException {
        debitAccount.refill(200, true);

        Assertions.assertThrows(InsufficientFundsException.class, () -> debitAccount.withdraw(400, true));
    }
    @Test
    public void tryDoOperationWhileOnTerm() throws UnauthorizedTransactionException {
        depositAccount.refill(300, true);

        Assertions.assertThrows(WithdrawalBeforeTermException.class, () -> depositAccount.withdraw(200, true));
    }
    @Test
    public void tryDoOperationWithSuspiciousStatus() throws UnknownAccountTypeException,
            UnauthorizedTransactionException, WithdrawalBeforeTermException, InsufficientFundsException {
        susDebitAccount = bank.createAccount(new DebitAccount(UUID.randomUUID(), bank, susClient), susClient);
        debitAccount.refill(500, true);

        Assertions.assertThrows(UnauthorizedTransactionException.class, () -> susDebitAccount.refill(400, true));
        Assertions.assertThrows(UnauthorizedTransactionException.class, () -> susDebitAccount.withdraw(0, true));
        Assertions.assertThrows(UnauthorizedTransactionException.class, () -> centralBank.doTransfer(susDebitAccount, debitAccount, 100));
        Assertions.assertThrows(UnauthorizedTransactionException.class, () -> centralBank.doTransfer(debitAccount, susDebitAccount, 100));

        susClient.setAddress("sdf");
        susDebitAccount.refill(400, true);
        Assertions.assertEquals(400, susDebitAccount.getBalance());

        centralBank.doTransfer(susDebitAccount, debitAccount, 100);
        Assertions.assertEquals(300, susDebitAccount.getBalance());
        Assertions.assertEquals(600, debitAccount.getBalance());
    }
    @Test
    public void tryChangeConditionAndNotifySubscribers(){
        subscribedClient = Client.builder("Kolya", "Predanyy").build();
        bank.subscribe(subscribedClient);
        bank.changeCreditLimit(123);

        Assertions.assertFalse(subscribedClient.getMessages().isEmpty());
        Assertions.assertTrue(client.getMessages().isEmpty());
    }
    @Test
    public void tryRefillAndCheckTransaction() throws UnauthorizedTransactionException {
        debitAccount.refill(200, true);
        debitAccount.refill(400, true);
        debitAccount.refill(600, true);

        for(Transaction transaction : debitAccount.getTransactions()) {
            System.out.println(transaction.getTransactionType() + " " + transaction.getSum());
        }

        Assertions.assertEquals(1200, debitAccount.getBalance());
    }
    @SneakyThrows
    @Test
    public void tryWithdrawAndCheckTransaction() {
        debitAccount.refill(200, true);
        debitAccount.refill(400, true);
        debitAccount.refill(600, true);
        debitAccount.withdraw(150, true);

        for(Transaction transaction : debitAccount.getTransactions()) {
            System.out.println(transaction.getTransactionType() + " " + transaction.getSum());
        }

        Assertions.assertEquals(1050, debitAccount.getBalance());
    }
    @SneakyThrows
    @Test
    public void tryTransfer() {
        debitAccount.refill(500, true);
        creditAccount.refill(200, true);
        centralBank.doTransfer(debitAccount, creditAccount, 100);

        Assertions.assertEquals(400, debitAccount.getBalance());
        Assertions.assertEquals(300, creditAccount.getBalance());
    }
    @SneakyThrows
    @Test
    public void tryRemoveTransaction() {
        debitAccount.refill(500, true);
        debitAccount.withdraw(100, true);
        creditAccount.refill(200, true);

        debitAccount1 = bank.createAccount(new DebitAccount(UUID.randomUUID(), bank, client), client);
        debitAccount2 = bank.createAccount(new DebitAccount(UUID.randomUUID(), bank, client), client);
        debitAccount1.refill(2000, true);
        debitAccount2.refill(2001, true);
        centralBank.doTransfer(debitAccount1, debitAccount2, 300);

        Transaction firstTransaction = new Transaction(TransactionType.WITHDRAW, 100);
        Transaction secondTransaction = new Transaction(TransactionType.REFILL, 200);
        Transaction thirdTransactionSender = new Transaction(TransactionType.TRANSFERSENDER, 300);
        Transaction thirdTransactionAccepter = new Transaction(TransactionType.TRANSFERACCEPTER, 300);

        debitAccount.cancellingTransaction(debitAccount, firstTransaction);
        creditAccount.cancellingTransaction(creditAccount, secondTransaction);
        debitAccount1.cancellingTransaction(debitAccount1, thirdTransactionSender);
        debitAccount2.cancellingTransaction(debitAccount2, thirdTransactionAccepter);

        Assertions.assertEquals(500, debitAccount.getBalance());
        Assertions.assertEquals(0, creditAccount.getBalance());
        Assertions.assertEquals(2000, debitAccount1.getBalance());
        Assertions.assertEquals(2001, debitAccount2.getBalance());
    }
    @Test
    public void tryAnalyzeAccounts() throws WithdrawalBeforeTermException, InsufficientFundsException, UnauthorizedTransactionException {
        debitAccount.refill(200, true);
        depositAccount.refill(100, true);
        creditAccount.withdraw(100, true);
        Analyzer debitAnalyzer = new DebitAnalyzer(2, debitAccount.getBalance(), bank.getDebitInterest());
        Analyzer depositAnalyzer = new DepositAnalyzer(3, depositAccount.getBalance(), bank.getDepositInterest(),1);
        Analyzer creditAnalyzer = new CreditAnalyzer(2, creditAccount.getBalance(), bank.getCreditLimit(),
                bank.getCreditCommission());

        Assertions.assertEquals(242, debitAnalyzer.calculate());
        Assertions.assertEquals(115.5, depositAnalyzer.calculate());
        Assertions.assertEquals(-144, creditAnalyzer.calculate());
    }
}