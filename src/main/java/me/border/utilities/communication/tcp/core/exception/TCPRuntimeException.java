package me.border.utilities.communication.tcp.core.exception;

public class TCPRuntimeException extends RuntimeException {

    public TCPRuntimeException(Throwable cause){
        super("An error has occurred during the runtime of this TCP protocol.", cause);
    }

    public TCPRuntimeException(String message, Throwable cause){
        super(message, cause);
    }
}
