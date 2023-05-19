package Objects;

public class Baggage {
    String items; // list of items (str)

    public Baggage(String items) {
        this.items = items;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return items;
    }
}
