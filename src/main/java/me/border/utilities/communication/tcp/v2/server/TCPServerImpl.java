package me.border.utilities.communication.tcp.v2.server;

import me.border.utilities.communication.tcp.core.exception.TCPRuntimeException;
import me.border.utilities.communication.tcp.core.exception.TCPStartupException;
import me.border.utilities.communication.tcp.core.TCPServer;
import me.border.utilities.communication.tcp.core.exception.TCPCommunicationException;
import me.border.utilities.communication.tcp.core.base.TCPClientConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public class TCPServerImpl implements TCPServer {

    private boolean started = false;
    private volatile boolean closed = false;

    public Set<TCPClientConnection> clientConnections = new HashSet<>();

    private final TCPClientConnectionFactory factory;

    private final int port;
    private final ServerSocket ss;
    private ExecutorService pool;

    private TCPServerImpl(int port, TCPClientConnectionFactory factory) throws IOException {
        this.port = port;
        this.ss = new ServerSocket(port);
        this.factory = factory;
        factory.setServer(this);
    }

    protected TCPServerImpl(int port, TCPClientConnectionFactory factory, ExecutorService pool) throws IOException {
        this(port, factory);
        this.pool = pool;
    }

    public void start() throws TCPStartupException {
        validate();
        if (started)
            throw new TCPStartupException();
        started = true;
        try {
            while (!closed) {
                System.out.println("Listening on port " + port);
                // WAITS UNTIL IT GETS A CONNECTION
                Socket socket = ss.accept();
                System.out.println("Successfully connected to client at " + socket.getInetAddress().getHostAddress());
                TCPClientConnection clientConnection = factory.constructConn(socket);
                clientConnections.add(clientConnection);

                pool.execute(clientConnection);
            }
        } catch (Exception e){
            throw new TCPRuntimeException(e);
        }
    }


    @Override
    public void sendAllClients(Object o) throws TCPCommunicationException {
        validate();
        for (TCPClientConnection clientConnection : clientConnections) {
            clientConnection.sendObject(o);
        }
    }

    @Override
    public Set<TCPClientConnection> getConnections() {
        return clientConnections;
    }

    @Override
    public void close() throws Exception {
        validate();
        closed = true;
        for (TCPClientConnection clientConnection : clientConnections){
            clientConnection.close();
        }

        clientConnections.clear();
        this.ss.close();
        pool.shutdown();
    }

    @Override
    public boolean isClosed() {
        return closed;
    }
}
