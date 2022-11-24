package com.github.beloin.memoryalocationsimulator;

import com.github.beloin.memoryalocationsimulator.models.Memory;
import com.github.beloin.memoryalocationsimulator.models.configuration.EntryConfiguration;
import com.github.beloin.memoryalocationsimulator.views.MemoryView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

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

        Rectangle rectangle = new Rectangle();
    }

    public void draw() {
        Memory mm = new Memory();
        MemoryView mmView = new MemoryView();
        mm.addListener(mmView);


    }
}