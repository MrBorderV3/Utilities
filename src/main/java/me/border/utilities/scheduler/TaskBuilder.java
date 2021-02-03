package me.border.utilities.scheduler;

import me.border.utilities.interfaces.Builder;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * A {@link Builder} class to allow easier creation of {@link Timer} tasks, can be used both async on the predetermined {@code timer} thread
 * or create a new timer if chosen async just for that task.
 */
public class TaskBuilder implements Builder<Timer> {
    private TaskBuilder() { }

    public static TaskBuilder builder(){
        return new TaskBuilder();
    }

    private static final Timer timer = new Timer(true);

    private Type type;
    private TimerTask task;
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

    public TaskBuilder task(TimerTask task){
        this.task = task;
        return this;
    }

    public TaskBuilder type(Type type){
        this.type = type;
        return this;
    }

    public Timer build(){
        if (timerThread) {
            switch (type) {
                case REPEATING:
                    timer.scheduleAtFixedRate(task, after, every);
                    break;
                case SCHEDULED:
                    timer.schedule(task, after);
                    break;
            }
        } else {
            Timer timer = new Timer();
            switch (type) {
                case REPEATING:
                    timer.scheduleAtFixedRate(task, after, every);
                    break;
                case SCHEDULED:
                    timer.schedule(task, after);
                    break;
            }
            return timer;
        }

        return timer;
    }

    public enum Type {
        REPEATING, SCHEDULED
    }
}
