package me.border.utilities.communication.tcp.core;

import me.border.utilities.communication.tcp.core.base.TCPClientConnection;
import me.border.utilities.communication.tcp.core.exception.TCPCommunicationException;
import me.border.utilities.communication.tcp.core.exception.TCPStartupException;
import me.border.utilities.terminable.Terminable;

import java.util.Set;

/**
 * The class {@code Server} is a base class of a server in a two-way Server-Client communication
 */
public interface TCPServer extends Terminable {

    /**
     * Start the server
     *
     * @throws TCPStartupException If the server is already started or has failed to start.
     */
    void start() throws TCPStartupException;

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