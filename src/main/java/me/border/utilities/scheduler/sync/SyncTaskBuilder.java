package me.border.utilities.scheduler.sync;

import me.border.utilities.util.builder.Builder;
import me.border.utilities.terminable.composite.CompositeTerminable;

import java.util.HashSet;
import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * A {@link Builder} class to allow easier creation of {@link SyncTask} tasks.
 *
 * @see SyncTask
 */
public class SyncTaskBuilder implements Builder<SyncTask> {

    public static SyncTaskBuilder builder(){
        return new SyncTaskBuilder();
    }

    private static final SyncTask SYNC_TASK = new SyncTask(true);

    private Type type;

    private boolean bind = false;
    private Set<CompositeTerminable> compositeTerminables;

    private TimerTask timerTask;
    private long every;
    private long after;

    private SyncTaskBuilder() { }

    public SyncTaskBuilder every(long every, TimeUnit tu){
        this.every = tu.toMillis(every);
        type = Type.REPEATING;
        return this;
    }

    public SyncTaskBuilder after(long after, TimeUnit tu){
        this.after = tu.toMillis(after);
        type = Type.SCHEDULED;
        return this;
    }

    public SyncTaskBuilder task(TimerTask timerTask){
        this.timerTask = timerTask;
        return this;
    }

    public SyncTaskBuilder type(Type type){
        this.type = type;
        return this;
    }

    public SyncTaskBuilder bind(CompositeTerminable compositeTerminable){
        if (!bind){
            bind = true;
            compositeTerminables = new HashSet<>();
        }

        compositeTerminables.add(compositeTerminable);
        return this;
    }

    public SyncTaskBuilder unbind(CompositeTerminable compositeTerminable){
        if (!bind)
            return this;

        compositeTerminables.remove(compositeTerminable);
        if (compositeTerminables.isEmpty())
            bind = false;

        return this;
    }

    @Override
    public SyncTask build() {
        if (bind) {
            compositeTerminables.forEach(ct -> ct.bind(SYNC_TASK));
        }

        switch (type) {
            case REPEATING:
                SYNC_TASK.scheduleAtFixedRate(timerTask, after, every);
                break;
            case SCHEDULED:
                SYNC_TASK.schedule(timerTask, after);
                break;
        }

        return SYNC_TASK;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        REPEATING, SCHEDULED
    }
}
