package com.github.beloin.memoryalocationsimulator;

import com.github.beloin.memoryalocationsimulator.models.Strategy;
import com.github.beloin.memoryalocationsimulator.models.configuration.EntryConfiguration;
import com.github.beloin.memoryalocationsimulator.models.configuration.MemoryConfiguration;
import com.github.beloin.memoryalocationsimulator.models.configuration.ProcessConfiguration;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ConfigurationParser {

    private final List<ProcessConfiguration> processConfigurations = new LinkedList<>();
    private final MemoryConfiguration memoryConfiguration = new MemoryConfiguration();

    public void parse(EntryConfiguration entryConfiguration) {
        memoryConfiguration.setMemoryUsedByOS(entryConfiguration.getMemoryUsedByOS());
        memoryConfiguration.setRealMemorySize(entryConfiguration.getRealMemorySize());
        memoryConfiguration.setStrategy(entryConfiguration.getStrategy());

        int instantiationIntervalStart = entryConfiguration.getInstantiationIntervalStart();
        int instantiationIntervalEnd = entryConfiguration.getInstantiationIntervalEnd();
        int memoryIntervalStart = entryConfiguration.getMemoryIntervalStart();
        int memoryIntervalEnd = entryConfiguration.getMemoryIntervalEnd();
        int processDurationIntervalStart = entryConfiguration.getProcessDurationIntervalStart();
        int processDurationIntervalEnd = entryConfiguration.getProcessDurationIntervalEnd();

        int proccessCount = entryConfiguration.getProccessCount();

        for (int i = 0; i < proccessCount; i++) {
            ProcessConfiguration process = new ProcessConfiguration();

            int instantiationTime = getRandomNumber(instantiationIntervalStart, instantiationIntervalEnd);
            int occupiedMemory = getRandomNumber(memoryIntervalStart, memoryIntervalEnd);
            int processDuration = getRandomNumber(processDurationIntervalStart, processDurationIntervalEnd);

            if (i > 0) {
                instantiationTime = processConfigurations.get(i - 1).getInstantiationTime() + instantiationTime;
            }

            process.setOccupiedMemory(occupiedMemory);
            process.setDuration(processDuration);
            process.setInstantiationTime(instantiationTime);

            processConfigurations.add(process);
        }
    }

    public List<ProcessConfiguration> getProcessConfigurationsList() {
        return processConfigurations;
    }

    public MemoryConfiguration getMemoryConfiguration() {
        return memoryConfiguration;
    }

    public static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.ints(min, max + 1)
                .findFirst()
                .getAsInt();
    }
}