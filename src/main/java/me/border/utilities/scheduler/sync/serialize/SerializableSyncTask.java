package me.border.utilities.scheduler.sync.serialize;

import me.border.utilities.scheduler.sync.SyncTask;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Wrapped Extension for {@link Timer} to add {@link Serializable} support.
 *
 * @see SyncTaskSerializer
 */
public class SerializableSyncTask implements Serializable {

    private static transient SyncTask globalSyncTask = new SyncTask(true);

    private final long after;
    private final long every;
    private final int runs;
    private final boolean ownTask;

    /**
     * Create a new SerializableTask with {@link #ownTask} set to false.
     *
     * @param after After how much time it should have its first run in milliseconds
     * @param every How much time it should wait between runs in milliseconds
     * @param runs How much times the task should execute
     *
     * @see #SerializableSyncTask(long, long, int, boolean)
     */
    protected SerializableSyncTask(long after, long every, int runs){
        this(after, every, runs, false);
    }

    /**
     * Create a new SerializableTask
     *
     * @param after After how much time it should have its first run in milliseconds
     * @param every How much time it should wait between runs in milliseconds
     * @param runs How much times the task should execute
     * @param ownTask Whether it should create its own {@link SyncTask} for it or use the global one
     */
    protected SerializableSyncTask(long after, long every, int runs, boolean ownTask){
        this.after = after;
        this.every = every;
        this.runs = runs;
        this.ownTask = ownTask;
    }

    /**
     * Starts the internal {@link SyncTask}
     *
     * @param runnable The runnable to run every time the task ticks.
     * @param endRunnable The runnable to run when the task resets/closes.
     * @param cancel Whether to cancel the task after the runs amount is reached.
     *        if {@code true} it will cancel, if {@code false} it will not.
     *
     * @return The {@link SyncTask} it used to schedule the {@link Runnable}s
     */
    public SyncTask start(Runnable runnable, Runnable endRunnable, boolean cancel){
        SyncTask syncTask;
        if (ownTask) {
            syncTask = new SyncTask(true);
        } else {
            if (globalSyncTask == null){
                globalSyncTask = new SyncTask(true);
            }

            syncTask = globalSyncTask;
        }
        syncTask.validate();

        syncTask.scheduleAtFixedRate(new TimerTask() {
            private int runsLeft = runs;

            @Override
            public void run() {
                runsLeft -= 1;
                runnable.run();

                if (runsLeft == 0){
                    endRunnable.run();
                    if (cancel){
                        cancel();
                        return;
                    }
                    runsLeft = runs;
                }
            }
        }, after, every);

        return syncTask;
    }

    public SyncTask getGlobalTask() {
        return globalSyncTask;
    }
}
