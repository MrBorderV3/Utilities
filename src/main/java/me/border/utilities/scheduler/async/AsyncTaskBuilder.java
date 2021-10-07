package me.border.utilities.scheduler.async;

import me.border.utilities.interfaces.Builder;
import me.border.utilities.terminable.composite.CompositeTerminable;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

public class AsyncTaskBuilder implements Builder<AsyncTask> {

    public static AsyncTaskBuilder builder(){
        return new AsyncTaskBuilder();
    }

    private static final ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(1);

    private Type type;

    private boolean bind = false;
    private Set<CompositeTerminable> compositeTerminables;

    private AsyncTask asyncTask;
    private long every;
    private long after;

    private AsyncTaskBuilder() { }

    public AsyncTaskBuilder every(long every, TimeUnit tu){
        this.every = tu.toMillis(every);
        type = AsyncTaskBuilder.Type.REPEATING;
        return this;
    }

    public AsyncTaskBuilder after(long after, TimeUnit tu){
        this.after = tu.toMillis(after);
        type = AsyncTaskBuilder.Type.SCHEDULED;
        return this;
    }

    public AsyncTaskBuilder task(AsyncTask asyncTask){
        this.asyncTask = asyncTask;
        return this;
    }

    public AsyncTaskBuilder type(AsyncTaskBuilder.Type type){
        this.type = type;
        return this;
    }

    public AsyncTaskBuilder bind(CompositeTerminable compositeTerminable){
        if (!bind){
            bind = true;
            compositeTerminables = new HashSet<>();
        }
        compositeTerminables.add(compositeTerminable);
        return this;
    }

    public AsyncTaskBuilder unbind(CompositeTerminable compositeTerminable){
        if (!bind)
            return this;
        compositeTerminables.remove(compositeTerminable);
        if (compositeTerminables.isEmpty())
            bind = false;
        return this;
    }

    @Override
    public AsyncTask build() {
        if (bind) {
            compositeTerminables.forEach(ct -> ct.bind(asyncTask));
        }

        switch (type) {
            case REPEATING:
                asyncTask.setInternalFuture(pool.scheduleAtFixedRate(asyncTask, after, every, TimeUnit.MILLISECONDS));
                break;
            case SCHEDULED:
                asyncTask.setInternalFuture(pool.schedule(asyncTask, after, TimeUnit.MILLISECONDS));
                break;
        }

        return asyncTask;
    }

    public Type getType() {
        return type;
    }

    static void remove(AsyncTask asyncTask){
        asyncTask.getInternalFuture().cancel(true);
        pool.remove(asyncTask);
        pool.purge();
    }

    public enum Type {
        REPEATING, SCHEDULED
    }
}
