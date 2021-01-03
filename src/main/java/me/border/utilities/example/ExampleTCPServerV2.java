package me.border.utilities.example;

import me.border.utilities.communication.base.exception.BuilderException;
import me.border.utilities.communication.tcp.core.exception.TCPStartupException;
import me.border.utilities.communication.tcp.core.TCPServer;
import me.border.utilities.communication.tcp.v2.server.ClientConnectionRunnable;
import me.border.utilities.communication.tcp.v2.server.TCPServerBuilder;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.ExecutorService;

public class ExampleTCPServerV2 {

    private TCPServer server;

    private ExampleTCPServerV2(int port, ExecutorService pool) {
        try {
            server = new TCPServerBuilder().setPool(pool).setPort(port)
                    .setRunnable(new ClientConnectionRunnable() {
                        @Override
                        public void run() {
                            try {
                                ObjectInputStream oin = new ObjectInputStream(getIn());
                                while (true) {
                                    Object o = oin.readObject();
                                    System.out.println(o);
                                }

                            } catch (EOFException ignored) {
                                // Error that gets thrown when the client ends communication. no need to print stacktrace.
                                // that's how the server knows to close communication incase the client didn't let the server know about closing the comms.
                            } catch (IOException | ClassNotFoundException e) {
                                e.printStackTrace();
                            } finally {
                                getConnection().closeSilently();
                                System.out.println("Closed Connection with client at " + getSocket().getInetAddress().getHostAddress());
                            }
                        }
                    }).build();
        } catch (BuilderException ex) {
            ex.printStackTrace();
        }
    }

    public void start() throws TCPStartupException {
        server.start();
    }

}
