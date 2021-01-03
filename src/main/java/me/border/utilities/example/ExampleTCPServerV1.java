package me.border.utilities.example;

import me.border.utilities.communication.tcp.v1.server.AbstractTCPClientConnection;
import me.border.utilities.communication.tcp.v1.server.AbstractTCPServer;

import java.io.IOException;
import java.net.Socket;

public class ExampleTCPServerV1 extends AbstractTCPServer {
    public ExampleTCPServerV1(int port, int pool) {
        super(port, pool);
    }

    @Override
    public void start() {
        super.start(ExampleTCPClientConnection.class);
    }

    private class ExampleTCPClientConnection extends AbstractTCPClientConnection {

        public ExampleTCPClientConnection(Socket clientSocket) throws IOException {
            super(clientSocket);
        }

        @Override
        public void run() {
            System.out.println("I RAN MF\n" + socket.toString());
        }

        @Override
        public Socket getSocket() {
            return socket;
        }
    }
}
