package com.qoomon.banking;

import java.util.ArrayList;
import java.util.Collections;

public class Lists<E> {

    public static <E> ArrayList<E> newArrayList(E... elements) {
        Preconditions.checkNotNull(elements);
        // Avoid integer overflow when a large array is passed in
        int capacity = computeArrayListCapacity(elements.length);
        ArrayList<E> list = new ArrayList<>(elements.length);
        Collections.addAll(list, elements);
        return list;
    }

    static int computeArrayListCapacity(int arraySize) {
        Preconditions.checkArgument(arraySize >= 0, "arraySize must be non-negative");

        return saturatedCast(5L + arraySize + (arraySize / 10));
    }

    static int saturatedCast(long value) {
        if (value > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        if (value < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        return (int) value;
    }
}
