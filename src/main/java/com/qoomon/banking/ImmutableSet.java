package com.qoomon.banking;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ImmutableSet<T> {

    public static <T> Set<T> of(T... objects) {
        return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(objects)));
    }
}
