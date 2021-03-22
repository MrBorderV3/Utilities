package me.border.utilities.cache;

import java.util.concurrent.TimeUnit;

public interface Cacheable {

    /**
     * Check if the cache is expired
     *
     * @return If it expired
     */
    boolean isExpired();

    /**
     * Add time to the cacheable's expire timer.
     *
     * @param time The amount of time to add
     * @param tu The {@link TimeUnit} to add the time as
     */
    void add(long time, TimeUnit tu);

    /**
     * Get the cached object
     *
     * @return The cached object
     */
    Object getObject();

    /**
     * Get the hashcode for the {@link Cacheable}
     *
     * @return The hashcode
     *
     * @see Object#hashCode()
     */
    int hashCode();

    /**
     * Get if the object is equal to the param object
     *
     * @param o Object to check against
     * @return {@code true} if they're equal {@code false} if they're not
     * 
     * @see Object#equals(Object) 
     */
    boolean equals(Object o);
}
