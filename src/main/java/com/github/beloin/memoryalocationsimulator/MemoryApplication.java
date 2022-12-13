package com.github.beloin.memoryalocationsimulator;

import com.github.beloin.memoryalocationsimulator.models.AppProcess;
import com.github.beloin.memoryalocationsimulator.models.Memory;
import com.github.beloin.memoryalocationsimulator.models.Strategy;
import com.github.beloin.memoryalocationsimulator.models.configuration.EntryConfiguration;
import com.github.beloin.memoryalocationsimulator.models.configuration.MemoryConfiguration;
import com.github.beloin.memoryalocationsimulator.models.configuration.ProcessConfiguration;
import com.github.beloin.memoryalocationsimulator.views.MemoryView;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class MemoryApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        BorderPane borderPane = new BorderPane();
        Group initialRoot = new Group();
        borderPane.setCenter(initialRoot);
        Scene scene = new Scene(borderPane, 800, 800);
        stage.setTitle("Memory Application");
        stage.setScene(scene);
        stage.show();

        draw(initialRoot, setup());
    }

    private Memory setup() {
        EntryConfiguration configuration = getEntryConfiguration();
        ConfigurationParser configurationParser = new ConfigurationParser();
        configurationParser.parse(configuration);

        MemoryConfiguration memoryConfiguration = configurationParser.getMemoryConfiguration();
        List<ProcessConfiguration> processConfigurationList = configurationParser.getProcessConfigurationsList();

        System.out.println("Memory Configuration: " + memoryConfiguration.getMemoryUsedByOS());
        System.out.println("Memory Configuration: " + memoryConfiguration.getRealMemorySize());
        System.out.println("Memory Configuration: " + memoryConfiguration.getStrategy());
        System.out.println("\n");

        for (int i = 0; i < processConfigurationList.size(); i++) {
            System.out.println("Process " + i);
            System.out.println("Duration = " + processConfigurationList.get(i).getDuration());
            System.out.println("OccupiedMemory = " + processConfigurationList.get(i).getOccupiedMemory());
            System.out.println("InstantiationTime = " + processConfigurationList.get(i).getInstantiationTime());
            System.out.println("\n");
        }

        List<AppProcess> appProcessList = new LinkedList<>();
        int id = 1;
        for (ProcessConfiguration pp : processConfigurationList) {
            AppProcess appProcess = AppProcess.of(pp, id);
            appProcessList.add(appProcess);
            id++;
        }



        return new Memory(memoryConfiguration, appProcessList);
    }

    public void draw(Group initialRoot, Memory memory) {
        MemoryView mmView = new MemoryView(initialRoot, memory);
        mmView.drawMemory();
        memory.start();
    }

    public EntryConfiguration getEntryConfiguration() {
        EntryConfiguration entry = new EntryConfiguration();

        entry.setMemoryUsedByOS(200);
        entry.setRealMemorySize(1500);
        entry.setStrategy(Strategy.BEST_FIT);

        entry.setMemoryIntervalStart(10);
        entry.setMemoryIntervalEnd(350);
        entry.setInstantiationIntervalStart(1);
        entry.setInstantiationIntervalEnd(10);
        entry.setProcessDurationIntervalStart(15);
        entry.setProcessDurationIntervalEnd(30);
        entry.setProccessCount(5);

        return entry;
    }

    public List<AppProcess> createAppProcesses() {
        ProcessConfiguration configuration01 = new ProcessConfiguration();
        configuration01.setDuration(10);
        configuration01.setOccupiedMemory(100);
        configuration01.setInstantiationTime(2);
        AppProcess app01 = AppProcess.of(configuration01, 1);

        ProcessConfiguration configuration02 = new ProcessConfiguration();
        configuration02.setDuration(10);
        configuration02.setOccupiedMemory(100);
        configuration02.setInstantiationTime(8);
        AppProcess app02 = AppProcess.of(configuration02, 2);

        ProcessConfiguration configuration03 = new ProcessConfiguration();
        configuration03.setDuration(10);
        configuration03.setOccupiedMemory(800);
        configuration03.setInstantiationTime(3);
        AppProcess app03 = AppProcess.of(configuration03, 3);

        ProcessConfiguration configuration04 = new ProcessConfiguration();
        configuration04.setDuration(10);
        configuration04.setOccupiedMemory(50);
        configuration04.setInstantiationTime(6);
        AppProcess app04 = AppProcess.of(configuration04, 4);

        return new LinkedList<>(List.of(app01, app02, app03, app04));
    }

    public List<AppProcess> createAppProcesses2() {
        ProcessConfiguration configuration01 = new ProcessConfiguration();
        configuration01.setDuration(10);
        configuration01.setOccupiedMemory(100);
        configuration01.setInstantiationTime(2);
        AppProcess app01 = AppProcess.of(configuration01, 1);


        ProcessConfiguration configuration03 = new ProcessConfiguration();
        configuration03.setDuration(11);
        configuration03.setOccupiedMemory(350);
        configuration03.setInstantiationTime(3);
        AppProcess app03 = AppProcess.of(configuration03, 3);


        ProcessConfiguration configuration02 = new ProcessConfiguration();
        configuration02.setDuration(25);
        configuration02.setOccupiedMemory(100);
        configuration02.setInstantiationTime(5);
        AppProcess app02 = AppProcess.of(configuration02, 2);

        ProcessConfiguration configuration04 = new ProcessConfiguration();
        configuration04.setDuration(10);
        configuration04.setOccupiedMemory(50);
        configuration04.setInstantiationTime(13);
        AppProcess app04 = AppProcess.of(configuration04, 4);

        return new LinkedList<>(List.of(app01, app02, app03, app04));
    }
}