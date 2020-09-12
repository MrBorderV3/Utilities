package me.border.utilities.utils;

public class ImmuteableResponse<T> implements Response<T> {

    private boolean answer;
    private boolean hasContext = false;
    private T context;

    public ImmuteableResponse(boolean answer){
        this.answer = answer;
    }

    public ImmuteableResponse(boolean answer, T context){
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
}
