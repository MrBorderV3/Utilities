package me.border.utilities.communication.client;

import java.net.Socket;

/**
 * The class {@code AbstractClient} is an abstract class of a client in a two-way Server-Client communication
 */
public abstract class AbstractClient {

    protected AbstractServerConnection serverConnection;
    protected Socket server;

    protected String ip;
    protected int port;

    public AbstractClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * Shortcut for {@link #start(Class)} to be implemented with the class type of the implementation
     * of {@link AbstractServerConnection} in the project.
     */
    public abstract void start();

    /**
     * Start the client and once connected to a server construct a connection {@link AbstractServerConnection}
     * With the given class type (Must extend {@link AbstractServerConnection}
     *
     * @param clazz The class of the connection
     */
    protected void start(Class<? extends AbstractServerConnection> clazz) {
        try {
            this.server = new Socket(ip, port);

            this.serverConnection = clazz.getConstructor(Socket.class).newInstance(server);
            new Thread(this.serverConnection).start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Get the {@link AbstractServerConnection} the client is connected to
     *
     * @return The connection
     */
    public AbstractServerConnection getServerConnection() {
        return serverConnection;
    }
}
