package me.border.utilities.example;

import me.border.utilities.communication.server.AbstractClientConnection;
import me.border.utilities.communication.server.AbstractServer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

public class ExampleServer extends AbstractServer {

    private ExampleServer() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        super(9090, 1);
        start();
    }

    @Override
    public void start() {
        super.start(ClientConnection.class);
    }

    public static class ClientConnection extends AbstractClientConnection {

        public ClientConnection(Socket clientSocket) throws IOException {
            super(clientSocket);
        }

        @Override
        public void run() {
        }
    }
}
