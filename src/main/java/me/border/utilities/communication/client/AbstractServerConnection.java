package me.border.utilities.communication.client;

import me.border.utilities.communication.core.CommunicationException;
import me.border.utilities.communication.core.Connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

/**
 * The class {@code AbstractServerConnection} represents a socket connection to a server from a client
 *
 * @see Connection
 */
public abstract class AbstractServerConnection implements Connection {
    protected Socket server;
    protected ObjectOutputStream oout;
    protected ObjectInputStream oin;

    public AbstractServerConnection(Socket server) throws IOException {
        this.server = server;
        oout = new ObjectOutputStream(server.getOutputStream());
        oin = new ObjectInputStream(server.getInputStream());
    }

    @Override
    public void sendObject(Object o) throws CommunicationException {
        if (!(o instanceof Serializable)){
            throw new CommunicationException(server, "Object [" + o.toString() + "] is not serializable and cannot be sent!");
        }
        try {
            oout.writeObject(o);
            oout.flush();
        } catch (IOException e) {
            throw new CommunicationException(server, e);
        }
    }
}
