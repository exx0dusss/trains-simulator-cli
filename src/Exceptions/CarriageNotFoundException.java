package Exceptions;

public class CarriageNotFoundException extends Exception {

    public CarriageNotFoundException(String message) {
        super(message);
    }

    public CarriageNotFoundException() {
        this("Carriage not found!");
    }

}
