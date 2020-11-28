package me.border.utilities.communication.base.exception;

public class BuilderException extends Exception {

    public BuilderException(String message){
        super(message);
    }

    public BuilderException(Throwable cause){
        super(cause);
    }

    public BuilderException(String message, Throwable throwable){
        super(message, throwable);
    }
}
