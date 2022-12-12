package com.github.beloin.memoryalocationsimulator.views;

import com.github.beloin.memoryalocationsimulator.models.Memory;
import com.github.beloin.memoryalocationsimulator.models.MemorySpace;
import com.github.beloin.memoryalocationsimulator.utils.Listener;
import com.github.beloin.memoryalocationsimulator.utils.Observable;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.List;

public class MemoryView  implements Listener<Memory> {

    private List<MemorySpace>  spaces;

    @Override
    public void update(Observable<Memory> observable) {
        Memory mm = observable.getObject();
        Platform.runLater(() -> {
            spaces = mm.getSpaces();
            draw();
        });
    }

    private final Group parent;

    public MemoryView(Group parent, List<MemorySpace> spaces) {
        this.parent = parent;
        this.spaces = spaces;
        parent.getChildren().add(vBox);
    }

    VBox vBox = new VBox();

    public void draw() {
        int fixedSize = vBox.getChildren().size();
        for (int i = 0; i < fixedSize; i++) {
            vBox.getChildren().remove(0);
        }

        double baseX = 150;
        double baseY = 0.2;
        for (int i = spaces.size()-1; i >= 0; i--) {
            MemorySpace space = spaces.get(i);
            StackPane st = new StackPane();

            String name = space.hasProcess() ? space.getAppProcess().getName(): "VAZIO";

            Label label = new Label(name + " -> " + space.getSize());
            double y = baseY * space.getSize();

            Rectangle rect = new Rectangle(baseX, y, getRandomColor(space));
            rect.setStroke(Color.BLACK);
            rect.setStrokeWidth(4);

            st.getChildren().add(rect);
            st.getChildren().add(label);
            vBox.getChildren().add(st);
        }
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
        Color randomColor = Color.color(Math.random(), Math.random(), Math.random());
        colorMap.put(memorySpace, randomColor);

        return randomColor;
    }
}
