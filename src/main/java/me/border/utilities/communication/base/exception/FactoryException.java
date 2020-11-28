package me.border.utilities.communication.base.exception;

public class FactoryException extends Exception {

    public FactoryException(String message){
        super(message);
    }

    public FactoryException(Throwable cause){
        super(cause);
    }

    public FactoryException(String message, Throwable throwable){
        super(message, throwable);
    }
}
