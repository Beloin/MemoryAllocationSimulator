package com.github.beloin.memoryalocationsimulator.utils.exceptions;

public class NoSpaceLeftException extends Exception {
    public NoSpaceLeftException() {
        super("No space left");
    }
}
