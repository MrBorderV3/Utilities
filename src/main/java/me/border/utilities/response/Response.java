package me.border.utilities.response;

public interface Response<T> {

    static <T> ImmuteableResponse<T> createImmuteable(boolean answer){
        return new ImmuteableResponse<>(answer);
    }

    static <T> ImmuteableResponse<T> createImmuteable(boolean answer, T context){
        return new ImmuteableResponse<>(answer, context);
    }

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

    /**
     * Set the response's answer
     */
    void setAnswer(boolean b);

    /**
     * Set the response's context
     */
    void setContext(T t);
}
