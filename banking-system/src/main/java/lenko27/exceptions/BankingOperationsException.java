package lenko27.exceptions;

/**
 * This class represents an exception that can occur during banking operations.
 * It extends the Exception class, adding several constructors for different use cases.
 */
public class BankingOperationsException extends Exception {

    /**
     * Default constructor for the BankingOperationsException class.
     * Creates a new exception with a default message.
     */
    public BankingOperationsException() {
        super("Exception occurred during banking operation.");
    }

    /**
     * Constructor for the BankingOperationsException class.
     * Creates a new exception with a specific message.
     *
     * @param message The specific message for this exception.
     */
    public BankingOperationsException(String message) {
        super(message);
    }

    /**
     * Constructor for the BankingOperationsException class.
     * Creates a new exception with a specific message and cause.
     *
     * @param message The specific message for this exception.
     * @param cause The cause of this exception.
     */
    public BankingOperationsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for the BankingOperationsException class.
     * Creates a new exception with a specific cause.
     *
     * @param cause The cause of this exception.
     */
    public BankingOperationsException(Throwable cause) {
        super(cause);
    }
}


