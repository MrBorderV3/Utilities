package me.border.utilities.example;

import me.border.utilities.file.watcher.FileEvent;
import me.border.utilities.file.watcher.FileListener;
import me.border.utilities.file.watcher.FileWatcher;
import me.border.utilities.utils.AsyncScheduler;

import java.io.File;

public class ExampleFileListener {

    private ExampleFileListener(File dir){
        FileWatcher watcher = new FileWatcher(dir);
        watcher.addListener(new FileListener() {
            @Override
            public void onCreated(FileEvent event) {
                File file = event.getFile();
            }

            @Override
            public void onModified(FileEvent event) {
                File file = event.getFile();
            }

            @Override
            public void onDeleted(FileEvent event) {
                File file = event.getFile();
            }
        });
        watcher.watch();
    }
}
