package lenko27.exceptions;

import lenko27.entities.client.Client;

/**
 * This class represents an exception that is thrown when a withdrawal is attempted before the term.
 * It extends the BankingOperationsException class, adding several constructors for different use cases.
 */
public class WithdrawalBeforeTermException extends BankingOperationsException {

    /**
     * Default constructor for the WithdrawalBeforeTermException class.
     * Creates a new exception with a default message.
     */
    public WithdrawalBeforeTermException() {
        super("Withdrawal before term is not allowed.");
    }

    /**
     * Constructor for the WithdrawalBeforeTermException class.
     * Creates a new exception with a specific message about the client.
     *
     * @param client The client who is not allowed to withdraw before the term.
     */
    public WithdrawalBeforeTermException(Client client) {
        super("Client " + client.getName() + " " + client.getSurname() + ": Withdrawal before term is not allowed.");
    }

    /**
     * Constructor for the WithdrawalBeforeTermException class.
     * Creates a new exception with a specific message.
     *
     * @param message The specific message for this exception.
     */
    public WithdrawalBeforeTermException(String message) {
        super(message);
    }
}

