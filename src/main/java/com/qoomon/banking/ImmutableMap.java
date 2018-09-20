package com.qoomon.banking;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ImmutableMap<K, V> {

    public static <K, V> Map<K, V> copyOf(Map<K, V> original) {
        Map<K, V> copy = new HashMap<>();
        for (Map.Entry<K, V> entry : original.entrySet()) {
            copy.put(entry.getKey(), entry.getValue());
        }
        return Collections.unmodifiableMap(copy);
    }
}
