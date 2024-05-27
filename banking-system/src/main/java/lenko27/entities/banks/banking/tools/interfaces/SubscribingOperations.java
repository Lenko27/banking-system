package lenko27.entities.banks.banking.tools.interfaces;

import lenko27.entities.client.Client;

/**
 * This interface represents the operations for managing subscriptions.
 */
public interface SubscribingOperations {

    /**
     * Method to subscribe a client to a service.
     *
     * @param client The client to be subscribed.
     */
    void subscribe(Client client);

    /**
     * Method to unsubscribe a client from a service.
     *
     * @param client The client to be unsubscribed.
     */
    void unsubscribe(Client client);

    /**
     * Method to notify all subscribers with a message.
     *
     * @param message The message to be sent to the subscribers.
     */
    void notifySubscribers(String message);
}

