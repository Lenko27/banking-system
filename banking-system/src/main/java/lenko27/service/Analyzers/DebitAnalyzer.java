package lenko27.service.Analyzers;

/**
 * This class represents a debit analyzer in the banking system.
 * It implements the Analyzer interface.
 */
public class DebitAnalyzer implements Analyzer{
    /**
     * The time period for the analysis.
     */
    private final int time;

    /**
     * The balance of the account.
     */
    private double balance;
    /**
     * The interest rate of the account.
     */
    private final double interest;

    /**
     * Constructor for the CreditAnalyzer class.
     *
     * @param time The time period for the analysis.
     * @param balance The balance of the account.
     * @param interest The interest of the account.
     */
    public DebitAnalyzer(int time, double balance, double interest){
        this.time = time;
        this.balance = balance;
        this.interest =interest;
    }
    /**
     * Method to calculate the new balance with interest after some months
     *
     * @return The new balance of the account.
     */
    @Override
    public double calculate() {
        for (int i=0; i < time; ++i){
            balance += balance*interest;
        }
        return balance;
    }
}
