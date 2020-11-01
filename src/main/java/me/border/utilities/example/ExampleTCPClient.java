package me.border.utilities.example;

import me.border.utilities.communication.tcp.client.AbstractTCPClient;
import me.border.utilities.communication.tcp.client.AbstractTCPServerConnection;

import java.io.IOException;
import java.net.Socket;

public class ExampleTCPClient extends AbstractTCPClient {

    public ExampleTCPClient() {
        super("104.238.158.11", 9090);
        start();
    }

    @Override
    public void start() {
        super.start(ServerTCPConnection.class);
    }

    public static class ServerTCPConnection extends AbstractTCPServerConnection {

        public ServerTCPConnection(Socket server) throws IOException {
            super(server);
        }

        @Override
        public void run() {

        }
    }
}
