package me.border.utilities.cache;

public interface Cacheable {

    /**
     * Check if the cache is expired
     *
     * @return If it expired
     */
    boolean isExpired();

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
