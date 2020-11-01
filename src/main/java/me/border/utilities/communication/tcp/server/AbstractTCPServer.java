package me.border.utilities.communication.tcp.server;

import me.border.utilities.communication.base.Server;
import me.border.utilities.communication.tcp.core.TCPCommunicationException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The class {@code AbstractTCPServer} is an abstract class of a TCP server in a two-way Server-Client communication
 */
public abstract class AbstractTCPServer implements Server<AbstractTCPClientConnection> {

    public Set<AbstractTCPClientConnection> clientConnections = new HashSet<>();

    private ServerSocket ss;
    private ExecutorService pool;

    private AbstractTCPServer(int port) {
        try {
            this.ss = new ServerSocket(port);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public AbstractTCPServer(int port, ExecutorService pool) {
        this(port);
        this.pool = pool;
    }

    public AbstractTCPServer(int port, int pool) {
        this(port);
        this.pool = Executors.newFixedThreadPool(pool);
    }

    @Override
    public abstract void start();

    @Override
    public void start(Class<? extends AbstractTCPClientConnection> clazz) {
        try {
            while (true) {
                System.out.println("Listening on port 9090");
                // WAITS UNTIL IT GETS A CONNECTION
                Socket socket = ss.accept();
                System.out.println("Successfully connected to client at " + socket.getInetAddress().getHostAddress());
                AbstractTCPClientConnection clientConnection = clazz.getConstructor(Socket.class).newInstance(socket);
                clientConnections.add(clientConnection);

                pool.execute(clientConnection);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void sendAllClients(Object o) throws TCPCommunicationException {
        for (AbstractTCPClientConnection clientConnection : clientConnections) {
            clientConnection.sendObject(o);
        }
    }
}
