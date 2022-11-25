package com.github.beloin.memoryalocationsimulator.models;


import com.github.beloin.memoryalocationsimulator.models.configuration.MemoryConfiguration;
import com.github.beloin.memoryalocationsimulator.utils.Listener;
import com.github.beloin.memoryalocationsimulator.utils.Observable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Memory implements Observable<Memory> {

    private final int realMemorySize;
    private final int memoryUsedByOS;
    private final Strategy strategy;

    private final List<AppProcess> processes;

    public Memory(MemoryConfiguration memoryConfiguration, List<AppProcess> processes) {
        this.realMemorySize = memoryConfiguration.getRealMemorySize();
        this.memoryUsedByOS = memoryConfiguration.getMemoryUsedByOS();
        this.strategy = memoryConfiguration.getStrategy();
        this.processes = processes;
    }


    private final List<Listener<?>> listeners = new LinkedList<>();

    @Override
    public void addListener(Listener<Memory> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Listener<Memory> listener) {
        listeners.remove(listener);
    }

    @Override
    public Memory getObject() {
        return this;
    }

    // TODO: HOW TO GET SPACES BETWEEN MEMORY? -> Use calculation and return List of spaces
    static class Space {
        int start;
        int stop;

        public int getTotal() {
            return stop - start;
        }
    }

    /**
     * Imutable List
     * @return
     */
    public List<Space> getAvalaibleSpaces() {

        return Collections.unmodifiableList(null);
    }
}
