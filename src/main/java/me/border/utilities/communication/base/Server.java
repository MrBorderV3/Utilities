package me.border.utilities.communication.base;

import me.border.utilities.communication.base.connection.ClientConnection;
import me.border.utilities.communication.base.exception.CommunicationException;

/**
 * The class {@code Server} is a base class of a server in a two-way Server-Client communication
 */
public interface Server<T extends ClientConnection> {

    /**
     * Shortcut for {@link #start(Class)} to be implemented with the class type of the implementation
     * of {@link ClientConnection} in the project.
     */
    void start();

    /**
     * Start the server and once a client connects construct a connection {@link ClientConnection}
     * With the given class type (Must extend {@link ClientConnection})
     *
     * @param clazz The class of the connection
     */
    void start(Class<? extends T> clazz);

    /**
     * Send an object to all clients
     *
     * @param object The object to send
     * @throws CommunicationException If an exception occurs during the sending to one of the clients
     *
     * @see ClientConnection#sendObject(Object)
     */
    void sendAllClients(Object object) throws CommunicationException;
}