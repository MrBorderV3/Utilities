package me.border.utilities.communication.tcp.client;

import me.border.utilities.communication.tcp.core.TCPCommunicationException;
import me.border.utilities.communication.tcp.core.TCPConnection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

/**
 * The class {@code AbstractTCPServerConnection} represents a socket connection to a client from a server
 *
 * @see TCPConnection
 */
public abstract class AbstractTCPServerConnection implements TCPConnection {
    protected Socket server;
    protected ObjectOutputStream oout;
    protected ObjectInputStream oin;

    public AbstractTCPServerConnection(Socket server) throws IOException {
        this.server = server;
        oout = new ObjectOutputStream(server.getOutputStream());
        oin = new ObjectInputStream(server.getInputStream());
    }

    @Override
    public void sendObject(Object o) throws TCPCommunicationException {
        if (!(o instanceof Serializable)){
            throw new TCPCommunicationException(server, "Object [" + o.toString() + "] is not serializable and cannot be sent!");
        }
        try {
            oout.writeObject(o);
            oout.flush();
        } catch (IOException e) {
            throw new TCPCommunicationException(server, e);
        }
    }
}
