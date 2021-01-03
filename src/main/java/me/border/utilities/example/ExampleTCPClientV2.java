package me.border.utilities.example;

import me.border.utilities.communication.base.exception.BuilderException;
import me.border.utilities.communication.base.exception.CommunicationException;
import me.border.utilities.communication.tcp.core.TCPClient;
import me.border.utilities.communication.tcp.core.exception.TCPStartupException;
import me.border.utilities.communication.tcp.v2.client.ServerConnectionRunnable;
import me.border.utilities.communication.tcp.v2.client.TCPClientBuilder;

import java.io.IOException;

public class ExampleTCPClientV2 {

    private TCPClient client;

    private ExampleTCPClientV2(String ip, int port) {
        try {
            client = new TCPClientBuilder().setIp(ip).setPort(port).setRunnable(new ServerConnectionRunnable() {
                @Override
                public void run() {
                    try {
                        getConnection().sendObject("Example");
                    } catch (CommunicationException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).build();
        } catch (BuilderException ex) {
            ex.printStackTrace();
        }
    }

    public void start() throws TCPStartupException {
        client.start();
    }
}
