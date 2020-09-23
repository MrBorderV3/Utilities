package me.border.utilities.utils;

public class AsyncScheduler {

    public static void runTaskAsync(Runnable runnable){
        new Thread(runnable).start();
    }
}
