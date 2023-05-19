package Exceptions;

public class MaxEmployeesReachedException extends Exception {

    public MaxEmployeesReachedException(String message) {
        super(message);
    }

    public MaxEmployeesReachedException() {
        this("Max employees number reached!");
    }

}
