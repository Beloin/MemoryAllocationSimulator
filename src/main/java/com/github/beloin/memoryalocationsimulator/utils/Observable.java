package com.github.beloin.memoryalocationsimulator.utils;

public interface Observable<T> {
    void addListener(Listener<T> listener);
    void removeListener(Listener<T> listener);
    T getObject();

    void updateListeners();
}
