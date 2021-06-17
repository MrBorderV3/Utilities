package me.border.utilities.cache.impl;

import me.border.utilities.cache.AbstractCacheMap;
import me.border.utilities.cache.Cacheable;

import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/**
 * PredicateCacheMap works very similarly to {@link ExpiringCacheMap} except it allows for a {@link Predicate} check too.
 * When the cleanup thread finds an expired {@link Cacheable} it will first run it through the given {@link Predicate}.
 * If the predicate returns {@code false} then the cacheable will be removed from the map normally.
 * If the predicate returns {@code true} time will be added to the cacheable using {@link Cacheable#add(long, TimeUnit)} with the values given in the constructor
 * and its removal will be cancelled.
 *
 * @param <K> Map key type
 *
 * @see ExpiringCacheMap
 */
public class PredicateCacheMap<K> extends AbstractCacheMap<K> {

    /**
     * Construct a new PredicateCacheMap
     *
     * @param predicate The predicate to test the values against
     * @param sleepTime Delay between cleanup executions
     * @param tu The {@link TimeUnit} for {@param sleepTime}
     * @param addTime The time to add to the value if it passes the predicate
     * @param addTU The {@link TimeUnit} for {@param addTime}
     */
    public PredicateCacheMap(Predicate<Object> predicate, long sleepTime, TimeUnit tu, long addTime, TimeUnit addTU) {
        super(tu.toMillis(sleepTime), cacheable -> {
            if (cacheable.isExpired()){
                if (predicate.test(cacheable.getObject())){
                    cacheable.add(addTime, addTU);
                    return false;
                }

                return true;
            }

            return false;
        });
    }
}
