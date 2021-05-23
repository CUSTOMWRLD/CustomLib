package com.customwrld.customlib.util;

import java.util.*;

public class RandomCollection<E> {

    private final NavigableMap<Double, E> map = new TreeMap<>();
    private final Random random;
    private double total = 0;
    public int size = 0;

    public RandomCollection() {
        this(new Random());
    }

    public RandomCollection(Random random) {
        this.random = random;
    }

    public RandomCollection(List<E> list) {
        this();
        this.total = 0.0D;
        this.size = list.size();

        for(E element : list) {
            this.map.put(1.0, element);
        }
    }

    public RandomCollection(Map<Double, E> map) {
        this();
        this.map.putAll(map);
        this.total = 0.0D;
        this.size = map.size();
    }

    public RandomCollection(Map<Double, E> map, Random random) {
        this(random);
        this.map.putAll(map);
        this.total = 0.0D;
        this.size = map.size();
    }

    public RandomCollection<E> add(double weight, E result) {
        if (weight <= 0) return this;
        total += weight;
        map.put(total, result);
        size++;
        return this;
    }

    public E next() {
        double value = random.nextDouble() * total;
        return map.higherEntry(value).getValue();
    }
}
