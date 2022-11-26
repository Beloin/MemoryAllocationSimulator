package com.github.beloin.memoryalocationsimulator.models;

// TODO: HOW TO GET SPACES BETWEEN MEMORY? -> Use calculation and return List of spaces
public class MemorySpace {
    public MemorySpace(int start, int stop) {
        this.start = start;
        this.stop = stop;
        hasProcess = false;
    }

    private final int start;
    private final int stop;


    private boolean hasProcess;
    private AppProcess appProcess;

    public void setProcess(AppProcess appProcess) {
        this.appProcess = appProcess;
        hasProcess = true;
    }

    public AppProcess getAppProcess() {
        return appProcess;
    }

    public boolean hasProcess() {
        return hasProcess;
    }

    public int getTotal() {
        return stop - start;
    }

    public int getStart() {
        return start;
    }

    public int getStop() {
        return stop;
    }
}