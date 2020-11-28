package me.border.utilities.communication.tcp.core;

import me.border.utilities.communication.tcp.core.base.TCPServerConnection;

/**
 * The class {@code Client} is a base class of a client in a two-way Server-Client communication
 */
public interface TCPClient {

    /**
     * Start the client and once connected to a server construct a connection {@link TCPServerConnection}
     */
    void start();

    /**
     * Get the {@link TCPServerConnection} the client is connected to
     *
     * @return The connection
     */
    TCPServerConnection getServerConnection();
}
