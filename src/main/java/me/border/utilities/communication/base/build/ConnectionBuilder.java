package me.border.utilities.communication.base.build;

import me.border.utilities.communication.base.Connection;
import me.border.utilities.communication.base.ConnectionRunnable;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.function.BiConsumer;

public interface ConnectionBuilder<T extends Connection> extends Builder<T> {

    ConnectionBuilder<T> setRunnable(ConnectionRunnable runnable);

    ConnectionBuilder<T> setSocket(Socket socket);
}
