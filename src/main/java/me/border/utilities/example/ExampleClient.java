package me.border.utilities.example;

import me.border.utilities.communication.client.AbstractClient;
import me.border.utilities.communication.client.AbstractServerConnection;

import java.io.IOException;
import java.net.Socket;

public class ExampleClient extends AbstractClient {

    public ExampleClient() {
        super("104.238.158.11", 9090);
        start();
    }

    @Override
    public void start() {
        super.start(ServerConnection.class);
    }

    public static class ServerConnection extends AbstractServerConnection {

        public ServerConnection(Socket server) throws IOException {
            super(server);
        }

        @Override
        public void run() {

        }
    }
}
