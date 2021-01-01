package me.border.utilities.interfaces;

import me.border.utilities.communication.base.exception.BuilderException;

public interface Builder<T> {

    T build() throws BuilderException;
}
