package com.github.beloin.memoryalocationsimulator.models.configuration;

import com.github.beloin.memoryalocationsimulator.models.Strategy;

public class EntryConfiguration {
    private Strategy strategy;

    private int proccessCount;

    // Aways in Megabytes
    private int realMemorySize;
    private int memoryUsedByOS;

    // M1
    private int memoryIntervalStart;
    // M2
    private int memoryIntervalEnd;

    // Aways in seconds
    // TC1
    private int instantiationIntervalStart;
    // TC2
    private int instantiationIntervalEnd;

    // Aways in seconds
    // TP1
    private int processDurationIntervalStart;
    // TP2
    private int processDurationIntervalEnd;


    public int getProccessCount() {
        return proccessCount;
    }

    public void setProccessCount(int proccessCount) {
        this.proccessCount = proccessCount;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

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

    public int getMemoryIntervalStart() {
        return memoryIntervalStart;
    }

    public void setMemoryIntervalStart(int memoryIntervalStart) {
        this.memoryIntervalStart = memoryIntervalStart;
    }

    public int getMemoryIntervalEnd() {
        return memoryIntervalEnd;
    }

    public void setMemoryIntervalEnd(int memoryIntervalEnd) {
        this.memoryIntervalEnd = memoryIntervalEnd;
    }

    public int getInstantiationIntervalStart() {
        return instantiationIntervalStart;
    }

    public void setInstantiationIntervalStart(int instantiationIntervalStart) {
        this.instantiationIntervalStart = instantiationIntervalStart;
    }

    public int getInstantiationIntervalEnd() {
        return instantiationIntervalEnd;
    }

    public void setInstantiationIntervalEnd(int instantiationIntervalEnd) {
        this.instantiationIntervalEnd = instantiationIntervalEnd;
    }

    public int getProcessDurationIntervalStart() {
        return processDurationIntervalStart;
    }

    public void setProcessDurationIntervalStart(int processDurationIntervalStart) {
        this.processDurationIntervalStart = processDurationIntervalStart;
    }

    public int getProcessDurationIntervalEnd() {
        return processDurationIntervalEnd;
    }

    public void setProcessDurationIntervalEnd(int processDurationIntervalEnd) {
        this.processDurationIntervalEnd = processDurationIntervalEnd;
    }

    @Override
    public String toString() {
        return "EntryConfiguration{" +
                "strategy=" + strategy +
                ", proccessCount=" + proccessCount +
                ", realMemorySize=" + realMemorySize +
                ", memoryUsedByOS=" + memoryUsedByOS +
                ", memoryIntervalStart=" + memoryIntervalStart +
                ", memoryIntervalEnd=" + memoryIntervalEnd +
                ", instantiationIntervalStart=" + instantiationIntervalStart +
                ", instantiationIntervalEnd=" + instantiationIntervalEnd +
                ", processDurationIntervalStart=" + processDurationIntervalStart +
                ", processDurationIntervalEnd=" + processDurationIntervalEnd +
                '}';
    }
}
