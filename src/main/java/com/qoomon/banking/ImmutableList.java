package com.qoomon.banking;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ImmutableList<T> {

    public static <T> List<T> of(T... objects) {
        return Collections.unmodifiableList(Arrays.asList(objects));
    }

    public static <T> List<T> copyOf(List<T> original) {
        return Collections.unmodifiableList(original);
    }
}
