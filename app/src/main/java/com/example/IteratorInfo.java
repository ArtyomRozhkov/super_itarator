package com.example;

import java.util.Iterator;

public class IteratorInfo {
    private Integer currentValue;
    private Iterator<Integer> iterator;

    public IteratorInfo(Integer currentValue, Iterator<Integer> iterator) {
        this.currentValue = currentValue;
        this.iterator = iterator;
    }

    public Integer getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Integer currentValue) {
        this.currentValue = currentValue;
    }

    public Iterator<Integer> getIterator() {
        return iterator;
    }

    public void setIterator(Iterator<Integer> iterator) {
        this.iterator = iterator;
    }
}
