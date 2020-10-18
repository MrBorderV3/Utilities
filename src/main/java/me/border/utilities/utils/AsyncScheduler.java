package me.border.utilities.utils;

public class AsyncScheduler {

    public static void runTaskAsync(Runnable runnable){
        new Thread(runnable).start();
    }

    public static void runTaskAsyncDaemon(Runnable runnable){
        Thread daemonThread = new Thread(runnable);
        daemonThread.setDaemon(true);
        daemonThread.start();
    }
}
