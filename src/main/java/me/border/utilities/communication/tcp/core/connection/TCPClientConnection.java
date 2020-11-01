package me.border.utilities.communication.tcp.core.connection;

import me.border.utilities.communication.base.connection.ClientConnection;
import me.border.utilities.communication.tcp.core.TCPCommunicationException;

/**
 * The class {@code TCPClientConnection} is a TCP Implementation of the {@link ClientConnection}
 */
public interface TCPClientConnection extends ClientConnection {

    @Override
    void sendObject(Object object) throws TCPCommunicationException;
}
