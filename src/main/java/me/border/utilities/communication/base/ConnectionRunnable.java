package me.border.utilities.communication.base;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public interface ConnectionRunnable extends Runnable {

    void setConnection(Connection connection) throws Exception;

    void setOut(OutputStream out);

    void setIn(InputStream in);

    void setSocket(Socket socket);

    Connection getConnection();

    OutputStream getOut();

    InputStream getIn();

    Socket getSocket();

    void close() throws IOException;
}
