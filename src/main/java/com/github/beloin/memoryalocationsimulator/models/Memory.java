package com.github.beloin.memoryalocationsimulator.models;


import com.github.beloin.memoryalocationsimulator.utils.Listener;
import com.github.beloin.memoryalocationsimulator.utils.Observable;

public class Memory implements Observable<Memory> {

    @Override
    public void addListener(Listener<Memory> listener) {

    }

    @Override
    public void removeListener(Listener<Memory> listener) {

    }

    @Override
    public Memory getObject() {
        return this;
    }
}
