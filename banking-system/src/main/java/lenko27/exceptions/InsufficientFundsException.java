package lenko27.exceptions;

import lenko27.entities.client.Client;

/**
 * This class represents an exception that is thrown when there are insufficient funds in an account.
 * It extends the BankingOperationsException class, adding several constructors for different use cases.
 */
public class InsufficientFundsException extends BankingOperationsException {

    /**
     * Default constructor for the InsufficientFundsException class.
     * Creates a new exception with a default message.
     */
    public InsufficientFundsException() {
        super("Insufficient funds in the account.");
    }

    /**
     * Constructor for the InsufficientFundsException class.
     * Creates a new exception with a specific message about the client.
     *
     * @param client The client who has insufficient funds.
     */
    public InsufficientFundsException(Client client) {
        super("Client " + client.getName() + " " + client.getSurname() + ": Insufficient funds in the account.");
    }

    /**
     * Constructor for the InsufficientFundsException class.
     * Creates a new exception with a specific message.
     *
     * @param message The specific message for this exception.
     */
    public InsufficientFundsException(String message) {
        super(message);
    }
}

