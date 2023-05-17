package com.example;

import java.util.Collection;

public class SuperIterator implements java.util.Iterator<Integer> {

    public SuperIterator(Collection<java.util.Iterator<Integer>> iterators){
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public boolean hasNext() {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Integer next() {
        throw new RuntimeException("Not implemented yet");
    }
}
