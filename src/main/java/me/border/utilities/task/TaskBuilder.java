package me.border.utilities.task;

import me.border.utilities.interfaces.Builder;
import me.border.utilities.terminable.composite.CompositeTerminable;

import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * A {@link Builder} class to allow easier creation of {@link Task} tasks.
 * Can be used sync on the predetermined timer thread or async on a new timer thread.
 *
 * @see Timer
 */
public class TaskBuilder implements Builder<Task> {
    private TaskBuilder() { }

    public static TaskBuilder builder(){
        return new TaskBuilder();
    }

    private static final Task task = new Task(true);

    private Type type;

    private boolean bind = false;
    private Set<CompositeTerminable> compositeTerminables;

    private TimerTask timerTask;
    private long every;
    private long after;

    private boolean timerThread = true;

    public TaskBuilder async(){
        this.timerThread = false;
        return this;
    }

    public TaskBuilder sync(){
        this.timerThread = true;
        return this;
    }

    public TaskBuilder every(long every, TimeUnit tu){
        this.every = tu.toMillis(every);
        type = Type.REPEATING;
        return this;
    }

    public TaskBuilder after(long after, TimeUnit tu){
        this.after = tu.toMillis(after);
        type = Type.SCHEDULED;
        return this;
    }

    public TaskBuilder task(TimerTask timerTask){
        this.timerTask = timerTask;
        return this;
    }

    public TaskBuilder type(Type type){
        this.type = type;
        return this;
    }

    public TaskBuilder bind(CompositeTerminable compositeTerminable){
        if (!bind){
            bind = true;
            compositeTerminables = new HashSet<>();
        }
        compositeTerminables.add(compositeTerminable);
        return this;
    }

    public TaskBuilder unbind(CompositeTerminable compositeTerminable){
        if (!bind)
            return this;
        compositeTerminables.remove(compositeTerminable);
        if (compositeTerminables.isEmpty())
            bind = false;
        return this;
    }

    @Override
    public Task build(){
        if (timerThread) {
            if (bind){
                compositeTerminables.forEach(ct -> ct.bind(task));
            }
            switch (type) {
                case REPEATING:
                    task.scheduleAtFixedRate(timerTask, after, every);
                    break;
                case SCHEDULED:
                    task.schedule(timerTask, after);
                    break;
            }
        } else {
            Task task = new Task();
            if (bind){
                compositeTerminables.forEach(ct -> ct.bind(task));
            }
            switch (type) {
                case REPEATING:
                    task.scheduleAtFixedRate(timerTask, after, every);
                    break;
                case SCHEDULED:
                    task.schedule(timerTask, after);
                    break;
            }
            return task;
        }

        return task;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        REPEATING, SCHEDULED
    }
}
