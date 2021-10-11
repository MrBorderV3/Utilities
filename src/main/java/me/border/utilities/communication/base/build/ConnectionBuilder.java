package me.border.utilities.communication.base.build;

import me.border.utilities.communication.base.Connection;
import me.border.utilities.communication.base.ConnectionRunnable;
import me.border.utilities.util.builder.Builder;

import java.net.Socket;

public interface ConnectionBuilder<T extends Connection> extends Builder<T> {

    ConnectionBuilder<T> setRunnable(ConnectionRunnable runnable);

    ConnectionBuilder<T> setSocket(Socket socket);
}
