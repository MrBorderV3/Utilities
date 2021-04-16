package me.border.utilities.scheduler.sync;

import me.border.utilities.terminable.Terminable;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

/**
 * Extension for {@link Timer} for sync tasks with {@link Terminable} support.
 */
public class SyncTask extends Timer implements Terminable {
    private static final Map<Integer, SyncTask> activeTasks = new HashMap<>();
    private static int lastId = 0;

    private volatile boolean closed = false;
    private final int id;

    public SyncTask(){
        this(false);
    }

    public SyncTask(boolean isDaemon){
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

    public static Map<Integer, SyncTask> getActiveTasks() {
        return activeTasks;
    }
}
