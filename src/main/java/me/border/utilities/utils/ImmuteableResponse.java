package me.border.utilities.utils;

public class ImmuteableResponse<T> implements Response<T> {

    private boolean answer;
    private T context;

    public ImmuteableResponse(boolean answer){
        this.answer = answer;
    }

    public ImmuteableResponse(boolean answer, T context){
        this(answer);
        this.context = context;
    }

    @Override
    public boolean getAnswer() {
        return answer;
    }

    @Override
    public T getContext() {
        return context;
    }
}
