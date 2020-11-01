package me.border.utilities.communication.tcp.client;

import me.border.utilities.communication.base.Client;

import java.net.Socket;

/**
 * The class {@code AbstractTCPClient} is an abstract class of a client in a two-way Server-Client communication
 */
public abstract class AbstractTCPClient implements Client<AbstractTCPServerConnection> {

    protected AbstractTCPServerConnection serverConnection;
    protected Socket server;

    protected String ip;
    protected int port;

    public AbstractTCPClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public abstract void start();

    @Override
    public void start(Class<? extends AbstractTCPServerConnection> clazz) {
        try {
            this.server = new Socket(ip, port);

            this.serverConnection = clazz.getConstructor(Socket.class).newInstance(server);
            new Thread(this.serverConnection).start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public AbstractTCPServerConnection getServerConnection() {
        return serverConnection;
    }
}
