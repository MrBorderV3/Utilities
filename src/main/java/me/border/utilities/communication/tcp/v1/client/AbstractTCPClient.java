package me.border.utilities.communication.tcp.v1.client;

import me.border.utilities.communication.base.build.ConnectionBuilder;
import me.border.utilities.communication.tcp.core.TCPClient;
import me.border.utilities.communication.tcp.core.base.TCPServerConnection;

import java.net.Socket;

/**
 * The class {@code AbstractTCPClient} is an abstract class of a client in a two-way Server-Client communication
 */
public abstract class AbstractTCPClient implements TCPClient {
    private volatile boolean closed = false;

    protected TCPServerConnection serverConnection;
    protected Socket server;

    protected String ip;
    protected int port;

    public AbstractTCPClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    protected void start(ConnectionBuilder<TCPServerConnection> builder) {
        validate();
        try {
            this.server = new Socket(ip, port);

            this.serverConnection = builder.setSocket(server).build();
            new Thread(this.serverConnection).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void start(Class<? extends TCPServerConnection> clazz) {
        validate();
        try {
            this.server = new Socket(ip, port);

            this.serverConnection = clazz.getConstructor(Socket.class).newInstance(server);
            new Thread(this.serverConnection).start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public TCPServerConnection getServerConnection() {
        return serverConnection;
    }

    @Override
    public void close() throws Exception {
        validate();
        closed = true;
        serverConnection.close();
        server.close();
    }

    @Override
    public boolean isClosed() {
        return closed;
    }
}
