package me.border.utilities.file;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractSerializedFile<I> {

    private static final Set<AbstractSerializedFile<?>> files = new HashSet<>();

    private final File file;
    private final File path;
    private I item;

    public AbstractSerializedFile(String file, File path, I item) {
        files.add(this);
        this.path = path;
        this.file = new File(path, file + ".dat");
        this.item = item;
    }

    public void setup() {
        if (!this.path.exists()){
            this.path.mkdirs();
        }
        if (this.file.exists()) {
            I temp = load();
            if (temp != null)
                this.item = temp;
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void save() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(item);
            oos.flush();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public I load() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            Object result = ois.readObject();
            ois.close();
            return (I) result;
        } catch (Exception e) {
            return null;
        }
    }


    public File getPath(){
        return path;
    }

    public File getFile() {
        return file;
    }

    public I getItem(){
        return item;
    }

    public void setItem(I i){
        this.item = i;
    }

    public static void setupAll(){
        files.forEach(AbstractSerializedFile::setup);
    }

    public static void saveAll(){
        files.forEach(AbstractSerializedFile::save);
    }
}
