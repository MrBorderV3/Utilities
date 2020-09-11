package me.border.utilities.utils;

public class Response<T> {

    private boolean answer;
    private T vault;

    public Response(boolean answer){
        this.answer = answer;
    }

    public Response(boolean answer, T vault){
        this(answer);
        this.vault = vault;
    }

    public boolean getAnswer() {
        return answer;
    }

    public T getVault() {
        return vault;
    }
}
