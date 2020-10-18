package me.border.utilities.utils.ref;

import java.lang.ref.WeakReference;

public class MuteableWeakReference<T> {

    private WeakReference<T> ref;

    public MuteableWeakReference() { }

    public MuteableWeakReference(T ref){
        this.ref = new WeakReference<>(ref);
    }

    public void set(T ref){
        clear();
        this.ref = new WeakReference<>(ref);
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
