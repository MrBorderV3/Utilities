package me.border.utilities.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class ExpiringCacheMap<K> implements CacheMap<K> {
    private final Map<K, Cacheable> cacheHashMap = new HashMap<>();
    private final Thread cleanUpThread;

    private volatile boolean closed = false;

    public ExpiringCacheMap(){
        this(30, TimeUnit.SECONDS);
    }

    @SuppressWarnings("BusyWait")
    public ExpiringCacheMap(long sleepTime, TimeUnit tu) {
        cleanUpThread = new Thread(() -> {
            long sleepTimeInMillis = tu.toMillis(sleepTime);
            try {
                while (!closed) {
                    Set<K> keySet = cacheHashMap.keySet();
                    Set<K> remove = new HashSet<>();
                    for (K key : keySet) {
                        Cacheable value = cacheHashMap.get(key);
                        if (value.isExpired()) {
                            remove.add(key);
                        }
                    }

                    for (K key : remove){
                        cacheHashMap.remove(key);
                    }
                    Thread.sleep(sleepTimeInMillis);
                }
            } catch (InterruptedException ignored) {
            }
        });
        cleanUpThread.setDaemon(true);
        cleanUpThread.setPriority(Thread.MIN_PRIORITY);
        cleanUpThread.start();
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
    public boolean containsKey(K key) {
        validate();
        return cacheHashMap.containsKey(key);
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
    public void clear() {
        validate();
        cacheHashMap.clear();
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
