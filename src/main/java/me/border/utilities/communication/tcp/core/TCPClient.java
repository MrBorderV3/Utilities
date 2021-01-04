package me.border.utilities.communication.tcp.core;

import me.border.utilities.communication.tcp.core.base.TCPServerConnection;
import me.border.utilities.communication.tcp.core.exception.TCPStartupException;
import me.border.utilities.terminable.Terminable;

/**
 * The class {@code Client} is a base class of a client in a two-way Server-Client communication
 */
public interface TCPClient extends Terminable {

    /**
     * Start the client and once connected to a server construct a connection {@link TCPServerConnection}
     *
     * @throws TCPStartupException If the startup has failed.
     */
    void start() throws TCPStartupException;

    /**
     * Get the {@link TCPServerConnection} the client is connected to
     *
     * @return The connection
     */
    TCPServerConnection getServerConnection();
}
