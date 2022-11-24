package com.github.beloin.memoryalocationsimulator.models.strategies;

import com.github.beloin.memoryalocationsimulator.models.Strategy;

public class StrategyFactory {

    public static FitStrategy strategy(Strategy strategy) {
        return switch (strategy) {
            case BEST_FIT -> new BestFit();
            case FIRST_FIT -> new FisrtFit();
            case WORST_FIT -> new WorstFit();
        };
    }
}
