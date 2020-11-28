package me.border.utilities.example;

import me.border.utilities.communication.tcp.client.AbstractTCPClient;
import me.border.utilities.communication.tcp.client.AbstractTCPServerConnection;

import java.io.IOException;
import java.net.Socket;

public class ExampleTCPClientV2 extends AbstractTCPClient {
    public ExampleTCPClientV2(String ip, int port) {
        super(ip, port);
    }

    @Override
    public void start() {
        super.start(ExampleTCPServerConnection.class);
    }

    private class ExampleTCPServerConnection extends AbstractTCPServerConnection {

        public ExampleTCPServerConnection(Socket server) throws IOException {
            super(server);
        }

        @Override
        public void run() {
            System.out.println("I RAN\n" + server.toString());
        }
    }
}
