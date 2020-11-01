package me.border.utilities.communication.udp.core.connection;

import me.border.utilities.communication.base.connection.ClientConnection;
import me.border.utilities.communication.udp.core.UDPCommunicationException;

public interface UDPClientConnection extends ClientConnection {

    @Override
    void sendObject(Object object) throws UDPCommunicationException;
}
