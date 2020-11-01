package me.border.utilities.communication.tcp.core;

import me.border.utilities.communication.base.exception.CommunicationException;

import java.net.Socket;

/**
 * The class {@code TCPCommunicationException} is a TCP implementation of the {@code CommunicationException} class.
 *
 * @see CommunicationException
 */
public class TCPCommunicationException extends CommunicationException {

    public TCPCommunicationException(Socket socket, Throwable throwable){
        super("An error has occurred in the communication between the sockets!\nSocket: " + socket.toString(), throwable);
    }

    public TCPCommunicationException(Socket socket){
        super("An error has occurred in the communication between the sockets!\nSocket: " + socket.toString());
    }

    public TCPCommunicationException(Socket socket, String message){
        super(message + "\nSocket: " + socket.toString());
    }
}
