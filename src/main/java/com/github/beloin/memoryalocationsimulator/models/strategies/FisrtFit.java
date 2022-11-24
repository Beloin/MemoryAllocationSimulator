package com.github.beloin.memoryalocationsimulator.models.strategies;

import com.github.beloin.memoryalocationsimulator.models.AppProcess;
import com.github.beloin.memoryalocationsimulator.models.MemorySpace;
import com.github.beloin.memoryalocationsimulator.utils.exceptions.NoSpaceLeftException;

import java.util.List;

public class FisrtFit implements FitStrategy {
    @Override
    public Return nextEmptySpace(List<MemorySpace> spaces, AppProcess process) throws NoSpaceLeftException {
        int index = 0;
        for (MemorySpace space : spaces) {
            if (!space.hasProcess()) {
                if (process.getOccupiedMemory() <= space.getSize()) return new Return(space, index);
            }
            index++;
        }

        throw new NoSpaceLeftException();
    }
}
