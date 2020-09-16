package me.border.utilities.utils;

import java.util.ArrayList;

public class ArrayResponse<T> implements Response<T> {


    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    private boolean answer;
    private boolean hasContext = false;
    private transient Object[] context;

    public ArrayResponse(boolean answer){
        this.answer = answer;
    }

    public ArrayResponse(boolean answer, T context){
        this(answer);
        if (context == null)
            throw new NullPointerException("Response cannot have null context");
        hasContext = true;
        this.context = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
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
        return (T) context[0];
    }

    public T get(int num){
        if (num >= context.length){
            throw new IllegalArgumentException("Array size is " + context.length + " and the requested number is " + num);
        }
        return (T) context[num];
    }

    @Override
    public void setAnswer(boolean b) {
        this.answer = b;
    }

    @Override
    public void setContext(T t) {
        if (t.getClass().isArray()) {
            this.context = (Object[]) t;
        } else {
            this.context[0] = t;
        }
    }

    public void set(T t, int num){
        if (num >= context.length){
            throw new IllegalArgumentException("Array size is " + context.length + " and the requested number is " + num);
        }
        context[num] = t;
    }

    public void add(T t){
        int newLength = context.length + 1;
        context[newLength ] = t;
    }
}
