package com.github.beloin.memoryalocationsimulator.views;

import com.github.beloin.memoryalocationsimulator.models.Memory;
import com.github.beloin.memoryalocationsimulator.utils.Listener;
import com.github.beloin.memoryalocationsimulator.utils.Observable;
import javafx.scene.canvas.Canvas;

public class MemoryView extends Canvas implements Listener<Memory> {

    @Override
    public void update(Observable<Memory> observable) {
        Memory mm = observable.getObject();
    }
}
