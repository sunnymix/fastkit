package net.fastkit.core.common;

public interface Value<T> {

    T value();

    boolean isEmpty();

    default boolean notEmpty() {
        return !isEmpty();
    }

    static <T> boolean isEmpty(Value<T> value) {
        return value == null || value.isEmpty();
    }

    static <T> boolean notEmpty(Value<T> value) {
        return !isEmpty(value);
    }

}
