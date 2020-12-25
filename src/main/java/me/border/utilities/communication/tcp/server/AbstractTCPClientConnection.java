package me.border.utilities.communication.tcp.server;

import me.border.utilities.communication.tcp.core.TCPCommunicationException;
import me.border.utilities.communication.tcp.core.base.TCPClientConnection;
import me.border.utilities.communication.tcp.core.base.TCPServerConnection;

import java.io.*;
import java.net.Socket;

/**
 * The class {@code AbstractTCPClientConnection} represents a socket connection to a client from a server
 *
 * @see TCPServerConnection
 */
public abstract class AbstractTCPClientConnection implements TCPClientConnection {

    protected final Socket socket;

    // INPUT STREAM TO RECEIVE DATA FROM THE CLIENT
    protected final InputStream in;

    // OUTPUT STREAM TO SEND DATA TO THE CLIENT
    protected final OutputStream out;

    public AbstractTCPClientConnection(Socket clientSocket) throws IOException {
        this.socket = clientSocket;
        out = socket.getOutputStream();
        in = socket.getInputStream();
    }

    @Override
    public void sendObject(Object o) throws TCPCommunicationException {
        if (!(o instanceof Serializable)){
            throw new TCPCommunicationException(socket, "Object [" + o.toString() + "] is not serializable and cannot be sent!");
        }
        try (ObjectOutputStream oout = new ObjectOutputStream(out)){
            oout.writeObject(o);
            oout.flush();
        } catch (IOException e){
            throw new TCPCommunicationException(socket, e);
        }
    }

    @Override
    public Socket getSocket() {
        return socket;
    }

    @Override
    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}
