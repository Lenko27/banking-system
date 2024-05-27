package lenko27.service.Analyzers;

import lenko27.service.ComplexInterestRate;

import java.util.List;/**
 * This class represents a deposit analyzer in the banking system.
 * It implements the Analyzer interface.
 */
public class DepositAnalyzer implements Analyzer{
    /**
     * The time period for the analysis.
     */
    private final int time;

    /**
     * The term of the deposit.
     */
    private int term;

    /**
     * The balance of the account.
     */
    private double balance;

    /**
     * The list of complex interest rates of the account.
     */
    private final List<ComplexInterestRate> depositInterest;

    /**
     * Constructor for the DepositAnalyzer class.
     *
     * @param time The time period for the analysis.
     * @param balance The balance of the account.
     * @param depositInterest The list of complex interest rates of the account.
     * @param term The term of the deposit.
     */
    public DepositAnalyzer(int time, double balance, List<ComplexInterestRate> depositInterest, int term){
        this.time = time;
        this.balance = balance;
        this.depositInterest = depositInterest;
        this.term = term;
    }

    /**
     * Method to calculate the new balance after applying the interest for the given time period.
     * The interest rate applied is the one whose balance threshold is less than or equal to the current balance.
     * The interest is not applied if the term is not yet over.
     *
     * @return The new balance of the account.
     */
    @Override
    public double calculate() {
        for (int i = 0; i < time; ++i) {
            if (term > 0) {
                term--;
                continue;
            }
            for (int j = depositInterest.size() - 1; j >= 0; --j) {
                ComplexInterestRate rate = depositInterest.get(j);
                if (balance >= rate.balanceThreshold()) {
                    balance += balance * rate.interestRate();
                    break;
                }
            }
        }
        return balance;
    }
}
