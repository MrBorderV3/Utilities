package me.border.utilities.communication.tcp.impl.server;

import me.border.utilities.interfaces.Builder;
import me.border.utilities.communication.base.exception.BuilderException;
import me.border.utilities.communication.tcp.core.TCPServer;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServerBuilder implements Builder<TCPServer> {

    private int port;
    private ExecutorService pool;
    private ClientConnectionRunnable runnable;

    public TCPServerBuilder(){
    }

    public TCPServerBuilder(int port){
        this.port = port;
    }

    public TCPServerBuilder setRunnable(ClientConnectionRunnable runnable){
        this.runnable = runnable;
        return this;
    }

    public TCPServerBuilder setPort(int port){
        this.port = port;
        return this;
    }

    public TCPServerBuilder setPool(ExecutorService pool) {
        this.pool = pool;
        return this;
    }

    public TCPServerBuilder setPool(int pool) {
        this.pool = Executors.newFixedThreadPool(pool);
        return this;
    }

    @Override
    public TCPServer build() throws BuilderException {
        if (port == 0){
            throw new BuilderException("Invalid port");
        }
        if (runnable == null){
            throw new BuilderException("Invalid runnable");
        }
        if (pool == null){
            pool = Executors.newFixedThreadPool(1);
        }
        try {
            TCPClientConnectionFactory factory = new TCPClientConnectionFactory(runnable);
            return new TCPServerImpl(port, factory, pool);
        } catch (IOException e) {
            throw new BuilderException("An error has occurred during the construction of the server.", e);
        }
    }
}
