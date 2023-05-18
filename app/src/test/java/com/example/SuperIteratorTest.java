package com.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SuperIteratorTest {

    @Test
    @DisplayName("Позитивный тест")
    void testSuccessful() {
        Iterator<Integer> iterator1 = List.of(1, 2, 4)
                .iterator();
        Iterator<Integer> iterator2 = List.of(3, 5, 7, 9)
                .iterator();
        Iterator<Integer> iterator3 = Collections.emptyIterator();

        SuperIterator sut = new SuperIterator(List.of(iterator1, iterator2, iterator3));

        List<Integer> expectedList = List.of(1, 2, 3, 4, 5, 7, 9);

        List<Integer> actualList = new ArrayList<>();
        sut.forEachRemaining(actualList::add);

        assertThat(actualList)
                .isEqualTo(expectedList);
    }

    @Test
    @DisplayName("Тест с пустым списком итераторов")
    void testWithEmptyListOfIterators() {
        SuperIterator sut = new SuperIterator(List.of());

        assertFalse(sut.hasNext());
    }

    @Test
    @DisplayName("Тест с пустыми итераторами")
    void testWithEmptyIterators() {
        Iterator<Integer> iterator1 = Collections.emptyIterator();
        Iterator<Integer> iterator2 = Collections.emptyIterator();

        SuperIterator sut = new SuperIterator(List.of(iterator1, iterator2));

        assertFalse(sut.hasNext());
    }

    @Test
    @DisplayName("Тест с пустыми итераторам: исключение при вызове метода next()")
    void testWithEmptyIteratorsException() {
        SuperIterator sut = new SuperIterator(List.of());

        assertThrows(RuntimeException.class, () -> sut.next());
    }

    @Test
    @DisplayName("Тест со списком с null итератором")
    void testWithNullableListOfIterators() {
        assertThrows(RuntimeException.class, () -> new SuperIterator(List.of(null)));
    }

    @Test
    @DisplayName("Тест с null вместо списка итераторов")
    void testWithNullInsteadOfIterators() {
        assertThrows(RuntimeException.class, () -> new SuperIterator(null));
    }

    @Test
    @DisplayName("Бесконечный итератор")
    void testWithInfinityIterator() throws ExecutionException, InterruptedException {
        InfinityIterator infinityIterator = new InfinityIterator();
        SuperIterator sut = new SuperIterator(List.of(infinityIterator));

        var completableFuture = CompletableFuture.supplyAsync(() -> {
            while (sut.hasNext()) {
                assertThat(sut.next()).isEqualTo(1);
            }
            return null;
        });

        try {
            completableFuture.get(1, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
        }
    }

    public class InfinityIterator implements Iterator<Integer> {

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Integer next() {
            return 1;
        }
    }
}