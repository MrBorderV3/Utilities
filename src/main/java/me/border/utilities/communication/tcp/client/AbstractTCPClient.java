package me.border.utilities.communication.tcp.client;

import me.border.utilities.communication.base.build.ConnectionBuilder;
import me.border.utilities.communication.tcp.core.TCPClient;
import me.border.utilities.communication.tcp.core.base.TCPServerConnection;

import java.net.Socket;

/**
 * The class {@code AbstractTCPClient} is an abstract class of a client in a two-way Server-Client communication
 */
public abstract class AbstractTCPClient implements TCPClient {

    protected TCPServerConnection serverConnection;
    protected Socket server;

    protected String ip;
    protected int port;

    public AbstractTCPClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    protected void start(ConnectionBuilder<TCPServerConnection> builder) {
        try {
            this.server = new Socket(ip, port);

            this.serverConnection = builder.setSocket(server).build();
            new Thread(this.serverConnection).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void start(Class<? extends TCPServerConnection> clazz) {
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
}
