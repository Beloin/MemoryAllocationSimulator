package com.github.beloin.memoryalocationsimulator.models;

import java.util.Objects;

// TODO: HOW TO GET SPACES BETWEEN MEMORY? -> Use calculation and return List of spaces
public class MemorySpace {
    private MemorySpace(int start, int stop, boolean hasProcess) {
        this.start = start;
        this.stop = stop;
        this.hasProcess = hasProcess;
    }

    public MemorySpace(int start, int stop) {
        this(start, stop, false);
    }

    public MemorySpace(int start, int stop, AppProcess appProcess) {
        this(start, stop, true);
        this.appProcess = appProcess;
    }


    private final int start;
    private int stop;

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

    public void removeProceess() {
        hasProcess = false;
        appProcess = null;
    }

    public int getSize() {
        return stop - start;
    }

    public int getStart() {
        return start;
    }

    public int getStop() {
        return stop;
    }

    public MemorySpace breakIn(AppProcess process) {
        int memory = process.getOccupiedMemory();

        if (memory == getSize()) {
            return null;
        }

        int newStartSpace = start + memory;
        int newStopSpace = this.stop;

        this.stop = start + memory;

        return new MemorySpace(newStartSpace, newStopSpace, false);
    }

    public MemorySpace join(MemorySpace other) {
        if (this.hasProcess || other.hasProcess) {
            return null;
        }

        int newStart;
        int newStop;

        int compareInt = this.start - other.start;
        if (compareInt < 0) {
            newStart = this.start;
            newStop = other.stop;
        } else {
            newStart = other.start;
            newStop = this.stop;
        }

        return new MemorySpace(newStart, newStop, false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemorySpace that = (MemorySpace) o;
        return start == that.start && stop == that.stop && hasProcess == that.hasProcess && Objects.equals(appProcess, that.appProcess);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, stop, hasProcess, appProcess);
    }
}