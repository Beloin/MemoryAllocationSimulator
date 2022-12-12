package com.github.beloin.memoryalocationsimulator;

import com.github.beloin.memoryalocationsimulator.models.AppProcess;
import com.github.beloin.memoryalocationsimulator.models.Memory;
import com.github.beloin.memoryalocationsimulator.models.Strategy;
import com.github.beloin.memoryalocationsimulator.models.configuration.MemoryConfiguration;
import com.github.beloin.memoryalocationsimulator.models.configuration.ProcessConfiguration;
import com.github.beloin.memoryalocationsimulator.views.MemoryView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MemoryApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        BorderPane borderPane = new BorderPane();
        Group initialRoot = new Group();
        borderPane.setCenter(initialRoot);
        Scene scene = new Scene(borderPane, 400, 400);
        stage.setTitle("Memory Application");
        stage.setScene(scene);
        stage.show();

        draw(initialRoot);
    }

    public void draw(Group initialRoot) {
        MemoryConfiguration configuration = new MemoryConfiguration();
        configuration.setStrategy(Strategy.FIRST_FIT);
        configuration.setMemoryUsedByOS(100);
        configuration.setRealMemorySize(1000);

        Queue<AppProcess> appProcesses = createAppProcesses2();

        Memory mm = new Memory(configuration, appProcesses);

        MemoryView mmView = new MemoryView(initialRoot, mm);
        mm.addListener(mmView);
        mmView.draw();

        mm.start();
    }

    public Queue<AppProcess> createAppProcesses() {
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
    public Queue<AppProcess> createAppProcesses2() {
        ProcessConfiguration configuration01 = new ProcessConfiguration();
        configuration01.setDuration(10);
        configuration01.setOccupiedMemory(100);
        configuration01.setInstantiationTime(2);
        AppProcess app01 = AppProcess.of(configuration01, 1);



        ProcessConfiguration configuration03 = new ProcessConfiguration();
        configuration03.setDuration(10);
        configuration03.setOccupiedMemory(700);
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
        configuration04.setInstantiationTime(6);
        AppProcess app04 = AppProcess.of(configuration04, 4);

        return new LinkedList<>(List.of(app01, app02, app03, app04));
    }
}