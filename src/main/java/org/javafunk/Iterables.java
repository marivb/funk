package org.javafunk;

import org.javafunk.collections.Bag;
import org.javafunk.collections.HashBag;
import org.javafunk.functional.functors.Reducer;

import java.util.*;

import static org.javafunk.functional.Eager.reduce;

public class Iterables {
    public static <T> List<T> asList(Iterable<? extends T> iterable) {
        return reduce(iterable, new ArrayList<T>(), new Reducer<T, List<T>>() {
            public List<T> accumulate(List<T> accumulator, T element) {
                accumulator.add(element);
                return accumulator;
            }
        });
    }

    public static <T> Set<T> asSet(Iterable<? extends T> iterable) {
        return reduce(iterable, new HashSet<T>(), new Reducer<T, Set<T>>() {
            public Set<T> accumulate(Set<T> accumulator, T element) {
                accumulator.add(element);
                return accumulator;
            }
        });
    }

    public static <T> Bag<T> asBag(Iterable<? extends T> iterable) {
        return reduce(iterable, new HashBag<T>(), new Reducer<T, HashBag<T>>() {
            public HashBag<T> accumulate(HashBag<T> accumulator, T element) {
                accumulator.add(element);
                return accumulator;
            }
        });
    }

    public static <T> Collection<T> materialize(Iterable<? extends T> iterable) {
        return asList(iterable);
    }
}
