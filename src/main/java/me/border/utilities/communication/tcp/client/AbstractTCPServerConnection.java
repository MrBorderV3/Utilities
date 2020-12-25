package me.border.utilities.communication.tcp.client;

import me.border.utilities.communication.tcp.core.TCPCommunicationException;
import me.border.utilities.communication.tcp.core.base.TCPServerConnection;

import java.io.*;
import java.net.Socket;

/**
 * The class {@code AbstractTCPServerConnection} represents a socket connection to a client from a server
 *
 * @see TCPServerConnection
 */
public abstract class AbstractTCPServerConnection implements TCPServerConnection {
    protected final Socket server;
    protected final OutputStream out;
    protected final InputStream in;

    public AbstractTCPServerConnection(Socket server) throws IOException {
        this.server = server;
        out = server.getOutputStream();
        in = server.getInputStream();
    }

    @Override
    public void sendObject(Object o) throws TCPCommunicationException {
        if (!(o instanceof Serializable)){
            throw new TCPCommunicationException(server, "Object [" + o.toString() + "] is not serializable and cannot be sent!");
        }
        try (ObjectOutputStream oout = new ObjectOutputStream(out)){
            oout.writeObject(o);
            oout.flush();
        } catch (IOException e) {
            throw new TCPCommunicationException(server, e);
        }
    }

    @Override
    public Socket getSocket() {
        return server;
    }

    @Override
    public void close() throws IOException {
        in.close();
        out.close();
        server.close();
    }
}
