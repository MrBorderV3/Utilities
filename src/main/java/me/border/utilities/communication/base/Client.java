package me.border.utilities.communication.base;

import me.border.utilities.communication.base.connection.ServerConnection;

/**
 * The class {@code Client} is a base class of a client in a two-way Server-Client communication
 */
public interface Client<T extends ServerConnection> {

    /**
     * Shortcut for {@link #start(Class)} to be implemented with the class type of the implementation
     * of {@link ServerConnection} in the project.
     */
    void start();

    /**
     * Start the client and once connected to a server construct a connection {@link ServerConnection}
     * With the given class type (Must extend {@link ServerConnection})
     *
     * @param clazz The class of the connection
     */
    void start(Class<? extends T> clazz);

    /**
     * Get the {@link ServerConnection} the client is connected to
     *
     * @return The connection
     */
    T getServerConnection();
}
