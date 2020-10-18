package me.border.utilities.utils.ref;

public class StrongReference<T>{

    private T ref;

    public StrongReference(){ }

    public StrongReference(T ref){
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
