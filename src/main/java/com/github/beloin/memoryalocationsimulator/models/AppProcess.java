package com.github.beloin.memoryalocationsimulator.models;

import com.github.beloin.memoryalocationsimulator.models.configuration.ProcessConfiguration;
import com.github.beloin.memoryalocationsimulator.utils.exceptions.NotStartedException;
import com.github.beloin.memoryalocationsimulator.utils.exceptions.YetRunnningException;

public class AppProcess {
    private AppProcess() {
    }

    private int occupiedMemory;

    private int duration;

    private int id;
    private String name;

    /**
     * Tempo que entrou na fila de espera para ser alocado.
     */
    private int instantiationTime;

    public static AppProcess of(ProcessConfiguration pConfiguration, int id) {
        AppProcess appProcess = new AppProcess();
        appProcess.duration = pConfiguration.getDuration();
        appProcess.instantiationTime = pConfiguration.getInstantiationTime();
        appProcess.occupiedMemory = pConfiguration.getOccupiedMemory();
        appProcess.id = id;
        appProcess.name = String.format("Process %d", id);
        return appProcess;
    }

    public static AppProcess of(ProcessConfiguration pConfiguration, int id, String name) {
        AppProcess appProcess = AppProcess.of(pConfiguration, id);
        appProcess.name = name;
        return appProcess;
    }

    private int startTime;
    private boolean hasStarted;
    private int endTime;
    private boolean hasStopped;

    public void start(int time) {
        if (hasStarted || hasStopped) return;
        startTime = time;
        hasStarted = true;
    }

    public void stop(int time) {
        if (hasStopped) return;
        if (!hasStarted) return;

        this.endTime = time;
        hasStopped = true;
    }

    public int getTimeLeft(int currentTime) throws NotStartedException {
        if (!hasStarted) throw new NotStartedException();
        int timePassed = currentTime - startTime;
        return duration - timePassed;
    }

    public boolean hasFinished(int currentTime) throws NotStartedException {
        return getTimeLeft(currentTime) == 0;
    }

    public boolean hasStarted() {
        return hasStarted;
    }

    public int getWaitTime() throws YetRunnningException {
        if (!hasStopped) throw new YetRunnningException();
        return endTime - instantiationTime;
    }

    public enum ProcessStatus {
        STTOPED,
        RUNNING,
        IDLE
    }

    public ProcessStatus getStatus() {
        if (hasStarted && !hasStopped) {
            return ProcessStatus.RUNNING;
        } else {
            if (hasStopped) {
                return ProcessStatus.STTOPED;
            }

            return ProcessStatus.IDLE;
        }
    }

    public int getInstantiationTime() {
        return instantiationTime;
    }

    public int getOccupiedMemory() {
        return occupiedMemory;
    }

    public int getDuration() {
        return duration;
    }

    public int getId() {
        return id;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "AppProcess{" +
                "name='" + name + '\'' +
                '}';
    }

    protected void resetDuration(int now) {
        this.startTime = now;
    }

    public int getTimeToStart(int now) {
        return instantiationTime - now;
    }
}
