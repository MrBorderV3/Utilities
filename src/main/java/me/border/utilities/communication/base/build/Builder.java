package me.border.utilities.communication.base.build;

import me.border.utilities.communication.base.exception.BuilderException;

public interface Builder<T> {

    T build() throws BuilderException;
}
