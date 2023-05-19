package Exceptions;

public class CarriageAlreadyConnectedException extends Exception {

    public CarriageAlreadyConnectedException(String message) {
        super(message);
    }

    public CarriageAlreadyConnectedException() {
        this("Carriage is already connected!");
    }

}
