package com.github.beloin.memoryalocationsimulator.models;

import com.github.beloin.memoryalocationsimulator.models.configuration.ProcessConfiguration;

public class AppProcess {
    private int occupiedMemory;

    private int duration;

    /**
     * Tempo que entrou na fila de espera para ser alocado.
     */
    private int instantiationTime;

    public static AppProcess of(ProcessConfiguration pConfiguration) {
        AppProcess appProcess = new AppProcess();
        appProcess.duration = pConfiguration.getDuration();
        appProcess.instantiationTime = pConfiguration.getInstantiationTime();
        appProcess.occupiedMemory = pConfiguration.getOccupiedMemory();
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

    public int getTimeLeft(int currentTime) throws Exception {
        if (!hasStarted) throw new Exception("Not started");
        int timePassed = currentTime - startTime;
        return duration - timePassed;
    }

    public boolean hasFinished(int currentTime) throws Exception {
        return getTimeLeft(currentTime) == 0;
    }

    public int getWaitTime() throws Exception {
        if (!hasStopped) throw new Exception("Yet Running");
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
        } else{
            if (hasStopped) {
                return ProcessStatus.STTOPED;
            }

            return ProcessStatus.IDLE;
        }
    }
}
