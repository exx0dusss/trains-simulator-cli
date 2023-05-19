package Objects;

public class Mail {
    private String address; // mail address
    private String message; // ma8l message

    public Mail(String address, String message) {
        this.address = address;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return String.format("Address: %s\nMessage: %s",
                this.address, this.message);
    }
}
