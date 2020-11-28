package me.border.utilities.communication.tcp.impl.server;

import me.border.utilities.communication.base.ConnectionRunnable;
import me.border.utilities.communication.base.build.ConnectionFactory;
import me.border.utilities.communication.base.exception.FactoryException;
import me.border.utilities.communication.tcp.core.TCPServer;
import me.border.utilities.communication.tcp.core.base.TCPClientConnection;

import java.io.IOException;
import java.net.Socket;

public class TCPClientConnectionFactory implements ConnectionFactory<TCPClientConnection> {

    private TCPServer server;
    private final ClientConnectionRunnable runnable;

    protected TCPClientConnectionFactory(ClientConnectionRunnable runnable){
        this.runnable = runnable;
    }

    public void setServer(TCPServer server) {
        this.server = server;
    }

    @Override
    public TCPClientConnection constructConn(Socket socket) throws FactoryException {
        try {
            return new TCPClientConnectionImpl(server, socket, runnable);
        } catch (IOException ex){
            throw new FactoryException(ex);
        }
    }

    @Override
    public TCPClientConnection constructConn(Socket socket, ConnectionRunnable runnable) throws FactoryException {
        if (!(runnable instanceof ClientConnectionRunnable)){
            throw new FactoryException("Invalid runnable");
        }
        try {
            return new TCPClientConnectionImpl(server, socket, (ClientConnectionRunnable) runnable);
        } catch (IOException ex){
            throw new FactoryException(ex);
        }
    }

}
