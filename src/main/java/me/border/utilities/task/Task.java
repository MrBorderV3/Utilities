package me.border.utilities.task;

import me.border.utilities.terminable.Terminable;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

/**
 * Extension for {@link Timer} to add {@link Terminable} support.
 */
public class Task extends Timer implements Terminable {
    private static final Map<Integer, Task> activeTasks = new HashMap<>();
    private static int lastId = 0;

    private volatile boolean closed = false;
    private final int id;

    public Task(){
        this(false);
    }

    public Task(boolean isDaemon){
        super(isDaemon);
        id = lastId++;
        activeTasks.put(id, this);
    }

    @Override
    public void close() throws Exception {
        validate();
        activeTasks.remove(getId());
        super.cancel();
        closed = true;
    }

    @Override
    public boolean isClosed() {
        return closed;
    }

    @Override
    public void cancel() {
        closeSilently();
    }

    public int getId() {
        validate();
        return id;
    }

    public static Map<Integer, Task> getActiveTasks() {
        return activeTasks;
    }
}
