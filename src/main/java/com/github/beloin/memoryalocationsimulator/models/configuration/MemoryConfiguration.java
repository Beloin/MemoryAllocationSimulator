package com.github.beloin.memoryalocationsimulator.models.configuration;

import com.github.beloin.memoryalocationsimulator.models.Strategy;

public class MemoryConfiguration {
    /**
     * Tamanho total da memória
     */
    private int realMemorySize;

    private int memoryUsedByOS;

    private Strategy strategy;

    public int getRealMemorySize() {
        return realMemorySize;
    }

    public void setRealMemorySize(int realMemorySize) {
        this.realMemorySize = realMemorySize;
    }

    public int getMemoryUsedByOS() {
        return memoryUsedByOS;
    }

    public void setMemoryUsedByOS(int memoryUsedByOS) {
        this.memoryUsedByOS = memoryUsedByOS;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
}
