package me.border.utilities.communication.tcp.impl.client;

import me.border.utilities.communication.base.ConnectionRunnable;
import me.border.utilities.communication.base.exception.BuilderException;
import me.border.utilities.communication.base.build.ConnectionBuilder;
import me.border.utilities.communication.tcp.core.TCPClient;
import me.border.utilities.communication.tcp.core.base.TCPServerConnection;

import java.io.IOException;
import java.net.Socket;

public class TCPServerConnectionBuilder implements ConnectionBuilder<TCPServerConnection> {

    private TCPClient client;
    private Socket socket;
    private ConnectionRunnable runnable;

    public TCPServerConnectionBuilder(){

    }

    public TCPServerConnectionBuilder(ServerConnectionRunnable runnable){
        this.runnable = runnable;
    }

    public void setClient(TCPClient client) {
        this.client = client;
    }

    @Override
    public TCPServerConnectionBuilder setSocket(Socket socket) {
        this.socket = socket;
        return this;
    }

    @Override
    public TCPServerConnectionBuilder setRunnable(ConnectionRunnable runnable) {
        this.runnable = runnable;
        return this;
    }

    @Override
    public TCPServerConnection build() throws BuilderException {
        if (socket == null){
            throw new BuilderException("Invalid socket");
        }
        if (runnable == null){
            throw new BuilderException("Invalid runnable");
        }
        if (client == null){
            throw new BuilderException("Invalid Client");
        }
        if (!(runnable instanceof ServerConnectionRunnable)){
            throw new BuilderException("Invalid runnable");
        }
        try {
            return new TCPServerConnectionImpl(client, socket, (ServerConnectionRunnable) runnable);
        } catch (IOException ex){
            throw new BuilderException(ex);
        }
    }
}
