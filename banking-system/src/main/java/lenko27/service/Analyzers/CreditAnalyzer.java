package lenko27.service.Analyzers;

import lombok.Getter;

import static java.lang.Math.abs;

/**
 * This class represents a credit analyzer in the banking system.
 * It implements the Analyzer interface.
 */
@Getter
public class CreditAnalyzer implements Analyzer{

    /**
     * The time period for the analysis.
     */
    private final int time;

    /**
     * The balance of the account.
     */
    private double balance;

    /**
     * The limit of the account.
     */
    private final double limit;

    /**
     * The commission of the account.
     */
    private final double commission;

    /**
     * A flag indicating whether the account's balance is over the limit.
     */
    private boolean isOverLimit = false;

    /**
     * Constructor for the CreditAnalyzer class.
     *
     * @param time The time period for the analysis.
     * @param balance The balance of the account.
     * @param limit The limit of the account.
     * @param commission The commission of the account.
     */
    public CreditAnalyzer(int time, double balance, double limit, double commission){
        this.time = time;
        this.balance = balance;
        this.limit = limit;
        this.commission = commission;
    }

    /**
     * Method to calculate the new balance after applying the commission for the given time period.
     * If the balance is negative, the commission is applied for each time period
     * until the balance is no longer negative or the account is over the limit.
     *
     * @return The new balance of the account.
     */
    @Override
    public double calculate() {
        if (balance < 0) {
            for (int i = 0; i < time; ++i) {
                balance -= abs(balance * commission);
                if (balance < limit) {
                    isOverLimit = true;
                }
            }
        }
        return balance;
    }
}
