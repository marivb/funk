/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.functors.Predicate;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.iterable;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.funk.Literals.list;
import static org.javafunk.funk.Literals.listWith;

public class EagerlyAnyAllNoneTest {
    @Test
    public void shouldReturnTrueIfAnyElementsSatisfyThePredicateFunction() {
        // Given
        List<Integer> inputNumbers = listWith(5, 10, 15, 20);

        // When
        Boolean result = Eagerly.any(inputNumbers, new Predicate<Integer>() {
            @Override
            public boolean evaluate(Integer item) {
                return item > 15;
            }
        });

        // Then
        assertThat(result, is(true));
    }

    @Test
    public void shouldReturnFalseIfNoElementsSatisfyThePredicateFunction() {
        // Given
        List<Integer> items = listWith(5, 10, 15, 20);

        // When
        Boolean result = Eagerly.any(items, new Predicate<Integer>() {
            @Override
            public boolean evaluate(Integer item) {
                return item > 25;
            }
        });

        // Then
        assertThat(result, is(false));
    }

    @Test
    public void shouldReturnFalseForAnyIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<String> items = iterable();

        // When
        Boolean result = Eagerly.any(items, new Predicate<String>() {
            @Override public boolean evaluate(String input) {
                return input.length() > 5;
            }
        });

        // Then
        assertThat(result, is(false));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfPredicateSuppliedToAnyIsNull() throws Exception {
        // Given
        Iterable<String> items = iterableWith("a", "b", "c");
        Predicate<String> predicate = null;

        // When
        Eagerly.any(items, predicate);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldReturnTrueIfAllElementsSatisfyThePredicateFunction() {
        // Given
        List<String> items = listWith("dog", "cat", "fish", "budgie");

        // When
        Boolean result = Eagerly.all(items, new Predicate<String>() {
            @Override
            public boolean evaluate(String item) {
                return item.length() > 2;
            }
        });

        // Then
        assertThat(result, is(true));
    }

    @Test
    public void shouldReturnTrueIfAnyOfTheElementsDoNotSatisfyThePredicateFunction() {
        // Given
        List<String> items = listWith("dog", "cat", "fish", "budgie");

        // When
        Boolean result = Eagerly.all(items, new Predicate<String>() {
            @Override
            public boolean evaluate(String item) {
                return item.length() > 3;
            }
        });

        // Then
        assertThat(result, is(false));
    }

    @Test
    public void shouldReturnTrueForAllIfTheSuppliedIterableIsEmpty() {
        // Given
        List<String> items = list();

        // When
        Boolean result = Eagerly.all(items, new Predicate<String>() {
            @Override
            public boolean evaluate(String item) {
                return item.length() > 3;
            }
        });

        // Then
        assertThat(result, is(true));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfPredicateSuppliedToAllIsNull() throws Exception {
        // Given
        Iterable<String> items = iterableWith("a", "b", "c");
        Predicate<String> predicate = null;

        // When
        Eagerly.all(items, predicate);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldReturnTrueIfNoneOfTheElementsMatchesThePredicateFunction() {
        // Given
        List<Integer> items = listWith(1, 3, 5, 7);

        // When
        Boolean result = Eagerly.none(items, new Predicate<Integer>() {
            @Override
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(result, is(true));
    }

    @Test
    public void shouldReturnFalseIfAnyOfTheElementsMatchesTePredicateFunction() {
        // Given
        List<Integer> items = listWith(1, 3, 6, 7);

        // When
        Boolean result = Eagerly.none(items, new Predicate<Integer>() {
            @Override
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(result, is(false));
    }

    @Test
    public void shouldReturnTrueForNoneIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<String> items = iterable();

        // When
        Boolean result = Eagerly.none(items, new Predicate<String>() {
            @Override public boolean evaluate(String input) {
                return input.length() < 5;
            }
        });

        // Then
        assertThat(result, is(result));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfPredicateSuppliedToNoneIsNull() throws Exception {
        // Given
        Iterable<String> items = iterableWith("a", "b", "c");
        Predicate<String> predicate = null;

        // When
        Eagerly.none(items, predicate);

        // Then a NullPointerException is thrown
    }
}
