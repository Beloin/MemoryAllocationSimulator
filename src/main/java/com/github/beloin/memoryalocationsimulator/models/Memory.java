package com.github.beloin.memoryalocationsimulator.models;


import com.github.beloin.memoryalocationsimulator.models.configuration.MemoryConfiguration;
import com.github.beloin.memoryalocationsimulator.models.configuration.ProcessConfiguration;
import com.github.beloin.memoryalocationsimulator.utils.Listener;
import com.github.beloin.memoryalocationsimulator.utils.Observable;

import java.util.*;

public class Memory implements Observable<Memory> {

    private final int realMemorySize;
    private final int memoryUsedByOS;
    private final Strategy strategy;

    private final List<AppProcess> stoppedProcess = new LinkedList<>();
    private final Queue<AppProcess> processQueue;
    private int lastProcessId;

    public Memory(MemoryConfiguration memoryConfiguration, Queue<AppProcess> processes) {
        this.realMemorySize = memoryConfiguration.getRealMemorySize();
        this.memoryUsedByOS = memoryConfiguration.getMemoryUsedByOS();
        this.strategy = memoryConfiguration.getStrategy();
        this.processQueue = processes;
        this.fullSpaces = new ArrayList<>(100);

        // Adding default SO process and free space
        ProcessConfiguration osProcess = generateOsProcess();
        fullSpaces.add(new MemorySpace(0, memoryUsedByOS, AppProcess.of(osProcess, 0, "OS")));
        fullSpaces.add(new MemorySpace(memoryUsedByOS, realMemorySize));
        lastProcessId = 0;
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

    void run() throws Exception {
        int now = 0; // TODO: Update each second

        // TODO: Remove process from process list if is already done.
        for (MemorySpace mm : fullSpaces) {
            if (mm.hasProcess()) {
                AppProcess process = mm.getAppProcess();
                if (process.hasFinished(now)) {
                    process.stop(now);
                    mm.removeProceess();
                    stoppedProcess.add(process);
                }
            }
        }

        // TODO: Update Memory -> This is not working, find another way
        boolean hasStarted = false;
        int emptyStart = 0;
        int emptyEnd = 0;
        class EmptyThing {
            int emptyStart;
            int emptyEnd;

            int size() {
                return emptyEnd - emptyStart;
            }
        }
        List<EmptyThing> emptyThingList = new LinkedList<>(); // Already ordered
        for (int i = 0; i < fullSpaces.size(); i++) {
            MemorySpace memorySpace = fullSpaces.get(i);
            if (!memorySpace.hasProcess()) {
                if (!hasStarted) {
                    emptyStart = i;
                    emptyEnd = i;
                    hasStarted = true;
                } else {
                    emptyEnd = i;
                }
            } else {
                if (hasStarted) {
                    EmptyThing n = new EmptyThing();
                    n.emptyEnd = emptyEnd;
                    n.emptyStart = emptyStart;
                    hasStarted = false;
                    emptyThingList.add(n);
                }
            }
        }


        // TODO: Get process from queue to add to list if has any left space
        for (AppProcess process : processQueue) {
        }

        updateListeners();
    }


    // TODO: SEE HOW WE WILL SEE THE MEMORY:
    // TODO: LIST OF SPACES? OR ONLY MEMORY
    private List<MemorySpace> fullSpaces;

    public List<MemorySpace> getSpaces() {
        return Collections.unmodifiableList(fullSpaces);
    }

    public List<MemorySpace> getFreeSpaces() {
        List<MemorySpace> spaces = getSpaces().stream().filter(sp -> !sp.hasProcess()).toList();
        return spaces;
    }

    @Override
    public void updateListeners() {
        for (Listener<Memory> listener : listeners) {
            listener.update(this);
        }
    }
}
