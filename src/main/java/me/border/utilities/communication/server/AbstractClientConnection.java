package me.border.utilities.communication.server;

import me.border.utilities.communication.core.CommunicationException;
import me.border.utilities.communication.core.Connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

/**
 * The class {@code AbstractClientConnection} represents a socket connection to a client from a server
 *
 * @see Connection
 */
public abstract class AbstractClientConnection implements Connection {

    protected Socket client;
    protected ObjectInputStream oin;
    protected ObjectOutputStream oout;

    public AbstractClientConnection(Socket clientSocket) throws IOException {
        this.client = clientSocket;
        oout = new ObjectOutputStream(client.getOutputStream());
        oin = new ObjectInputStream(client.getInputStream());
    }

    @Override
    public void sendObject(Object o) throws CommunicationException {
        if (!(o instanceof Serializable)){
            throw new CommunicationException(client, "Object [" + o.toString() + "] is not serializable and cannot be sent!");
        }
        try {
            oout.writeObject(o);
            oout.flush();
        } catch (IOException e){
            throw new CommunicationException(client, e);
        }
    }
}
