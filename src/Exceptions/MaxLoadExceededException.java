package Exceptions;

public class MaxLoadExceededException extends Exception {
    public MaxLoadExceededException(String message) {
        super(message);
    }

    public MaxLoadExceededException() {
        this("Max weight exceeded!");
    }
}
