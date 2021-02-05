package me.border.utilities.scheduler;

import me.border.utilities.terminable.Terminable;

import java.util.Timer;

/**
 * Extension for {@link Timer} to add {@link Terminable} support.
 */
public class Task extends Timer implements Terminable {

    private volatile boolean closed = false;

    public Task(){
        super();
    }

    public Task(boolean isDaemon){
        super(isDaemon);
    }

    @Override
    public void close() throws Exception {
        validate();
        cancel();
        closed = true;
    }

    @Override
    public boolean isClosed() {
        return closed;
    }
}
