package me.border.utilities.communication.base;

import me.border.utilities.communication.base.exception.CommunicationException;
import me.border.utilities.terminable.Terminable;

import java.io.IOException;

/**
 * The class presents a core form of a connection to either a Server or a Client in either TCP or UDP
 * The class must be ran on a different thread that is in charge of handling all the communication
 * between the current socket to the socket represented by this class
 */
public interface Connection extends Runnable, Terminable {

    /**
     * Send an object to the socket represented by this connection.
     *
     * @param object The object to send to the other connection.
     * @throws CommunicationException If an {@link IOException} is thrown during the sending, the {@link CommunicationException}
     * will encapsulate the {@link IOException}.
     * The exception will also be thrown if {@code object} is not serializable.
     */
    void sendObject(Object object) throws CommunicationException;
}
