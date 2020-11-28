package me.border.utilities.communication.tcp.impl.client;

import me.border.utilities.communication.base.Connection;
import me.border.utilities.communication.base.ConnectionRunnable;
import me.border.utilities.communication.base.exception.BuilderException;
import me.border.utilities.communication.tcp.core.TCPClient;
import me.border.utilities.communication.tcp.core.base.TCPServerConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public abstract class ServerConnectionRunnable implements ConnectionRunnable {

    private TCPClient client;
    private TCPServerConnection connection;

    private Socket socket;
    private OutputStream out;
    private InputStream in;

    public void setClient(TCPClient client) {
        this.client = client;
    }

    @Override
    public void setConnection(Connection connection) throws BuilderException {
        if (!(connection instanceof TCPServerConnection)){
            throw new BuilderException("Invalid connection given at runnable");
        }

        this.connection = (TCPServerConnection) connection;
    }

    @Override
    public void setOut(OutputStream out) {
        this.out = out;
    }

    @Override
    public void setIn(InputStream in) {
        this.in = in;
    }

    @Override
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public TCPClient getClient() {
        return client;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public OutputStream getOut() {
        return out;
    }

    @Override
    public InputStream getIn() {
        return in;
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