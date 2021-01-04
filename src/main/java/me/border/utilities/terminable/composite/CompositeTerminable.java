package me.border.utilities.terminable.composite;

import me.border.utilities.terminable.Terminable;
import me.border.utilities.terminable.exception.CompositeClosingException;
import me.border.utilities.terminable.exception.TerminableClosedException;

/**
 * Represents a {@link Terminable} made up of several other {@link AutoCloseable}s.
 *
 * The {@link CompositeTerminableImpl#close()} method closes in LIFO (Last-In-First-Out) order.
 */
public interface CompositeTerminable extends Terminable {
    /**
     * Creates a new standalone {@link CompositeTerminable}.
     *
     * @return a new {@link CompositeTerminable}
     */
    static CompositeTerminable create() {
        return new CompositeTerminableImpl();
    }

    /**
     * Closes this composite terminable.
     *
     * @throws CompositeClosingException if any of the sub-terminables throw a exception on close
     */
    @Override
    void close() throws CompositeClosingException;

    @Override
    default void closeSilently() {
        try {
            close();
        } catch (CompositeClosingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Binds an {@link AutoCloseable} with this composite terminable.
     *
     * Note that implementations do not keep track of duplicate contained
     * closeables. If a single {@link AutoCloseable} is added twice, it will be
     * {@link #close() closed} twice.
     *
     * @param closeable the terminable to bind
     * @return this (for chaining)
     */
    CompositeTerminable with(AutoCloseable closeable);

    /**
     * Binds all given {@link AutoCloseable} with this composite terminable.
     *
     * Note that implementations do not keep track of duplicate contained
     * closeables. If a single {@link AutoCloseable} is added twice, it will be
     * {@link AutoCloseable#close() closed} twice.
     *
     * Ignores null values.
     *
     * @param closeables the closeables to bind
     * @return this (for chaining)
     */
    default CompositeTerminable withAll(AutoCloseable... closeables) throws TerminableClosedException{
        for (AutoCloseable closeable : closeables) {
            if (closeable == null) {
                continue;
            }
            bind(closeable);
        }
        return this;
    }

    /**
     * Binds all given {@link AutoCloseable} with this composite terminable.
     *
     * Note that implementations do not keep track of duplicate contained
     * closeables. If a single {@link AutoCloseable} is added twice, it will be
     * {@link #close() closed} twice.
     *
     * Ignores null values.
     *
     * @param closeables the closeables to bind
     * @return this (for chaining)
     */
    default CompositeTerminable withAll(Iterable<? extends AutoCloseable> closeables) throws TerminableClosedException {
        for (AutoCloseable closeable : closeables) {
            if (closeable == null) {
                continue;
            }
            bind(closeable);
        }
        return this;
    }

    /**
     * Binds an {@link AutoCloseable} with this composite terminable.
     * @see #with(AutoCloseable)
     *
     * @param closeable the terminable to bind
     * @param <T> The type of the closeable
     * @return The closeable
     */
    default <T extends AutoCloseable> T bind(T closeable) {
        with(closeable);
        return closeable;
    }

    /**
     * Removes instances which have already been terminated.
     */
    void cleanup();
}
