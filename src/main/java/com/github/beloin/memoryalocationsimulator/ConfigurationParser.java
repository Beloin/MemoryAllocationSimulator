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

    public static void main(String[] args) {
        EntryConfiguration entry = new EntryConfiguration();

        entry.setMemoryUsedByOS(5);
        entry.setRealMemorySize(1000);
        entry.setStrategy(Strategy.BEST_FIT);

        entry.setMemoryIntervalStart(10);
        entry.setMemoryIntervalEnd(350);
        entry.setInstantiationIntervalStart(1);
        entry.setInstantiationIntervalEnd(10);
        entry.setProcessDurationIntervalStart(15);
        entry.setProcessDurationIntervalEnd(30);
        entry.setProccessCount(5);

        ConfigurationParser configurationParser = new ConfigurationParser();
        configurationParser.parse(entry);

        List<ProcessConfiguration> myProcessList = configurationParser.getProcessConfigurationsList();

        MemoryConfiguration memConfig = configurationParser.getMemoryConfiguration();

        System.out.println("Memory Configuration: " + memConfig.getMemoryUsedByOS());
        System.out.println("Memory Configuration: " + memConfig.getRealMemorySize());
        System.out.println("Memory Configuration: " + memConfig.getStrategy());
        System.out.println("\n");

        for (int i = 0; i < entry.getProccessCount(); i++) {
            System.out.println("Process " + i);
            System.out.println("Duration = " + myProcessList.get(i).getDuration());
            System.out.println("OccupiedMemory = " + myProcessList.get(i).getOccupiedMemory());
            System.out.println("InstantiationTime = " + myProcessList.get(i).getInstantiationTime());
            System.out.println("\n");
        }
    }


}