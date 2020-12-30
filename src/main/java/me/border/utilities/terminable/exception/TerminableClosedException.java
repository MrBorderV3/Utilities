package me.border.utilities.terminable.exception;

public class TerminableClosedException extends Exception {

    public TerminableClosedException(){
        super("This terminable is closed and cannot be used.");
    }
}
