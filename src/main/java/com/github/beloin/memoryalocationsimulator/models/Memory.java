package com.github.beloin.memoryalocationsimulator.models;


import com.github.beloin.memoryalocationsimulator.models.configuration.MemoryConfiguration;
import com.github.beloin.memoryalocationsimulator.utils.Listener;
import com.github.beloin.memoryalocationsimulator.utils.Observable;

import java.util.*;

public class Memory implements Observable<Memory> {

    private final int realMemorySize;
    private final int memoryUsedByOS;
    private final Strategy strategy;

    private final List<AppProcess> runningProcesses = new LinkedList<>();
    private final List<AppProcess> stoppedProcess = new LinkedList<>();
    private final Queue<AppProcess> processQueue;

    public Memory(MemoryConfiguration memoryConfiguration, Queue<AppProcess> processes) {
        this.realMemorySize = memoryConfiguration.getRealMemorySize();
        this.memoryUsedByOS = memoryConfiguration.getMemoryUsedByOS();
        this.strategy = memoryConfiguration.getStrategy();
        this.processQueue = processes;
        this.fullSpaces = new ArrayList<>(100);


        // TODO: How to make SOProcess -> Process that cannot be removed
        // TODO: Duration = MAX_INTEGER?
        // TODO: tag: canBeRemoved?
        fullSpaces.add(new MemorySpace(0, memoryUsedByOS));
        fullSpaces.add(new MemorySpace(0, memoryUsedByOS));
    }

    public Memory(MemoryConfiguration memoryConfiguration) {
        this(memoryConfiguration, new LinkedList<>());
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

    public void addAppProcess(AppProcess process) {
        this.processQueue.add(process);
    }

    void run() {
        // TODO: Remove process from process list if is already done.
        for (AppProcess process : runningProcesses) {
        }

        // TODO: Get process from queue to add to list if has any left space
        for (AppProcess process : processQueue) {
        }
    }



    // TODO: SEE HOW WE WILL SEE THE MEMORY:
    // TODO: LIST OF SPACES? OR ONLY MEMORY

    private final List<MemorySpace> fullSpaces;

    public List<MemorySpace> getSpaces() {
        for (AppProcess process : runningProcesses) {
        }
        return Collections.unmodifiableList(null);
    }

    public List<MemorySpace> getFreeSpaces() {
        List<MemorySpace> spaces = getSpaces().stream().filter(sp -> !sp.hasProcess()).toList();
        return spaces;
    }
}
