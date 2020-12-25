package me.border.utilities.communication.tcp.core.base;

import me.border.utilities.communication.base.Connection;
import me.border.utilities.communication.tcp.core.TCPCommunicationException;

import java.io.IOException;
import java.net.Socket;

/**
 * Represents a TCP connection to either a server or a client socket.
 */
interface TCPConnection extends Connection {

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

    /**
     * Get the socket associated with this connection.
     *
     * @return The socket.
     */
    Socket getSocket();

    /**
     * Close this connection
     */
    void close() throws IOException;
}
