package Exceptions;

public class MaxPoweredCarriagesException extends Exception {
    public MaxPoweredCarriagesException(String message) {
        super(message);

    }

    public MaxPoweredCarriagesException() {
        this("Limit of connected powered carriages reached!");
    }

}
