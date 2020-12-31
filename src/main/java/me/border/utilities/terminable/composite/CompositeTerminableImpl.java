package me.border.utilities.terminable.composite;

import me.border.utilities.terminable.Terminable;
import me.border.utilities.terminable.exception.CompositeClosingException;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedDeque;

public class CompositeTerminableImpl implements CompositeTerminable {
    private boolean closed = false;
    private final Deque<AutoCloseable> closeables = new ConcurrentLinkedDeque<>();

    protected CompositeTerminableImpl() {
    }

    @Override
    public CompositeTerminable with(AutoCloseable closeable) {
        Objects.requireNonNull(closeable, "closeable");
        this.closeables.push(closeable);
        return this;
    }

    @Override
    public void close() throws CompositeClosingException {
        List<Exception> caught = new ArrayList<>();
        for (AutoCloseable ac; (ac = this.closeables.poll()) != null; ) {
            try {
                ac.close();
            } catch (Exception e) {
                caught.add(e);
            }
        }

        closed = true;
        if (!caught.isEmpty()) {
            throw new CompositeClosingException(caught);
        }
    }

    @Override
    public boolean isClosed() {
        return closed;
    }

    @Override
    public void cleanup() {
        this.closeables.removeIf(ac -> {
            if (ac instanceof CompositeTerminable) {
                ((CompositeTerminable) ac).cleanup();
            }
            if (ac instanceof Terminable) {
                return ((Terminable) ac).isClosed();
            }
            return false;
        });
    }
}
