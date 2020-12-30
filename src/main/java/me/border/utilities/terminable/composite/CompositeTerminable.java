package me.border.utilities.terminable.composite;

import me.border.utilities.terminable.Terminable;
import me.border.utilities.terminable.exception.CompositeClosingException;

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
     * @throws CompositeClosingException if any of the sub-terminables throw an
     *                                   exception on close
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
     * Binds an {@link Terminable} with this composite terminable.
     *
     * <p>Note that implementations do not keep track of duplicate contained
     * terminables. If a single {@link Terminable} is added twice, it will be
     * {@link Terminable#close() closed} twice.</p>
     *
     * @param terminable the terminable to bind
     * @throws NullPointerException if the terminable is null
     * @return this (for chaining)
     */
    CompositeTerminable with(Terminable terminable);

    /**
     * Binds all given {@link Terminable} with this composite terminable.
     *
     * <p>Note that implementations do not keep track of duplicate contained
     * terminables. If a single {@link Terminable} is added twice, it will be
     * {@link Terminable#close() closed} twice.</p>
     *
     * <p>Ignores null values.</p>
     *
     * @param terminables the terminables to bind
     * @return this (for chaining)
     */
    default CompositeTerminable withAll(Terminable... terminables) {
        for (Terminable terminable : terminables) {
            if (terminable == null) {
                continue;
            }
            bind(terminable);
        }
        return this;
    }

    /**
     * Binds all given {@link Terminable} with this composite terminable.
     *
     * <p>Note that implementations do not keep track of duplicate contained
     * terminables. If a single {@link Terminable} is added twice, it will be
     * {@link Terminable#close() closed} twice.</p>
     *
     * <p>Ignores null values.</p>
     *
     * @param terminables the terminables to bind
     * @return this (for chaining)
     */
    default CompositeTerminable withAll(Iterable<? extends Terminable> terminables) {
        for (Terminable terminable : terminables) {
            if (terminable == null) {
                continue;
            }
            bind(terminable);
        }
        return this;
    }

    default <T extends Terminable> T bind(T terminable) {
        with(terminable);
        return terminable;
    }

    /**
     * Removes instances which have already been terminated.
     */
    void cleanup();
}
