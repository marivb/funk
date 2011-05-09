package org.javafunk.functional;

import org.junit.Test;
import org.javafunk.functional.functors.Predicate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.Literals.listWith;

public class EagerAnyAllNoneTest {
    @Test
    public void shouldReturnTrueIfAnyElementsSatisfyThePredicateFunction() {
        // Given
        List<Integer> inputNumbers = listWith(5, 10, 15, 20);

        // When
        Boolean result = Eager.any(inputNumbers, new Predicate<Integer>() {
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
        Boolean result = Eager.any(items, new Predicate<Integer>() {
            @Override
            public boolean evaluate(Integer item) {
                return item > 25;
            }
        });

        // Then
        assertThat(result, is(false));
    }

    @Test
    public void shouldReturnTrueIfAllElementsSatisfyThePredicateFunction() {
        // Given
        List<String> items = listWith("dog", "cat", "fish", "budgie");

        // When
        Boolean result = Eager.all(items, new Predicate<String>() {
            @Override
            public boolean evaluate(String item) {
                return item.length() > 2;
            }
        });

        // Then
        assertThat(result, is(true));
    }

    @Test
    public void shouldReturnFalseIfAnyOfTheElementsDoNotSatisyThePredicateFunction() {
        // Given
        List<String> items = listWith("dog", "cat", "fish", "budgie");

        // When
        Boolean result = Eager.all(items, new Predicate<String>() {
            @Override
            public boolean evaluate(String item) {
                return item.length() > 3;
            }
        });

        // Then
        assertThat(result, is(false));
    }

    @Test
    public void shouldReturnTrueIfNoneOfTheElementsMatchesThePredicateFunction() {
        // Given
        List<Integer> items = listWith(1, 3, 5, 7);

        // When
        Boolean result = Eager.none(items, new Predicate<Integer>() {
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
        Boolean result = Eager.none(items, new Predicate<Integer>() {
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
}
