package me.border.utilities.scheduler.sync.serialize;

/**
 *
 */
public class SyncTaskSerializer {

    /**
     * Create a {@link SerializableSyncTask}
     *
     * @param after delay in milliseconds before the task does its first execution.
     * @param every time in milliseconds between successive task executions.
     * @param runs amount of times for the task to execute before resetting/closing.
     *
     * @return The matching {@link SerializableSyncTask}
     */
    public static SerializableSyncTask createSerializableTask(long after, long every, int runs){
        return new SerializableSyncTask(after, every, runs);
    }
}
