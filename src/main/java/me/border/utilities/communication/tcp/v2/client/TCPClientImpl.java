package me.border.utilities.communication.tcp.v2.client;

import me.border.utilities.communication.tcp.core.TCPClient;
import me.border.utilities.communication.tcp.core.base.TCPClientConnection;
import me.border.utilities.communication.tcp.core.base.TCPServerConnection;
import me.border.utilities.communication.tcp.core.exception.TCPStartupException;

import java.net.Socket;

public class TCPClientImpl implements TCPClient {
    private boolean started = false;
    private volatile boolean closed = false;

    private final TCPServerConnectionBuilder builder;
    private TCPServerConnection serverConnection;

    private final int port;
    private final String ip;

    protected TCPClientImpl(String ip, int port, TCPServerConnectionBuilder builder) {
        this.port = port;
        this.ip = ip;
        this.builder = builder;
        builder.setClient(this);
    }

    public void start() throws TCPStartupException {
        validate();
        if (started)
            throw new TCPStartupException();
        started = true;
        try {
            Socket server = new Socket(ip, port);

            this.serverConnection = builder.setSocket(server).build();
            new Thread(this.serverConnection).start();

        } catch (Exception e) {
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
    }

    @Override
    public boolean isClosed() {
        return closed;
    }
}
