package me.border.utilities.communication.base.connection;

import me.border.utilities.communication.base.Server;

/**
 * Dummy class so {@link Server} could only extend connections that implement this class and not any connection
 * So servers wont mistakenly implement client connections or otherwise
 *
 * @see Connection
 */
public interface ServerConnection extends Connection {
}
