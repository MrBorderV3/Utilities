package me.border.utilities.file.watcher;

import java.util.EventListener;

public interface FileListener extends EventListener {

    /**
     * Fired upon creation of a file in the watched directory.
     *
     * @param event Event object.
     */
    void onCreated(FileEvent event);

    /**
     * Fired upon modification of a file in the watched directory.
     *
     * @param event Event object.
     */
    void onModified(FileEvent event);

    /**
     * Fired upon deletion of a file in the watched directory.
     *
     * @param event Event object.
     */
    void onDeleted(FileEvent event);
}
