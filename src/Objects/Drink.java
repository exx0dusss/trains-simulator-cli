package Objects;

public class Drink extends Food {
    public Drink(String name, double price, double weight) {
        super(name, price, weight);
    }

    public Drink(String name, double price, double weight, String description) {
        super(name, price, weight, description);
    }

}
