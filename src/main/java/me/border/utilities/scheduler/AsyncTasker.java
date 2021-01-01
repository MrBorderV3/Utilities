package me.border.utilities.scheduler;

public class AsyncTasker {

    /**
     * Run a task asynchronously on a new thread
     *
     * @param runnable The task
     * @return The thread
     */
    public static Thread runTaskAsync(Runnable runnable){
        Thread thread = new Thread(runnable);
        thread.start();
        return thread;
    }

    /**
     * Run a task asynchronously on a new daemon thread
     *
     * @param runnable The task
     * @return The thread
     */
    public static Thread runTaskAsyncDaemon(Runnable runnable){
        Thread daemonThread = new Thread(runnable);
        daemonThread.setDaemon(true);
        daemonThread.start();

        return daemonThread;
    }
}
