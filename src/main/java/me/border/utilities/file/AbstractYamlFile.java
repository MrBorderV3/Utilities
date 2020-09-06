package me.border.utilities.file;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractYamlFile {

    private String fileName;
    private File file;
    public Map<String, Object> values = new HashMap<>();

    public AbstractYamlFile(String fileName){
        this.fileName = fileName;
        Runtime.getRuntime().addShutdownHook(new Thread(this::save));
    }

    public boolean contains(String path){
        return values.containsKey(path);
    }

    public String getString(String path){
        return (String) get(path);
    }

    public int getInt(String path){
        return (int) get(path);
    }

    private <T> T getAs(String path){
        return (T) get(path);
    }

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

    public void setup(){
        try {
            File file = new File(this.fileName + ".yml");
            if (!file.exists())
                file.createNewFile();
            this.file = file;
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

    public String getFileName(){
        return fileName;
    }

    public File getFile(){
        return file;
    }
}