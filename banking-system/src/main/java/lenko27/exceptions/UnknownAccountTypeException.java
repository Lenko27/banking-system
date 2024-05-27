package lenko27.exceptions;

import lenko27.entities.client.Client;

/**
 * This class represents an exception that is thrown when an unknown account type is encountered.
 * It extends the BankingOperationsException class, adding several constructors for different use cases.
 */
public class UnknownAccountTypeException extends BankingOperationsException {

    /**
     * Default constructor for the UnknownAccountTypeException class.
     * Creates a new exception with a default message.
     */
    public UnknownAccountTypeException() {
        super("Unknown account type. User is not allowed to choose this type of account.");
    }

    /**
     * Constructor for the UnknownAccountTypeException class.
     * Creates a new exception with a specific message about the client.
     *
     * @param client The client who is not allowed to choose the unknown account type.
     */
    public UnknownAccountTypeException(Client client) {
        super("Client " + client.getName() + " " + client.getSurname() + ": Unknown account type. User is not allowed to choose this type of account.");
    }

    /**
     * Constructor for the UnknownAccountTypeException class.
     * Creates a new exception with a specific message.
     *
     * @param message The specific message for this exception.
     */
    public UnknownAccountTypeException(String message) {
        super(message);
    }
}

