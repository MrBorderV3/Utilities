package me.border.utilities.cache;

import me.border.utilities.terminable.Terminable;

public interface CacheMap<K> extends Terminable {

    /**
     * Add a {@link Cacheable} object to the cache
     *
     * @param key Object identifier
     * @param value The {@link Cacheable} to cache
     */
    void cache(K key, Cacheable value);


    /**
     * Get a {@link Cacheable} from the cache with the corresponding identifier
     *
     * @param key The identifier
     * @return The {@link Cacheable}
     */
    Cacheable get(K key);

    /**
     * Get a parsed object from the cache with the corresponding identifier
     * {@link #get(Object)}
     *
     * @param key the identifier
     * @return The parsed object
     */
    Object getParsed(K key);

    /**
     * Check if the cache contains a key
     *
     * @param key The key to check against
     * @return {@code true} if the key exists {@code false} if it isn't.
     */
    boolean containsKey(K key);

    /**
     * Remove an object from the cache
     *
     * @param key The object identifier
     */
    void remove(K key);

    /**
     * Clear the cache
     */
    void clear();
}
