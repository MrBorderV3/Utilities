package me.border.utilities.communication.tcp.v2.client;

import me.border.utilities.util.builder.Builder;
import me.border.utilities.util.builder.BuilderException;
import me.border.utilities.communication.tcp.core.TCPClient;

public class TCPClientBuilder implements Builder<TCPClient> {

    private String ip;
    private int port;
    private ServerConnectionRunnable runnable;

    public TCPClientBuilder(){
    }

    public TCPClientBuilder(int port){
        this.port = port;
    }

    public TCPClientBuilder setRunnable(ServerConnectionRunnable runnable){
        this.runnable = runnable;
        return this;
    }

    public TCPClientBuilder setPort(int port){
        this.port = port;
        return this;
    }

    public TCPClientBuilder setIp(String ip) {
        this.ip = ip;
        return this;
    }

    @Override
    public TCPClient build() throws BuilderException {
        if (port == 0){
            throw new BuilderException("Invalid port");
        }
        if (runnable == null){
            throw new BuilderException("Invalid runnable");
        }
        TCPServerConnectionBuilder builder = new TCPServerConnectionBuilder(runnable);
        return new TCPClientImpl(ip, port, builder);
    }
}
