package lenko27.entities.transactions;

/**
 * This enum represents the type of a transaction in the banking system.
 */
public enum TransactionType {

    /**
     * Represents a refill transaction, where money is added to an account.
     */
    REFILL,

    /**
     * Represents a withdraw transaction, where money is taken from an account.
     */
    WITHDRAW,

    /**
     * Represents a transfer transaction where the current account is the sender.
     */
    TRANSFERSENDER,

    /**
     * Represents a transfer transaction where the current account is the receiver.
     */
    TRANSFERACCEPTER,
}

