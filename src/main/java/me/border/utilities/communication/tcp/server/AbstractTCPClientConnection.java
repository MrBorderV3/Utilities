package me.border.utilities.communication.tcp.server;

import me.border.utilities.communication.tcp.core.TCPConnection;
import me.border.utilities.communication.tcp.core.TCPCommunicationException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

/**
 * The class {@code AbstractTCPClientConnection} represents a socket connection to a client from a server
 *
 * @see TCPConnection
 */
public abstract class AbstractTCPClientConnection implements TCPConnection {

    protected Socket client;
    protected ObjectInputStream oin;
    protected ObjectOutputStream oout;

    public AbstractTCPClientConnection(Socket clientSocket) throws IOException {
        this.client = clientSocket;
        oout = new ObjectOutputStream(client.getOutputStream());
        oin = new ObjectInputStream(client.getInputStream());
    }

    @Override
    public void sendObject(Object o) throws TCPCommunicationException {
        if (!(o instanceof Serializable)){
            throw new TCPCommunicationException(client, "Object [" + o.toString() + "] is not serializable and cannot be sent!");
        }
        try {
            oout.writeObject(o);
            oout.flush();
        } catch (IOException e){
            throw new TCPCommunicationException(client, e);
        }
    }
}
