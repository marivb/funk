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
import org.javafunk.funk.monads.Option;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.collectionWith;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.funk.Literals.listWith;
import static org.javafunk.funk.monads.Option.some;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;

public class EagerlyFirstSecondRestLastTest {
    @Test
    public void shouldReturnAnOptionOfTheFirstElementFromTheSuppliedIterable() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(10, 9, 8, 7);

        // When
        Option<Integer> output = Eagerly.first(input);

        // Then
        assertThat(output, is(some(10)));
    }

    @Test
    public void shouldReturnNoneForFirstIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = new ArrayList<Integer>();

        // When
        Option<Integer> output = Eagerly.first(input);

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test
    public void shouldReturnAnOptionOfTheSecondElementFromTheSuppliedIterable() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(10, 9, 8, 7);

        // When
        Option<Integer> output = Eagerly.second(input);

        // Then
        assertThat(output, is(some(9)));
    }

    @Test
    public void shouldReturnNoneForSecondIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = new ArrayList<Integer>();

        // When
        Option<Integer> output = Eagerly.second(input);

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test
    public void shouldReturnAnOptionOfTheFirstElementInTheSuppliedIterableMatchingTheSuppliedPredicate() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(9, 8, 7, 6, 5, 4, 3, 2, 1);

        // When
        Option<Integer> output = Eagerly.first(input, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(output, is(some(8)));
    }

    @Test
    public void shouldReturnNoneForPredicatedFirstIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = new ArrayList<Integer>();

        // When
        Option<Integer> output = Eagerly.first(input, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test
    public void shouldReturnNoneForPredicatedFirstIfNoElementsInTheSuppliedIterableMatch() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 3, 5, 7);

        // When
        Option<Integer> output = Eagerly.first(input, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfThePredicateSuppliedToFirstIsNull() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3, 4, 5);
        Predicate<Integer> predicate = null;

        // When
        Eagerly.first(input, predicate);

        // Then a NullPointerException is thrown.
    }

    @Test
    public void shouldReturnTheFirstNElementsFromTheSuppliedIterable() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = collectionWith(10, 9, 8, 7);

        // When
        Collection<Integer> actualOutput = Eagerly.first(input, 4);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAsManyElementsAsPossibleForFirstIfThereAreNotEnoughElementsInTheSuppliedIterable() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(3, 2, 1);
        Collection<Integer> expectedOutput = collectionWith(3, 2, 1);

        // When
        Collection<Integer> actualOutput = Eagerly.first(input, 4);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForFirstIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = Collections.emptyList();
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.first(input, 3);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForFirstIfTheNumberOfElementsRequiredIsZero() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(3, 2, 1);
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.first(input, 0);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionForFirstIfTheNumberOfElementsRequiredIsNegative() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(3, 2, 1);

        // When
        Eagerly.first(input, -3);

        // Then an IllegalArgumentException should be thrown.
    }

    @Test
    public void shouldReturnTheFirstNElementsInTheSuppliedIterableMatchingTheSuppliedPredicate() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(9, 8, 7, 6, 5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = collectionWith(8, 6);

        // When
        Collection<Integer> actualOutput = Eagerly.first(input, 2, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAsManyElementsAsPossibleForPredicatedNFirstIfThereAreNotEnoughMatchingElementsInTheSuppliedIterable() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = collectionWith(4, 2);

        // When
        Collection<Integer> actualOutput = Eagerly.first(input, 4, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForPredicatedNFirstIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = Collections.emptyList();
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.first(input, 3, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForPredicatedNFirstIfTheNumberOfElementsRequiredIsZero() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(3, 2, 1);
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.first(input, 0, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForPredicatedNFirstIfThereAreNoElementsMatchingTheSuppliedPredicate() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 3, 5, 7);
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.first(input, 2, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfThePredicateSuppliedToFirstNIsNull() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3, 4, 5);
        Predicate<Integer> predicate = null;
        Integer numberOfElementsRequired = 5;

        // When
        Eagerly.first(input, numberOfElementsRequired, predicate);

        // Then a NullPointerException is thrown.
    }

    @Test
    public void shouldReturnAnOptionOfTheLastElementFromTheSuppliedIterable() throws Exception {
        // Given
        Iterable<Integer> input = listWith(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);

        // When
        Option<Integer> output = Eagerly.last(input);

        // Then
        assertThat(output, is(some(1)));
    }

    @Test
    public void shouldReturnNoneForLastIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = new ArrayList<Integer>();

        // When
        Option<Integer> output = Eagerly.last(input);

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test
    public void shouldReturnAnOptionOfTheLastElementInTheSuppliedIterableMatchingTheSuppliedPredicate() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(9, 8, 7, 6, 5, 4, 3, 2, 1);

        // When
        Option<Integer> output = Eagerly.last(input, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(output, is(some(2)));
    }

    @Test
    public void shouldReturnNoneForPredicatedLastIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = new ArrayList<Integer>();

        // When
        Option<Integer> output = Eagerly.last(input, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test
    public void shouldReturnNoneForPredicatedLastIfNoElementsInTheSuppliedIterableMatch() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 3, 5, 7);

        // When
        Option<Integer> output = Eagerly.last(input, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfThePredicateSuppliedToLastIsNull() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3, 4, 5);
        Predicate<Integer> predicate = null;

        // When
        Eagerly.last(input, predicate);

        // Then a NullPointerException is thrown.
    }

    @Test
    public void shouldReturnTheLastNElementsFromTheSuppliedIterable() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = collectionWith(3, 2, 1);

        // When
        Collection<Integer> actualOutput = Eagerly.last(input, 3);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAsManyElementsAsPossibleForLastIfThereAreNotEnoughElementsInTheSuppliedIterable() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(3, 2, 1);
        Collection<Integer> expectedOutput = collectionWith(3, 2, 1);

        // When
        Collection<Integer> actualOutput = Eagerly.last(input, 4);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForLastIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = Collections.emptyList();
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.last(input, 3);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForLastIfTheNumberOfElementsRequiredIsZero() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(3, 2, 1);
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.last(input, 0);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionForLastIfTheNumberOfElementsRequiredIsNegative() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(3, 2, 1);

        // When
        Eagerly.last(input, -3);

        // Then an IllegalArgumentException should be thrown.
    }

    @Test
    public void shouldReturnTheLastNElementsInTheSuppliedIterableMatchingTheSuppliedPredicate() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(9, 8, 7, 6, 5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = collectionWith(6, 4, 2);

        // When
        Collection<Integer> actualOutput = Eagerly.last(input, 3, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAsManyElementsAsPossibleForPredicatedNLastIfThereAreNotEnoughMatchingElementsInTheSuppliedIterable() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = collectionWith(4, 2);

        // When
        Collection<Integer> actualOutput = Eagerly.last(input, 4, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForPredicatedNLastIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = Collections.emptyList();
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.last(input, 3, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForPredicatedNLastIfTheNumberOfElementsRequiredIsZero() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(3, 2, 1);
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.last(input, 0, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForPredicatedNLastIfThereAreNoElementsMatchingTheSuppliedPredicate() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 3, 5, 7);
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.last(input, 2, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfThePredicateSuppliedToLastNIsNull() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3, 4, 5);
        Predicate<Integer> predicate = null;
        Integer numberOfElementsRequired = 5;

        // When
        Eagerly.first(input, numberOfElementsRequired, predicate);

        // Then a NullPointerException is thrown.
    }

    @Test
    public void shouldReturnAllButFirstElementForRestIfIterableHasMoreThanOneElement() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3, 4);
        Collection<Integer> expectedOutput = collectionWith(2, 3, 4);

        // When
        Collection<Integer> actualOutput = Eagerly.rest(input);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForRestIfIterableHasOnlyOneElement() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1);
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.rest(input);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForRestIfIterableHasNoElements() throws Exception {
        // Given
        Iterable<Integer> input = Iterables.empty();
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.rest(input);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }
}
