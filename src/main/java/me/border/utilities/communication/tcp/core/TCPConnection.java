package me.border.utilities.communication.tcp.core;

import me.border.utilities.communication.base.connection.Connection;


public interface TCPConnection extends Connection {

    /**
     * Send an object to the socket represented by this connection.
     *
     * @param object The object to send to the other connection.
     * @throws TCPCommunicationException If an {@link java.io.IOException} is thrown during the sending, the {@code CommunicationException}
     * will encapsulate the {@code IOException}.
     * The exception will also be thrown if {@param object} is not serializable.
     */
    @Override
    void sendObject(Object object) throws TCPCommunicationException;
}
