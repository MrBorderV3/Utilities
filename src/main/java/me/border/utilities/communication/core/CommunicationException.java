package me.border.utilities.communication.core;

import java.net.Socket;

/**
 * The class {@code CommunicationException} is a form of an exception {@code Exception} that can be thrown whilst communicating in a two-way Server-Client communication
 * @see me.border.utilities.communication.server.AbstractServer
 * @see me.border.utilities.communication.client.AbstractClient
 */
public class CommunicationException extends Exception {

    public CommunicationException(Socket socket, Throwable throwable){
        super("An error has occurred in the communication between the sockets!\nSocket: " + socket.toString(), throwable);
    }

    public CommunicationException(Socket socket){
        super("An error has occurred in the communication between the sockets!\nSocket: " + socket.toString());
    }

    public CommunicationException(Socket socket, String str){
        super(str + "\nSocket: " + socket.toString());
    }
}
