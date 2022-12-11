package com.github.beloin.memoryalocationsimulator;

import com.github.beloin.memoryalocationsimulator.models.configuration.EntryConfiguration;
import com.github.beloin.memoryalocationsimulator.models.configuration.MemoryConfiguration;
import com.github.beloin.memoryalocationsimulator.models.configuration.ProcessConfiguration;

import java.util.LinkedList;
import java.util.List;

public class ConfigureData {

    private List<ProcessConfiguration> processConfigurations = new LinkedList<>();
    private MemoryConfiguration memoryConfiguration;

    public void configure(EntryConfiguration entryConfiguration){
        // ...
//        processConfigurations.add(...)
        MemoryConfiguration memoryConfiguration = new MemoryConfiguration();
        memoryConfiguration.setMemoryUsedByOS(entryConfiguration.getMemoryUsedByOS());
        memoryConfiguration.setRealMemorySize(entryConfiguration.getRealMemorySize());
        memoryConfiguration.setStrategy(entryConfiguration.getStrategy());
        this.memoryConfiguration = memoryConfiguration;


    }

    public static void main(String[] args) {
//        EntryConfiguration
//        ConfigureData configureData = new ConfigureData();
//        configureData.configure();
    }
}
