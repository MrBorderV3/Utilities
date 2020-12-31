package me.border.utilities.scheduler;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class SchedulerBuilder {

    private static final Timer timer = new Timer(true);

    private Type type;
    private TimerTask task;
    private long every;
    private long after;
    private boolean timerThread = true;

    public SchedulerBuilder async(){
        this.timerThread = false;
        return this;
    }

    public SchedulerBuilder sync(){
        this.timerThread = true;
        return this;
    }

    public SchedulerBuilder every(long every, TimeUnit tu){
        this.every = tu.toMillis(every);
        type = Type.REPEATING;
        return this;
    }

    public SchedulerBuilder after(long after, TimeUnit tu){
        this.after = tu.toMillis(after);
        type = Type.SCHEDULED;
        return this;
    }

    public SchedulerBuilder task(TimerTask task){
        this.task = task;
        return this;
    }

    public SchedulerBuilder type(Type type){
        this.type = type;
        return this;
    }

    public Timer run(){
        if (timerThread) {
            switch (type) {
                case REPEATING:
                    timer.scheduleAtFixedRate(task, after, every);
                case SCHEDULED:
                    timer.schedule(task, after);
            }
        } else {
            Timer timer = new Timer(true);
            switch (type) {
                case REPEATING:
                    timer.scheduleAtFixedRate(task, after, every);
                case SCHEDULED:
                    timer.schedule(task, after);
            }
            return timer;
        }

        return timer;
    }

    public enum Type {
        REPEATING, SCHEDULED
    }
}
