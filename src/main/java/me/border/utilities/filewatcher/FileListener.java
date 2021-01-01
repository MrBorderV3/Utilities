package me.border.utilities.filewatcher;

import java.util.EventListener;

/**
 * Function interface to listen for events whenever a file is created/modified/deleted at a {@link FileWatcher} initialized directory
 * @see FileWatcher
 */
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
