package com.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class SuperIterator implements Iterator<Integer> {

    private final List<IteratorInfo> iteratorInfos = new ArrayList<>();

    public SuperIterator(Collection<Iterator<Integer>> iterators) {
        if (iterators == null) {
            throw new RuntimeException("Collection of iterators is null");
        }

        iterators.forEach(iterator -> {
            if (iterator != null && iterator.hasNext()) {
                iteratorInfos.add(new IteratorInfo(iterator.next(), iterator));
            }
        });
    }

    @Override
    public boolean hasNext() {
        return !iteratorInfos.isEmpty();
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new RuntimeException("The list of values is empty");
        }

        IteratorInfo iteratorWithMinValue = findIteratorWithMinValue();
        Integer minValue = iteratorWithMinValue.getCurrentValue();
        updateIteratorOrRemoveIfEmpty(iteratorWithMinValue);

        return minValue;
    }

    private IteratorInfo findIteratorWithMinValue() {
        IteratorInfo iterWithMinValue = iteratorInfos.get(0);

        for (IteratorInfo currentIterator : iteratorInfos) {
            if (currentIterator.getCurrentValue() < iterWithMinValue.getCurrentValue()) {
                iterWithMinValue = currentIterator;
            }
        }

        return iterWithMinValue;
    }

    private void updateIteratorOrRemoveIfEmpty(IteratorInfo iterWithMinValue) {
        if (iterWithMinValue.getIterator().hasNext()) {
            iterWithMinValue.setCurrentValue(iterWithMinValue.getIterator().next());
        } else {
            iteratorInfos.remove(iterWithMinValue);
        }
    }
}
