package lenko27.service;

import lombok.Getter;

/**
 * This record represents a complex interest rate of deposit accounts in the banking system.
 *
 * @param balanceThreshold The balance threshold for applying this interest rate.
 * @param interestRate     The interest rate that applies when the balance is above the threshold.
 */
public record ComplexInterestRate(double balanceThreshold, double interestRate) {
    public ComplexInterestRate { }
}

