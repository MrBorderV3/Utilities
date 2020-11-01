package me.border.utilities.communication.tcp.core.connection;

import me.border.utilities.communication.base.connection.ServerConnection;
import me.border.utilities.communication.tcp.core.TCPCommunicationException;

/**
 * The class {@code TCPServerConnection} is a TCP Implementation of the {@link ServerConnection}
 */
public interface TCPServerConnection extends ServerConnection {

    @Override
    void sendObject(Object object) throws TCPCommunicationException;
}
