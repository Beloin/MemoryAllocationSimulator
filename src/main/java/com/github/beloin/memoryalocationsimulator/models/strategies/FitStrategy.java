package com.github.beloin.memoryalocationsimulator.models.strategies;

import com.github.beloin.memoryalocationsimulator.models.AppProcess;
import com.github.beloin.memoryalocationsimulator.models.MemorySpace;
import com.github.beloin.memoryalocationsimulator.utils.exceptions.NoSpaceLeftException;

import java.util.List;

public interface FitStrategy {
    class Return {
        public MemorySpace memorySpace;
        public int index;

        public Return(MemorySpace memorySpace, int index) {
            this.memorySpace = memorySpace;
            this.index = index;
        }
    }
    Return nextEmptySpace(List<MemorySpace> spaces, AppProcess process) throws NoSpaceLeftException;
}
