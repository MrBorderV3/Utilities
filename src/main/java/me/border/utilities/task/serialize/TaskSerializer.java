package me.border.utilities.task.serialize;

/**
 *
 */
public class TaskSerializer {

    /**
     * Create a {@link SerializableTask}
     *
     * @param after delay in milliseconds before the task does its first execution.
     * @param every time in milliseconds between successive task executions.
     * @param runs amount of times for the task to execute before resetting/closing.
     *
     * @return The matching {@link SerializableTask}
     */
    public static SerializableTask createSerializableTask(long after, long every, int runs){
        return new SerializableTask(after, every, runs);
    }
}
