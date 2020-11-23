package me.border.utilities.communication.base;

import me.border.utilities.communication.base.connection.Connection;

/**
 * The class {@code Client} is a base class of a client in a two-way Server-Client communication
 */
public interface Client<T extends Connection> {

    /**
     * Shortcut for {@link #start(Class)} to be implemented with the class type of the implementation
     * of {@link Connection} in the project.
     */
    void start();

    /**
     * Start the client and once connected to a server construct a connection {@link Connection}
     * With the given class type (Must extend {@link Connection})
     *
     * @param clazz The class of the connection
     */
    void start(Class<? extends T> clazz);

    /**
     * Get the {@link Connection} the client is connected to
     *
     * @return The connection
     */
    T getServerConnection();
}
