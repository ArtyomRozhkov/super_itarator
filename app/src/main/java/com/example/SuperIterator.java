package com.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SuperIterator implements java.util.Iterator<Integer> {

    private List<IteratorInfo> values = new ArrayList<>();

    public SuperIterator(Collection<java.util.Iterator<Integer>> iterators) {
        if (iterators == null) {
            throw new RuntimeException("Collection of iterators is null");
        }

        iterators.forEach(iterator -> {
            if (iterator != null && iterator.hasNext()) {
                values.add(new IteratorInfo(iterator.next(), iterator));
            }
        });
    }

    @Override
    public boolean hasNext() {
        return !values.isEmpty();
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new RuntimeException("The list of values is empty");
        }

        IteratorInfo iterWithMinValue = values.get(0);
        for (IteratorInfo currentIterator : values) {
            if (currentIterator.getCurrentValue() < iterWithMinValue.getCurrentValue()) {
                iterWithMinValue = currentIterator;
            }
        }

        Integer minValue = iterWithMinValue.getCurrentValue();

        if (iterWithMinValue.getIterator().hasNext()) {
            iterWithMinValue.setCurrentValue(iterWithMinValue.getIterator().next());
        } else {
            values.remove(iterWithMinValue);
        }

        return minValue;
    }
}
