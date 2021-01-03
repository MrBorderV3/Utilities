package me.border.utilities.communication.tcp.v2.client;

import me.border.utilities.communication.base.exception.BuilderException;
import me.border.utilities.communication.tcp.core.TCPClient;
import me.border.utilities.communication.tcp.core.exception.TCPCommunicationException;
import me.border.utilities.communication.tcp.core.base.TCPServerConnection;

import java.io.*;
import java.net.Socket;

public class TCPServerConnectionImpl implements TCPServerConnection {
    private volatile boolean closed = false;

    private final ServerConnectionRunnable runnable;
    public Socket server;

    // INPUT STREAM TO RECEIVE DATA FROM THE CLIENT
    public InputStream in;

    // OUTPUT STREAM TO SEND DATA TO THE CLIENT
    public OutputStream out;

    protected TCPServerConnectionImpl(TCPClient client, Socket socket, ServerConnectionRunnable runnable) throws IOException, BuilderException {
        this.server = socket;
        this.runnable = runnable;
        out = new ObjectOutputStream(server.getOutputStream());
        in = new ObjectInputStream(server.getInputStream());

        runnable.setConnection(this);
        runnable.setIn(in);
        runnable.setOut(out);
        runnable.setClient(client);
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
            throw new TCPCommunicationException(server, "Object [" + o.toString() + "] is not serializable and cannot be sent!");
        }
        try (ObjectOutputStream oout = new ObjectOutputStream(out)) {
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
    public void close() throws Exception {
        validate();
        closed = true;
        in.close();
        out.close();
        server.close();
    }

    @Override
    public boolean isClosed() {
        return closed;
    }
}
