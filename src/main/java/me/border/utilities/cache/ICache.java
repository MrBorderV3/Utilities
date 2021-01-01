package me.border.utilities.cache;

import me.border.utilities.terminable.Terminable;

public interface ICache<K> extends Terminable {

    /**
     * Cache a {@link Cacheable}
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
     * @see #get(K)
     *
     * @param key the identifier
     * @return The parsed object
     */
    Object getParsed(K key);

    /**
     * Remove an object from the cache
     *
     * @param key The object identifier
     */
    void remove(K key);
}
