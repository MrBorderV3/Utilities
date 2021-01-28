package me.border.utilities.file;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractYamlFile {

    private final String name;
    private final File file;
    public Map<String, Object> values = new LinkedHashMap<>();


    /**
     * Construct a new Yaml File object.
     *
     * @param name The name of the file.
     * @param path The path for the file.
     */
    public AbstractYamlFile(String name, File path) {
        this.name = name;
        if (!path.exists()){
            path.mkdirs();
        }
        this.file = new File(path, this.name + ".yml");
        Runtime.getRuntime().addShutdownHook(new Thread(this::save));
    }

    /**
     * Check if the yaml file contains a given path
     *
     * @param path The path to check
     * @return {@code true} if the path exists {@code false} if the path does not exist
     */
    public boolean contains(String path){
        return values.containsKey(path);
    }

    // All of the following are get methods that return different types
    // All of them use #get(String) as their base and just cast to whatever type is being used.

    public String getString(String path){
        return (String) get(path);
    }

    public int getInt(String path){
        return (int) get(path);
    }

    public <T> T getAs(String path){
        return (T) get(path);
    }

    // END OF GET METHODS

    /**
     * Get a value from the yaml file
     *
     * @param path Path in the yaml file
     * @return The value associated with the given path
     */
    public Object get(String path) {
        try {
            return values.get(path);
        } catch (NullPointerException e) {
            throw new NullPointerException(path + " Does not exist in the file "  + "!");
        }
    }

    public void set(String path, Object value){
        values.put(path, value);
    }

    /**
     * Save the file
     */
    public void save(){
        try {
            FileWriter fileWriter = new FileWriter(file);
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            options.setPrettyFlow(true);
            Yaml yaml = new Yaml(options);
            yaml.dump(values, fileWriter);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Setup the file, create if doesn't exist or generate the values from the existing file if it does exist.
     */
    public void setup(){
        try {
            if (!file.exists())
                file.createNewFile();
            Yaml yaml = new Yaml();
            InputStream inputStream = new FileInputStream(file);
            Map<String, Object> o = yaml.loadAs(inputStream, Map.class);
            if (o != null){
                this.values.putAll(o);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public String getName(){
        return name;
    }

    public File getFile(){
        return file;
    }
}