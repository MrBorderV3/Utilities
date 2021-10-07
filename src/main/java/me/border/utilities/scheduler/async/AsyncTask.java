package me.border.utilities.scheduler.async;

import me.border.utilities.terminable.Terminable;

import java.util.concurrent.ScheduledFuture;

public abstract class AsyncTask implements Runnable, Terminable {

    private ScheduledFuture<?> internalFuture;

    protected void setInternalFuture(ScheduledFuture<?> scheduledFuture){
        this.internalFuture = scheduledFuture;
    }

    protected ScheduledFuture<?> getInternalFuture() {
        return internalFuture;
    }

    @Override
    public void close() throws Exception {
        validate();
        AsyncTaskBuilder.remove(this);
    }

    @Override
    public boolean isClosed() {
        return internalFuture.isCancelled();
    }

}
