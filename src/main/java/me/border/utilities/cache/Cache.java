package me.border.utilities.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Cache<K> implements ICache<K> {
    private final Map<K, Cacheable> cacheHashMap = new HashMap<>();
    private Thread cleanUpThread;

    private volatile boolean closed = false;

    public Cache(){
        this(30, TimeUnit.SECONDS);
    }

    public Cache(long sleepTime, TimeUnit tu){
        try {
            cleanUpThread = new Thread(() -> {
                long sleepTimeInMillis = tu.toMillis(sleepTime);
                try {
                    while (!closed) {
                        Set<K> keySet = cacheHashMap.keySet();
                        for (K key : keySet) {
                            Cacheable value = cacheHashMap.get(key);
                            if (value.isExpired()) {
                                cacheHashMap.remove(key);
                            }
                        }
                        Thread.sleep(sleepTimeInMillis);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            cleanUpThread.setDaemon(true);
            cleanUpThread.setPriority(Thread.MIN_PRIORITY);
            cleanUpThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cache(K key, Cacheable value) {
        validate();
        cacheHashMap.put(key, value);
    }

    @Override
    public Cacheable get(K key) {
        validate();
        Cacheable object = cacheHashMap.get(key);
        if (object == null)
            return null;
        if (object.isExpired()) {
            cacheHashMap.remove(key);
            return null;
        } else {
            return object;
        }
    }

    @Override
    public Object getParsed(K key) {
        Cacheable cacheable = get(key);
        if (cacheable == null)
            return null;
        return cacheable.getObject();
    }

    @Override
    public void remove(K key) {
        validate();
        cacheHashMap.remove(key);
    }

    @Override
    public void close() throws Exception {
        validate();
        cleanUpThread.interrupt();
        cacheHashMap.clear();
        closed = true;
    }

    @Override
    public boolean isClosed() {
        return closed;
    }
}