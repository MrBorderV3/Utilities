package me.border.utilities.terminable.exception;

import me.border.utilities.terminable.composite.CompositeTerminable;

import java.util.Collections;
import java.util.List;

/**
 * Exception thrown to propagate exceptions thrown by
 * {@link CompositeTerminable#close()}.
 */
public class CompositeClosingException extends Exception {
    private final List<? extends Throwable> causes;

    public CompositeClosingException(List<? extends Throwable> causes) {
        super("Exception(s) occurred whilst closing: " + causes.toString());
        if (causes.isEmpty()) {
            throw new IllegalArgumentException("No causes");
        }
        this.causes = Collections.unmodifiableList(causes);
    }

    public List<? extends Throwable> getCauses() {
        return this.causes;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
        for (Throwable cause : this.causes) {
            cause.printStackTrace();
        }
    }

}
