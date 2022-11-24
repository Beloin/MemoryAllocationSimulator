package com.github.beloin.memoryalocationsimulator.models;


import com.github.beloin.memoryalocationsimulator.models.configuration.MemoryConfiguration;
import com.github.beloin.memoryalocationsimulator.models.configuration.ProcessConfiguration;
import com.github.beloin.memoryalocationsimulator.models.strategies.FitStrategy;
import com.github.beloin.memoryalocationsimulator.models.strategies.StrategyFactory;
import com.github.beloin.memoryalocationsimulator.utils.Listener;
import com.github.beloin.memoryalocationsimulator.utils.Observable;
import com.github.beloin.memoryalocationsimulator.utils.exceptions.NoSpaceLeftException;
import com.github.beloin.memoryalocationsimulator.utils.exceptions.NotStartedException;

import java.util.*;

public class Memory extends Thread implements Observable<Memory> {

    private final int realMemorySize;
    private final int memoryUsedByOS;
    private final Strategy strategy;
    private final FitStrategy strategyImpl;

    private final List<AppProcess> stoppedProcess = new LinkedList<>();
    private final Queue<AppProcess> processQueue;
    private int lastProcessId;

    public Memory(MemoryConfiguration memoryConfiguration, Queue<AppProcess> processes) {
        this.realMemorySize = memoryConfiguration.getRealMemorySize();
        this.memoryUsedByOS = memoryConfiguration.getMemoryUsedByOS();
        this.strategy = memoryConfiguration.getStrategy();
        this.strategyImpl = StrategyFactory.strategy(strategy);
        this.processQueue = processes;
        this.fullSpaces = new ArrayList<>(100);

        // Adding default SO process and free space
        ProcessConfiguration osProcessConfiguration = generateOsProcess();
        AppProcess osProcess = AppProcess.of(osProcessConfiguration, 0, "OS");
        osProcess.start(0);

        fullSpaces.add(new MemorySpace(0, memoryUsedByOS, osProcess));
        fullSpaces.add(new MemorySpace(memoryUsedByOS, realMemorySize));
        lastProcessId = 0;
    }

    public int getRealMemorySize() {
        return realMemorySize;
    }

    private ProcessConfiguration generateOsProcess() {
        ProcessConfiguration osProcess = new ProcessConfiguration();
        osProcess.setOccupiedMemory(memoryUsedByOS);
        osProcess.setDuration(Integer.MAX_VALUE);
        osProcess.setInstantiationTime(0);
        return osProcess;
    }

    public Memory(MemoryConfiguration memoryConfiguration) {
        this(memoryConfiguration, new LinkedList<>());
    }

    private final List<Listener<Memory>> listeners = new LinkedList<>();

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

    @Override
    public void run() {
        int now = 0; // TODO: Update each second
        while (true) {
            // TODO: Remove process from process list if is already done.
            for (MemorySpace mm : fullSpaces) {
                if (mm.hasProcess()) {
                    try {
                        AppProcess process = mm.getAppProcess();
                        boolean hasFinished = process.hasFinished(now);
                        if (hasFinished) {
                            process.stop(now);
                            mm.removeProceess();
                            stoppedProcess.add(process);
                        }
                    } catch (NotStartedException e) {
                        e.printStackTrace();
                    }
                }
            }

            // Compacting sequential empty spaces.
            pseudoCompating();

            List<AppProcess> toRemoveList = new ArrayList<>(10);
            for (AppProcess process: processQueue) {
                int instantiationTime = process.getInstantiationTime();
                if (now >= instantiationTime) {
                    FitStrategy.Return returnSpace;
                    try {
                        returnSpace = strategyImpl.nextEmptySpace(fullSpaces, process);
                    } catch (NoSpaceLeftException e) {
                        continue;
                    }

                    MemorySpace spaceToBeAllocated = returnSpace.memorySpace;
                    MemorySpace newMemorySpace = spaceToBeAllocated.breakIn(process);
                    spaceToBeAllocated.setProcess(process);

                    if (newMemorySpace != null) {
                        fullSpaces.add(returnSpace.index + 1, newMemorySpace);
                    }

                    process.start(now);
                    toRemoveList.add(process);
                }
            }
            for (AppProcess toBeRemove : toRemoveList) {
                processQueue.remove(toBeRemove);
            }

            updateListeners();

            printMemory();
            sleepOrNot();
            now++;
        }
    }

    private void printMemory() {
        System.out.println("SPACES:");
        for (int i = fullSpaces.size()-1; i >= 0; i--) {
            MemorySpace space = fullSpaces.get(i);
            String name = space.hasProcess() ? space.getAppProcess().getName(): "VAZIO";

            System.out.printf("|  %1$10s -> %2$4s  |%n", name, space.getSize());
        }
        System.out.println("------------------------");
    }

    private void sleepOrNot() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pseudoCompating() {
        int i = 0;
        while (i != fullSpaces.size()) {
            int next = i + 1;
            if (next != fullSpaces.size()) {
                MemorySpace currentSpace = fullSpaces.get(i);
                MemorySpace nextSpace = fullSpaces.get(next);
                if (!currentSpace.hasProcess() && !nextSpace.hasProcess()) {
                    MemorySpace newSpace = currentSpace.join(nextSpace);
                    fullSpaces.add(i, newSpace);
                    fullSpaces.remove(currentSpace);
                    fullSpaces.remove(nextSpace);

                    i = 0;
                    continue;
                }
            }

            i++;
        }
    }


    private List<MemorySpace> fullSpaces;

    public List<MemorySpace> getSpaces() {
        return Collections.unmodifiableList(fullSpaces);
    }

    public List<MemorySpace> getFreeSpaces() {
        return getSpaces().stream().filter(sp -> !sp.hasProcess()).toList();
    }

    @Override
    public void updateListeners() {
        for (Listener<Memory> listener : listeners) {
            listener.update(this);
        }
    }
}
