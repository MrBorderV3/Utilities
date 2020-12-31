package me.border.utilities.terminable;

/**
 * An extension of {@link AutoCloseable}
 */
public interface Terminable extends AutoCloseable {

    /**
     * Closes this resources
     *
     * @throws Exception Any exceptions that might occur during the resource closure.
     * @see AutoCloseable#close()
     */
    void close() throws Exception;

    /**
     * Gets if the object represented by this instance is already closed.
     *
     * @return true if this terminable is closed.
     */
    boolean isClosed();

    /**
     * Closes this resource, and prints the exception if one is thrown.
     */
    default void closeSilently(){
        try {
            close();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
