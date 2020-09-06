package me.border.utilities.example;

import me.border.utilities.cache.AbstractCache;

public class ExampleCache extends AbstractCache<String> {
    protected ExampleCache(){
        super(60000); // In millis
    }
}
