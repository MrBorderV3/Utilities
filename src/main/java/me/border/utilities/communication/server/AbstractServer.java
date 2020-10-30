package me.border.utilities.communication.server;

import me.border.utilities.communication.core.CommunicationException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The class {@code AbstractServer} is an abstract class of a server in a two-way Server-Client communication
 */
public abstract class AbstractServer {

    public static Set<AbstractClientConnection> clientConnections = new HashSet<>();

    private ServerSocket ss;
    private ExecutorService pool;

    private AbstractServer(int port) {
        try {
            this.ss = new ServerSocket(port);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public AbstractServer(int port, ExecutorService pool) {
        this(port);
        this.pool = pool;
    }

    public AbstractServer(int port, int pool) {
        this(port);
        this.pool = Executors.newFixedThreadPool(pool);
    }

    /**
     * Shortcut for {@link #start(Class)} to be implemented with the class type of the implementation
     * of {@link AbstractClientConnection} in the project.
     */
    public abstract void start();

    /**
     * Start the server and once a client connects construct a connection {@link AbstractClientConnection}
     * With the given class type (Must extend {@link AbstractClientConnection}
     *
     * @param clazz The class of the connection
     */
    protected void start(Class<? extends AbstractClientConnection> clazz) {
        try {
            while (true) {
                System.out.println("Listening on port 9090");
                // WAITS UNTIL IT GETS A CONNECTION
                Socket socket = ss.accept();
                System.out.println("Successfully connected to client at " + socket.getInetAddress().getHostAddress());
                AbstractClientConnection clientConnection = clazz.getConstructor(Socket.class).newInstance(socket);
                clientConnections.add(clientConnection);

                pool.execute(clientConnection);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Send an object to all clients
     *
     * @param o The object to send
     * @throws CommunicationException If an exception occurs during the sending to one of the clients
     *
     * @see me.border.utilities.communication.core.Connection
     */
    public static void sendAllClients(Object o) throws CommunicationException {
        for (AbstractClientConnection clientConnection : clientConnections) {
            clientConnection.sendObject(o);
        }
    }
}
