package com.github.beloin.memoryalocationsimulator.models.strategies;

import com.github.beloin.memoryalocationsimulator.models.AppProcess;
import com.github.beloin.memoryalocationsimulator.models.MemorySpace;
import com.github.beloin.memoryalocationsimulator.utils.exceptions.NoSpaceLeftException;

import java.util.List;

public class WorstFit implements FitStrategy{
    @Override
    public Return nextEmptySpace(List<MemorySpace> spaces, AppProcess process) throws NoSpaceLeftException {
        MemorySpace best = null;
        boolean hasSet = false;
        int minimumLeftSpace = Integer.MIN_VALUE;
        int index = 0;
        for (int i = 0; i < spaces.size(); i++) {
            MemorySpace space = spaces.get(i);
            int leftSpace = space.getSize() - process.getOccupiedMemory();

            if (!space.hasProcess()) {
                if (process.getOccupiedMemory() <= space.getSize()) {
                    if (best == null) {
                        best = space;
                        minimumLeftSpace = leftSpace;
                        hasSet = true;
                        index = i;
                        continue;
                    }

                    if (minimumLeftSpace < leftSpace) {
                        best = space;
                        minimumLeftSpace = leftSpace;
                        hasSet = true;
                        index = i;
                    }
                }
            }


        }

        if (!hasSet) {
            throw new NoSpaceLeftException();
        }

        return new Return(best, index);
    }
}
