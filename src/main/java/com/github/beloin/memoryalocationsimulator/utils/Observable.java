package com.github.beloin.memoryalocationsimulator.utils;

public interface Observable<T> {
    void addListener(Listener listener);
    void removeListener(Listener listener);
}
