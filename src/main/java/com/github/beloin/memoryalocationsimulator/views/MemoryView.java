package com.github.beloin.memoryalocationsimulator.views;

import com.github.beloin.memoryalocationsimulator.models.AppProcess;
import com.github.beloin.memoryalocationsimulator.models.Memory;
import com.github.beloin.memoryalocationsimulator.models.MemorySpace;
import com.github.beloin.memoryalocationsimulator.utils.Listener;
import com.github.beloin.memoryalocationsimulator.utils.Observable;
import com.github.beloin.memoryalocationsimulator.utils.exceptions.NotStartedException;
import com.github.beloin.memoryalocationsimulator.utils.exceptions.YetRunnningException;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MemoryView implements Listener<Memory> {

    private List<MemorySpace> spaces;

    @Override
    public void update(Observable<Memory> observable) {
        Platform.runLater(() -> {
            spaces = memory.getSpaces();
            drawMemory();
        });
    }

    private final Group parent;
    private final Memory memory;

    public MemoryView(Group parent, Memory memory) {
        this.parent = parent;
        this.spaces = memory.getSpaces();
        this.memory = memory;
        memory.addListener(this);

        // View config
        HBox hBox = new HBox();

        hBox.getChildren().add(moreInfoVbox);
        hBox.getChildren().add(vBox);
        hBox.getChildren().add(queueVbox);
        hBox.setSpacing(10);


        parent.getChildren().add(hBox);
    }


    private final VBox vBox = new VBox();
    VBox queueVbox = new VBox();
    VBox moreInfoVbox = new VBox();

    public void drawMemory() {
        vBox.getChildren().clear();

        double baseX = 180;
        double baseY = 0.5;
        Label memoryLabel = new Label("Memory -> " + memory.getRealMemorySize() + "mb");
        vBox.getChildren().add(memoryLabel);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(0);
        for (int i = spaces.size() - 1; i >= 0; i--) {
            MemorySpace space = spaces.get(i);
            StackPane st = new StackPane();

            VBox labelVbox = new VBox();
            labelVbox.setSpacing(5);
            labelVbox.setAlignment(Pos.CENTER);


            String name = space.hasProcess() ? space.getAppProcess().getName() : "VAZIO";
            Label label = new Label(name + " -> " + space.getSize() + "mb");
            double y = baseY * space.getSize();

            Rectangle rect = new Rectangle(baseX, y, getRandomColor(space));
            rect.setStroke(Color.BLACK);
            rect.setStrokeWidth(.5);

            labelVbox.getChildren().add(label);

            if (space.hasProcess()) {
                try {
                    Label timeLabel = new Label("Time Left -> " + space.getAppProcess().getTimeLeft(memory.getNow()) + "s");
                    labelVbox.getChildren().add(timeLabel);
                } catch (NotStartedException e) {
                    throw new RuntimeException(e);
                }
            }


            st.getChildren().add(rect);
            st.getChildren().add(labelVbox);
            vBox.getChildren().add(st);
        }

        drawQueue();
        drawMoreInfo();
    }

    public void drawQueue() {
        queueVbox.getChildren().clear();
        List<AppProcess> queue = memory.getProcessQueue();
        for (int i = queue.size() - 1; i >= 0; i--) {
            AppProcess appProcess = queue.get(i);
            Label txt = new Label(String.format(
                    "%s -> %dmb em %ds durante %ds",
                    appProcess.getName(), appProcess.getOccupiedMemory(),
                    appProcess.getTimeToStart(memory.getNow()),
                    appProcess.getDuration()
            ));
            queueVbox.getChildren().add(txt);
        }

        queueVbox.setAlignment(Pos.CENTER_LEFT);
    }

    public void drawMoreInfo() {
        moreInfoVbox.getChildren().clear();
        List<AppProcess> doneProcesses = memory.getStoppedProcess();
        int i;
        int totalWaitTime = 0;
        for (i = 0; i < doneProcesses.size(); i++) {
            AppProcess appProcess = doneProcesses.get(i);
            try {
                Label processInfo = new Label(String.format(
                        "%s -> Start: %d, Stop: %d | Espera = %d",
                        appProcess.getName(),
                        appProcess.getStartTime(), appProcess.getEndTime(),
                        appProcess.getWaitTime()
                ));
                moreInfoVbox.getChildren().add(processInfo);
                totalWaitTime += appProcess.getWaitTime();
            } catch (YetRunnningException e) {
                e.printStackTrace();
            }
        }

        moreInfoVbox.setAlignment(Pos.CENTER_LEFT);
        Label moreInfo = new Label(String.format("Tempo de espera médio: %.2f", (double) totalWaitTime / (double) i));
        moreInfoVbox.getChildren().add(moreInfo);
    }

    private final HashMap<MemorySpace, Color> colorMap = new HashMap<>();

    public Color getRandomColor(MemorySpace memorySpace) {
        if (!memorySpace.hasProcess()) {
            return Color.WHITE;
        }

        boolean containsKey = colorMap.containsKey(memorySpace);
        if (containsKey) {
            return colorMap.get(memorySpace);
        }
        Color randomColor = Color.color(getRandomColor(), getRandomColor(), getRandomColor());
        colorMap.put(memorySpace, randomColor);

        return randomColor;
    }

    private final Random random = new Random();

    private double getRandomColor() {
        return random.nextDouble(0.35, 1);
    }
}
