package me.border.utilities.communication.base.build;

import me.border.utilities.communication.base.Connection;
import me.border.utilities.communication.base.ConnectionRunnable;
import me.border.utilities.communication.base.exception.FactoryException;

import java.net.Socket;


public interface ConnectionFactory<T extends Connection> {

    T constructConn(Socket socket) throws FactoryException;

    T constructConn(Socket socket, ConnectionRunnable runnable) throws FactoryException;
}
