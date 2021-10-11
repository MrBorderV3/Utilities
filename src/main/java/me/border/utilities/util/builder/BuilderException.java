package me.border.utilities.util.builder;

public class BuilderException extends RuntimeException {

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
