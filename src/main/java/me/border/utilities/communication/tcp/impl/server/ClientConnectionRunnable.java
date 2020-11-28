package me.border.utilities.communication.tcp.impl.server;

import me.border.utilities.communication.base.Connection;
import me.border.utilities.communication.base.ConnectionRunnable;
import me.border.utilities.communication.base.exception.FactoryException;
import me.border.utilities.communication.tcp.core.TCPServer;
import me.border.utilities.communication.tcp.core.base.TCPClientConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public abstract class ClientConnectionRunnable implements ConnectionRunnable {

    private TCPServer server;
    private TCPClientConnection connection;

    private Socket socket;
    private OutputStream out;
    private InputStream in;

    public void setServer(TCPServer server) {
        this.server = server;
    }

    @Override
    public void setConnection(Connection connection) throws FactoryException {
        if (!(connection instanceof TCPClientConnection)){
            throw new FactoryException("Invalid connection given at runnable");
        }

        this.connection = (TCPClientConnection) connection;
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

    public TCPServer getServer() {
        return server;
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
        server.getConnections().remove(connection);
    }
}
