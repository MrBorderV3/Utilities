package me.border.utilities.communication.tcp.core.exception;

public class TCPStartupException extends Exception {

    public TCPStartupException(){
        super("An error has occurred while starting up this TCP protocol.");
    }

    public TCPStartupException(String message){
        super(message);
    }
}
