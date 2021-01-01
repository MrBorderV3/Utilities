package me.border.utilities.response;

import java.util.Objects;

public class ImmuteableResponse<T> implements Response<T> {

    private final boolean answer;
    private boolean hasContext = false;
    private T context;

    ImmuteableResponse(boolean answer){
        this.answer = answer;
    }

    ImmuteableResponse(boolean answer, T context){
        this(answer);
        Objects.requireNonNull(context, "Context cannot be null");
        this.context = context;
        hasContext = true;
    }

    @Override
    public boolean getAnswer() {
        return answer;
    }

    @Override
    public boolean hasContext() {
        return hasContext;
    }

    @Override
    public T getContext() {
        return context;
    }

    @Override
    public void setAnswer(boolean b) {
        throw new UnsupportedOperationException("This response is immuteable and cannot be set");
    }

    @Override
    public void setContext(T t) {
        throw new UnsupportedOperationException("This response is immuteable and cannot be set");
    }
}
