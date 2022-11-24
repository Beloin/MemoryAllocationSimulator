package com.github.beloin.memoryalocationsimulator.models.configuration;

public class ProcessConfiguration {
    private int occupiedMemory;

    private int duration;

    /**
     * Tempo que entrou na fila de espera para ser alocado.
     */
    private int instantiationTime;

    public int getOccupiedMemory() {
        return occupiedMemory;
    }

    public void setOccupiedMemory(int occupiedMemory) {
        this.occupiedMemory = occupiedMemory;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getInstantiationTime() {
        return instantiationTime;
    }

    public void setInstantiationTime(int instantiationTime) {
        this.instantiationTime = instantiationTime;
    }
}