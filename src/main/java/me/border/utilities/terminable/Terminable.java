package me.border.utilities.terminable;

public interface Terminable extends AutoCloseable {
    void close() throws Exception;

    boolean isClosed();

    default void closeSilently(){
        try {
            close();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
