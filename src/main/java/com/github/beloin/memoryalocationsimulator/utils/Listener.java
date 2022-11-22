package com.github.beloin.memoryalocationsimulator.utils;

public interface Listener<T> {
    void update(Observable<T> observable);
}
