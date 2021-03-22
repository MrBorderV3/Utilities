package me.border.utilities.cache;

import java.util.concurrent.TimeUnit;

/**
 * A Cleanup thread runs at given intervals and checks for expired {@link Cacheable} using {@link Cacheable#isExpired()}
 * If they're expired then it removes them from the map.
 *
 * @param <K> Map key type
 */
public class ExpiringCacheMap<K> extends AbstractCacheMap<K> {

    public ExpiringCacheMap(){
        this(30, TimeUnit.SECONDS);
    }

    public ExpiringCacheMap(long sleepTime, TimeUnit tu) {
        super(tu.toMillis(sleepTime), Cacheable::isExpired);
    }
}
