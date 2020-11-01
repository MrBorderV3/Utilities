package me.border.utilities.communication.udp.core;

import me.border.utilities.communication.base.exception.CommunicationException;

import java.net.DatagramSocket;

/**
 * The class {@code UDPCommunicationException} is a UDP implementation of the {@code CommunicationException} class.
 *
 * @see CommunicationException
 */
public class UDPCommunicationException extends CommunicationException {

    public UDPCommunicationException(DatagramSocket socket, Throwable throwable){
        super("An error has occurred in the communication between the sockets!\nSocket: " + socket.toString(), throwable);
    }

    public UDPCommunicationException(DatagramSocket socket){
        super("An error has occurred in the communication between the sockets!\nSocket: " + socket.toString());
    }

    public UDPCommunicationException(DatagramSocket socket, String message){
        super(message + "\nSocket: " + socket.toString());
    }
}
