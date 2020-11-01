package me.border.utilities.communication.udp.core.connection;

import me.border.utilities.communication.base.connection.ServerConnection;
import me.border.utilities.communication.udp.core.UDPCommunicationException;

public interface UDPServerConnection extends ServerConnection {

    @Override
    void sendObject(Object object) throws UDPCommunicationException;
}
