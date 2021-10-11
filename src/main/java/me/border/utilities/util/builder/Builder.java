package me.border.utilities.util.builder;

public interface Builder<T> {

    T build() throws BuilderException;
}
