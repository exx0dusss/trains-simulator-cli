package Exceptions;

public class MaxWeightExceededException extends Exception {
    public MaxWeightExceededException(String message) {
        super(message);
    }

    public MaxWeightExceededException() {
        this("Max load exceeded!");
    }
}
