package me.border.utilities.terminable.exception;

public class TerminableClosedException extends RuntimeException {

    public TerminableClosedException(){
        super("This terminable is closed and cannot be used.");
    }

    public TerminableClosedException(String message){
        super(message);
    }
}
