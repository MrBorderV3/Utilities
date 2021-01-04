package me.border.utilities.filewatcher;

import me.border.utilities.terminable.Terminable;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * Represents a watcher that watches over a given directory and executes given {@link FileListener}s on the files.
 * The watcher notifies the listeners every Modification/Deletion/Creation of a file in the given directory.
 *
 * The watcher will create a new {@link FileWatcher} for a directory that is created inside the directory its watching
 * and attach that watcher to itself.
 *
 * The {@link #close()} method will close this watcher and all attached {@link FileWatcher}s and {@link WatchService}s.
 */
public class FileWatcher implements Runnable, Terminable {
    private List<FileListener> listeners = new ArrayList<>();
    private final File dir;

    private final List<WatchService> watchServices = new ArrayList<>();
    // All the watchers that were started as a result from this watcher
    private final List<FileWatcher> watchers = new ArrayList<>();

    private volatile boolean closed = false;
    private volatile boolean stopped = false;

    /**
     * Initialize a new watcher with the given directory as its directory to watch.
     *
     * @param dir The directory to watch
     */
    public FileWatcher(File dir) {
        this.dir = dir;
    }

    /**
     * Begin watching the specified directory or unpause the current thread that is watching the directory if it was stopped before
     */
    public void watch() {
        validate();
        if (dir.exists()) {
            if (stopped) {
                stopped = false;
            } else {
                Thread thread = new Thread(this);
                thread.setDaemon(true);
                thread.start();
            }
        }
    }

    @Override
    public void run() {
        while (!closed) {
            if (!stopped) {
                try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
                    Path path = Paths.get(dir.getAbsolutePath());
                    path.register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
                    watchServices.add(watchService);
                    boolean poll = true;
                    while (poll) {
                        poll = pollEvents(watchService);
                    }
                } catch (IOException | InterruptedException | ClosedWatchServiceException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * Poll all events in given {@link WatchService}.
     *
     * @param watchService The watch service
     * @return {@link WatchKey#reset()}
     * @throws InterruptedException if interrupted while waiting
     */
    private boolean pollEvents(WatchService watchService) throws InterruptedException {
        WatchKey key = watchService.take();
        Path path = (Path) key.watchable();
        for (WatchEvent<?> event : key.pollEvents()) {
            notifyListeners(event.kind(), path.resolve((Path) event.context()).toFile());
        }
        return key.reset();
    }

    /**
     * Notify all attached listeners of an event.
     *
     * @param kind The type of the event.
     * @param file The source of the event.
     */
    private void notifyListeners(WatchEvent.Kind<?> kind, File file) {
        FileEvent event = new FileEvent(file);
        if (kind == ENTRY_CREATE) {
            for (FileListener listener : listeners) {
                listener.onCreated(event);
            }
            if (file.isDirectory()) {
                FileWatcher fileWatcher = new FileWatcher(file);
                fileWatcher.setListeners(listeners);
                fileWatcher.watch();
                watchers.add(fileWatcher);
            }
        } else if (kind == ENTRY_MODIFY) {
            for (FileListener listener : listeners) {
                listener.onModified(event);
            }
        } else if (kind == ENTRY_DELETE) {
            for (FileListener listener : listeners) {
                listener.onDeleted(event);
            }
        }
    }

    /**
     * Attach a {@link FileListener} to the watcher
     *
     * @param listener The listener to add
     */
    public void addListener(FileListener listener) {
        validate();
        listeners.add(listener);
    }

    /**
     * Remove a {@link FileListener} to the watcher
     *
     * @param listener The listener to remove
     */
    public void removeListener(FileListener listener) {
        validate();
        listeners.remove(listener);
    }

    /**
     * Get all the attached {@link FileListener}s
     *
     * @return A list of the listeners.
     */
    public List<FileListener> getListeners() {
        validate();
        return listeners;
    }

    /**
     * Set the watcher's {@link FileListener}
     *
     * This will override all the existing listeners and clear them.
     *
     * @param listeners The listeners to set.
     */
    public void setListeners(List<FileListener> listeners) {
        validate();
        this.listeners = listeners;
    }

    public List<FileWatcher> getWatchers() {
        return Collections.unmodifiableList(watchers);
    }

    /**
     * Stop this watcher and clear its listeners.
     *
     * Unlike {@link #close()} this method does not clear the attached {@link FileWatcher}s and {@link WatchService}s.
     */
    public void stop() {
        validate();
        stopped = true;
        listeners.clear();
    }

    /**
     * Stop and Close this watcher and clear all attached {@link FileWatcher}s and {@link WatchService}s.
     */
    @Override
    public void close() throws Exception {
        stop();
        closed = true;
        for (int i = 0; i < watchServices.size(); i++){
            watchServices.remove(i).close();
        }
        for (int i = 0; i < watchers.size(); i++){
            watchers.remove(i).close();
        }
    }

    @Override
    public boolean isClosed() {
        return closed;
    }
}
