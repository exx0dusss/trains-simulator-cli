package Exceptions;

public class MailStackFullException extends Exception {
    public MailStackFullException(String message) {
        super(message);

    }

    public MailStackFullException() {
        this("Mailstack is full!");
    }

}
