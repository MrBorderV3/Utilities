package me.border.utilities.example;

import me.border.utilities.file.AbstractSerializedFile;

import java.io.File;
import java.util.HashMap;

public class ExampleSerializedFile extends AbstractSerializedFile<HashMap<String, String>> {

    protected ExampleSerializedFile() {
        super("example", new File("/data"), new HashMap<>());
    }
}
