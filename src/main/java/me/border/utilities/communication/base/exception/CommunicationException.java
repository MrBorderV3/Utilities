package me.border.utilities.communication.base.exception;

/**
 * The class {@code CommunicationException} is a form of an exception {@code Exception}
 * that can be thrown whilst communicating in a two-way Server-Client communication
 */
public class CommunicationException extends Exception {

    public CommunicationException(String message){
        super(message);
    }

    public CommunicationException(String message, Throwable throwable){
        super(message, throwable);
    }
}
