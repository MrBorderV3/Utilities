package me.border.utilities.example;

import me.border.utilities.communication.tcp.server.AbstractTCPClientConnection;
import me.border.utilities.communication.tcp.server.AbstractTCPServer;

import java.io.IOException;
import java.net.Socket;

public class ExampleTCPServer extends AbstractTCPServer {

    private ExampleTCPServer() {
        super(9090, 1);
        start();
    }

    @Override
    public void start() {
        super.start(TCPClientConnection.class);
    }

    public static class TCPClientConnection extends AbstractTCPClientConnection {

        public TCPClientConnection(Socket clientSocket) throws IOException {
            super(clientSocket);
        }

        @Override
        public void run() {
        }
    }
}
