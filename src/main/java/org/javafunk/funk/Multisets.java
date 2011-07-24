package org.javafunk.funk;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import static java.util.Arrays.asList;
import static org.javafunk.funk.Eager.first;
import static org.javafunk.funk.Eager.rest;
import static org.javafunk.funk.Literals.listFrom;
import static org.javafunk.funk.Literals.listWith;

public class Multisets {
    private Multisets() {}

    public static <T> Multiset<T> union(Iterable<? extends Iterable<? extends T>> iterables) {
        Multiset<T> unionMultiset = HashMultiset.create();
        for (Iterable<? extends T> iterable : iterables) {
            unionMultiset.addAll(listFrom(iterable));
        }
        return unionMultiset;
    }

    public static <T> Multiset<T> difference(Iterable<? extends Iterable<? extends T>> iterables) {
        Multiset<T> differences = HashMultiset.create(listFrom(first(iterables)));
        for (Iterable<? extends T> iterable : rest(iterables)) {
            for (T item : iterable) {
                differences.remove(item);
            }
        }
        return differences;
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2) {
        return union(asList(i1, i2));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3) {
        return union(asList(i1, i2, i3));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4) {
        return union(asList(i1, i2, i3, i4));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5) {
        return union(asList(i1, i2, i3, i4, i5));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6) {
        return union(asList(i1, i2, i3, i4, i5, i6));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T>... i7on) {
        return union(listWith(i1, i2, i3, i4, i5, i6).and(i7on));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2) {
        return difference(asList(i1, i2));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3) {
        return difference(asList(i1, i2, i3));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4) {
        return difference(asList(i1, i2, i3, i4));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5) {
        return difference(asList(i1, i2, i3, i4, i5));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6) {
        return difference(asList(i1, i2, i3, i4, i5, i6));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T>... i7on) {
        return difference(listWith(i1, i2, i3, i4, i5, i6).and(i7on));
    }
}