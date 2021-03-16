package me.border.utilities.task.serialize;

import me.border.utilities.task.Task;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Wrapped Extension for {@link Timer} to add {@link Serializable} support.
 *
 * @see TaskSerializer
 */
public class SerializableTask implements Serializable {

    private static transient Task globalTask = new Task(true);

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
     * @see #SerializableTask(long, long, int, boolean)
     */
    protected SerializableTask(long after, long every, int runs){
        this(after, every, runs, false);
    }

    /**
     * Create a new SerializableTask
     *
     * @param after After how much time it should have its first run in milliseconds
     * @param every How much time it should wait between runs in milliseconds
     * @param runs How much times the task should execute
     * @param ownTask Whether it should create its own {@link Task} for it or use the global one
     */
    protected SerializableTask(long after, long every, int runs, boolean ownTask){
        this.after = after;
        this.every = every;
        this.runs = runs;
        this.ownTask = ownTask;
    }

    /**
     * Starts the internal {@link Task}
     *
     * @param runnable The runnable to run every time the task ticks.
     * @param endRunnable The runnable to run when the task resets/closes.
     * @param cancel Whether to cancel the task after the runs amount is reached.
     *        if {@code true} it will cancel, if {@code false} it will not.
     *
     * @return The {@link Task} it used to schedule the {@link Runnable}s
     */
    public Task start(Runnable runnable, Runnable endRunnable, boolean cancel){
        Task task;
        if (ownTask) {
            task = new Task(true);
        } else {
            if (globalTask == null){
                globalTask = new Task(true);
            }

            task = globalTask;
        }
        task.validate();

        task.scheduleAtFixedRate(new TimerTask() {
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

        return task;
    }

    public Task getGlobalTask() {
        return globalTask;
    }
}
