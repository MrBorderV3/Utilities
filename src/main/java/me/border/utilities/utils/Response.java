package me.border.utilities.utils;

public interface Response<T> {

    /**
     * Get the answer attached to the response
     *
     * @return The answer
     */
    boolean getAnswer();

    /**
     * Get if the response has a context
     *
     * @return Whether it has a response
     */
    boolean hasContext();

    /**
     * Get the response's context
     *
     * @return The context
     */
    T getContext();
}
