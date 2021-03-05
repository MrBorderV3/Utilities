package me.border.utilities.task.serialize;

import me.border.utilities.task.Task;
import me.border.utilities.terminable.Terminable;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Wrapped Extension for {@link Timer} to add {@link Serializable} support.
 *
 * @see TaskSerializer
 */
public class SerializableTask implements Serializable, Terminable {

    private final transient Task task = new Task();

    private final long after;
    private final long every;
    private final int runs;

    protected SerializableTask(long after, long every, int runs){
        this.after = after;
        this.every = every;
        this.runs = runs;
    }

    /**
     * Starts the internal {@link Task}
     *
     * @param runnable The runnable to run every time the task ticks.
     * @param endRunnable The runnable to run when the task resets/closes.
     * @param close Whether to close the task after the runs amount is reached.
     *        if {@code true} it will close, if {@code false} it will not.
     */
    public void start(Runnable runnable, Runnable endRunnable, boolean close){
        getBase().validate();
        task.scheduleAtFixedRate(new TimerTask() {
            private int runsLeft = runs;

            @Override
            public void run() {
                runsLeft -= 1;
                runnable.run();

                if (runsLeft == 0){
                    endRunnable.run();
                    if (close){
                        getBase().closeSilently();
                        return;
                    }
                    runsLeft = runs;
                }
            }
        }, after, every);
    }

    public Task getBase() {
        return task;
    }

    @Override
    public void close() throws Exception {
        getBase().close();
    }

    @Override
    public boolean isClosed() {
        return getBase().isClosed();
    }
}
