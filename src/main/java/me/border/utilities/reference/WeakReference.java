package me.border.utilities.reference;

public class WeakReference<T> {

    private java.lang.ref.WeakReference<T> ref;

    public WeakReference() { }

    public WeakReference(T ref){
        this.ref = new java.lang.ref.WeakReference<>(ref);
    }

    public void set(T ref){
        clear();
        this.ref = new java.lang.ref.WeakReference<>(ref);
    }

    public T get(){
        return ref.get();
    }

    public void clear() {
        if (this.ref != null)
            ref.clear();
    }

    public boolean isEmpty(){
        return ref == null;
    }
}
