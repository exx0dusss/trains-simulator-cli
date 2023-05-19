package Exceptions;

public class RouteGenerationFailedException extends Exception {
    public RouteGenerationFailedException(String message) {
        super(message);
    }

    public RouteGenerationFailedException() {
        this("Generation failed, end station not found!");
    }
}
