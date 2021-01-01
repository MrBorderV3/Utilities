package me.border.utilities.filewatcher;

/**
 * Adapter for {@link FileListener}
 */
public abstract class FileAdapter implements FileListener {

    @Override
    public void onCreated(FileEvent event){
    }

    @Override
    public void onModified(FileEvent event){
    }

    @Override
    public void onDeleted(FileEvent event){
    }
}
