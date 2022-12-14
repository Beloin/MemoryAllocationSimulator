package com.github.beloin.memoryalocationsimulator;

import com.github.beloin.memoryalocationsimulator.models.AppProcess;
import com.github.beloin.memoryalocationsimulator.models.Memory;
import com.github.beloin.memoryalocationsimulator.models.MemorySpace;
import com.github.beloin.memoryalocationsimulator.models.Strategy;
import com.github.beloin.memoryalocationsimulator.models.configuration.EntryConfiguration;
import com.github.beloin.memoryalocationsimulator.models.configuration.MemoryConfiguration;
import com.github.beloin.memoryalocationsimulator.models.configuration.ProcessConfiguration;
import com.github.beloin.memoryalocationsimulator.views.ConfigurationView;
import com.github.beloin.memoryalocationsimulator.views.MemoryView;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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

        VBox vbox = new VBox();
        ConfigurationView confView = new ConfigurationView(initialRoot);
        confView.buildMenu((entryConfiguration) -> {
            Memory memory = setup(entryConfiguration);
            draw(initialRoot, memory);

            initialRoot.getChildren().add(vbox);
            drawPauseButton(vbox, memory);
            drawAddButtonn(vbox, memory, entryConfiguration);
        });
    }

    private void drawPauseButton(VBox parent, Memory memory) {
        Button btn = new Button("Pause/Play");
        parent.getChildren().add(btn);
        btn.setOnAction((e) -> {
            memory.playOrPause();
        });
    }

    private int lastId = 1;

    private Memory setup(EntryConfiguration entryConfiguration) {
        ConfigurationParser configurationParser = new ConfigurationParser();
        configurationParser.parse(entryConfiguration);

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
        for (ProcessConfiguration pp : processConfigurationList) {
            AppProcess appProcess = AppProcess.of(pp, lastId);
            appProcessList.add(appProcess);
            lastId++;
        }

        return new Memory(memoryConfiguration, appProcessList);
    }

    public void draw(Group initialRoot, Memory memory) {
        MemoryView mmView = new MemoryView(initialRoot, memory);
        mmView.drawMemory();
        memory.start();
    }

    public void drawAddButtonn(VBox initialRoot, Memory memory, EntryConfiguration entryConfiguration) {
        Button btn = new Button("Adicionar Processo");
        btn.setOnAction((e) -> {
            ConfigurationParser configurationParser = new ConfigurationParser();
            ProcessConfiguration pConf = configurationParser.nextProcess(entryConfiguration, memory.getNow());
            AppProcess appProcess = AppProcess.of(pConf, lastId++);
            memory.addAppProcess(appProcess);
        });

        initialRoot.getChildren().add(btn);
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

}