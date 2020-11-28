package me.border.utilities.communication.tcp.impl.client;

import me.border.utilities.communication.tcp.core.TCPClient;
import me.border.utilities.communication.tcp.core.base.TCPServerConnection;

import java.net.Socket;

public class TCPClientImpl implements TCPClient {

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

    public void start() {
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
}
