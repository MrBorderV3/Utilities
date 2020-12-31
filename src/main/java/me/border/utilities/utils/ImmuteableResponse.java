package me.border.utilities.utils;

public class ImmuteableResponse<T> implements Response<T> {

    private final boolean answer;
    private boolean hasContext = false;
    private T context;

    ImmuteableResponse(boolean answer){
        this.answer = answer;
    }

    ImmuteableResponse(boolean answer, T context){
        this(answer);
        if (context == null)
            throw new NullPointerException("Response cannot have null context");
        hasContext = true;
        this.context = context;
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
