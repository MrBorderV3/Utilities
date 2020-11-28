package me.border.utilities.communication.tcp.core;

import me.border.utilities.communication.tcp.core.base.TCPClientConnection;

import java.util.Set;

/**
 * The class {@code Server} is a base class of a server in a two-way Server-Client communication
 */
public interface TCPServer {

    /**
     * Start the server and once a client connects construct a connection {@link TCPClientConnection}
     */
    void start();

    /**
     * Send an object to all clients
     *
     * @param object The object to send
     * @throws TCPCommunicationException If an exception occurs during the sending to one of the clients
     *
     * @see TCPClientConnection#sendObject(Object)
     */
    void sendAllClients(Object object) throws TCPCommunicationException;

    /**
     * Get all the connected clients
     *
     * @return A set containing all the connected clients
     */
    Set<TCPClientConnection> getConnections();
}