package me.border.utilities.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public abstract class AbstractCacheMap<K> implements CacheMap<K> {
    protected final Map<K, Cacheable> cacheHashMap = new HashMap<>();
    private final Thread cleanUpThread;

    private volatile boolean closed = false;

    @SuppressWarnings("BusyWait")
    protected AbstractCacheMap(long sleepTimeMillis, Predicate<Cacheable> predicate){
        cleanUpThread = new Thread(() -> {
            try {
                while (!closed) {
                    Set<K> keySet = cacheHashMap.keySet();
                    Set<K> remove = new HashSet<>();
                    for (K key : keySet) {
                        Cacheable value = cacheHashMap.get(key);
                        if (predicate.test(value)) {
                            remove.add(key);
                        }
                    }

                    for (K key : remove){
                        cacheHashMap.remove(key);
                    }
                    Thread.sleep(sleepTimeMillis);
                }
            } catch (InterruptedException ignored) {
            }
        });

        cleanUpThread.setDaemon(true);
        cleanUpThread.setPriority(Thread.MIN_PRIORITY);
        cleanUpThread.start();
    }

    public void cache(K key, Object value, int timeToLiveMinutes){
        cache(key, value, timeToLiveMinutes, TimeUnit.MINUTES);
    }

    public void cache(K key, Object value, int timeToLive, TimeUnit tu){
        CachedObject cObj = new CachedObject(value, timeToLive, tu);
        cache(key, cObj);
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
