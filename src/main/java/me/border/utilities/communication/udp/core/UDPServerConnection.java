package me.border.utilities.communication.udp.core;

import me.border.utilities.communication.base.connection.ServerConnection;

public interface UDPServerConnection extends ServerConnection {

    @Override
    void sendObject(Object object) throws UDPCommunicationException;
}
