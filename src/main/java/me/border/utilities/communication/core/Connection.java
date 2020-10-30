package me.border.utilities.communication.core;

/**
 * The class {@code Connection} presents a core form of a connection to either a Server or a Client
 * The class must be ran on a different thread that is in charge of handling all the communication
 * between the current socket to the socket represented by this class
 *
 * @see me.border.utilities.communication.client.AbstractClient
 * @see me.border.utilities.communication.server.AbstractServer
 */
public interface Connection extends Runnable {

    /**
     * Send an object to the socket represented by this connection.
     *
     * @param object The object to send to the other connection.
     * @throws CommunicationException If an {@link java.io.IOException} is thrown during the sending, the {@code CommunicationException}
     * will encapsulate the {@code IOException} or if the {@param object} is not serializable
     */
    void sendObject(Object object) throws CommunicationException;
}
