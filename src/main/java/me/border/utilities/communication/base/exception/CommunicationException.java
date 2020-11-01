package me.border.utilities.communication.base.exception;

import me.border.utilities.communication.base.Client;
import me.border.utilities.communication.base.Server;

/**
 * The class {@code CommunicationException} is a form of an exception {@code Exception}
 * that can be thrown whilst communicating in a two-way Server-Client communication
 * @see Server
 * @see Client
 */
public class CommunicationException extends Exception {

    public CommunicationException(String message){
        super(message);
    }

    public CommunicationException(String message, Throwable throwable){
        super(message, throwable);
    }
}
