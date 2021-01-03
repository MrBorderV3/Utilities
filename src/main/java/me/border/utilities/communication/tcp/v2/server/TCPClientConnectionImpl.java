package me.border.utilities.communication.tcp.v2.server;

import me.border.utilities.communication.base.exception.FactoryException;
import me.border.utilities.communication.tcp.core.exception.TCPCommunicationException;
import me.border.utilities.communication.tcp.core.TCPServer;
import me.border.utilities.communication.tcp.core.base.TCPClientConnection;

import java.io.*;
import java.net.Socket;

public class TCPClientConnectionImpl implements TCPClientConnection {

    private volatile boolean closed = false;

    private final ClientConnectionRunnable runnable;
    public Socket client;

    // INPUT STREAM TO RECEIVE DATA FROM THE CLIENT
    public InputStream in;

    // OUTPUT STREAM TO SEND DATA TO THE CLIENT
    public OutputStream out;

    protected TCPClientConnectionImpl(TCPServer server, Socket socket, ClientConnectionRunnable runnable) throws IOException, FactoryException {
        this.client = socket;
        this.runnable = runnable;
        out = new ObjectOutputStream(client.getOutputStream());
        in = new ObjectInputStream(client.getInputStream());

        runnable.setConnection(this);
        runnable.setIn(in);
        runnable.setOut(out);
        runnable.setServer(server);
        runnable.setSocket(socket);
    }

    @Override
    public void run() {
        runnable.run();
    }

    @Override
    public void sendObject(Object o) throws TCPCommunicationException {
        validate();
        if (!(o instanceof Serializable)){
            throw new TCPCommunicationException(client, "Object [" + o.toString() + "] is not serializable and cannot be sent!");
        }
        try (ObjectOutputStream oout = new ObjectOutputStream(out)) {
            oout.writeObject(o);
            oout.flush();
        } catch (IOException e) {
            throw new TCPCommunicationException(client, e);
        }
    }

    @Override
    public Socket getSocket() {
        return client;
    }

    @Override
    public void close() throws Exception {
        validate();
        closed = true;
        in.close();
        out.close();
        client.close();
    }

    @Override
    public boolean isClosed() {
        return closed;
    }
}
