package lenko27.entities.client;

import lombok.Builder;
import lombok.NonNull;
import lombok.Setter;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a client in the banking system.
 */
@Getter
@Builder(builderMethodName = "hiddenBuilder")
public class Client{

    /**
     * The client's name.
     */
    private @NonNull String name;

    /**
     * The client's surname.
     */
    private @NonNull String surname;

    /**
     * The client's address.
     */
    private @Setter String address;

    /**
     * The client's passport data.
     */
    private @Setter String passportData;

    /**
     * The list of messages associated with the client.
     */
    @Builder.Default
    @Getter private List<String> messages = new ArrayList<>();

    /**
     * Method to check if the client is suspicious.
     * A client is considered suspicious if both their address and passport data are null.
     *
     * @return true if the client is suspicious, false otherwise.
     */
    public boolean isSuspicious() {
        return (getAddress() == null) && (getPassportData() == null);
    }
    /**
     * Method to update the client's list of messages.
     *
     * @param message The message to be added.
     */
    public void update(String message){
        messages.add(message);
    }
    /**
     * Static method to create a new ClientBuilder.
     *
     * @param name The client's name.
     * @param surname The client's surname.
     * @return a new ClientBuilder.
     */
    public static ClientBuilder builder(String name, String surname) {
        return hiddenBuilder().name(name).surname(surname);
    }
}

