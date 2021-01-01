package me.border.utilities.reference;

public class Reference<T>{

    private T ref;

    public Reference(){ }

    public Reference(T ref){
        set(ref);
    }

    public void set(T ref){
        this.ref = ref;
    }

    public T get(){
        return ref;
    }

    public T remove(){
        T ref = this.ref;
        this.ref = null;
        return ref;
    }

    public boolean isEmpty(){
        return ref == null;
    }
}
