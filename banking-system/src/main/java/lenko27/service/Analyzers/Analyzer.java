package lenko27.service.Analyzers;

/**
 * This interface represents an analyzer for clients'
 * money in the system.
 */
public interface Analyzer {
    /**
     * Method to perform a calculation and return the result.
     *
     * @return The result of the calculation as a double.
     */
    double calculate();
}
