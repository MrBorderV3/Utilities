package me.border.utilities.scheduler.async;

import me.border.utilities.terminable.Terminable;

public interface AsyncTask extends Runnable, Terminable {

    @Override
    default void close() throws Exception {
        validate();
        AsyncTaskBuilder.remove(this);
    }

    @Override
    default boolean isClosed() {
        return AsyncTaskBuilder.exists(this);
    }
}
