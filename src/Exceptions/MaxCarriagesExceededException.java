package Exceptions;

public class MaxCarriagesExceededException extends Exception {
    MaxCarriagesExceededException(String message) {
        super(message);
    }

    public MaxCarriagesExceededException() {
        this("Reached limit of the carriages");
    }
}
