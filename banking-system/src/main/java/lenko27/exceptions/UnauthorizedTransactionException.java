package lenko27.exceptions;

import lenko27.entities.client.Client;
/**
 * This class represents an exception that is thrown when a transaction is unauthorized.
 * It extends the BankingOperationsException class, adding several constructors for different use cases.
 */
public class UnauthorizedTransactionException extends BankingOperationsException {

    /**
     * Default constructor for the UnauthorizedTransactionException class.
     * Creates a new exception with a default message.
     */
    public UnauthorizedTransactionException() {
        super("Unauthorized transaction. User is not allowed to perform this operation.");
    }

    /**
     * Constructor for the UnauthorizedTransactionException class.
     * Creates a new exception with a specific message about the client.
     *
     * @param client The client who is not authorized to perform the operation.
     */
    public UnauthorizedTransactionException(Client client) {
        super("Client " + client.getName() + " " + client.getSurname() + ": Unauthorized transaction. User is not allowed to perform this operation.");
    }

    /**
     * Constructor for the UnauthorizedTransactionException class.
     * Creates a new exception with a specific message.
     *
     * @param message The specific message for this exception.
     */
    public UnauthorizedTransactionException(String message) {
        super(message);
    }
}

