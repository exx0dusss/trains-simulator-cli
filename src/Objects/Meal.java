package Objects;

public class Meal extends Food {
    public Meal(String name, double price, double weight) {
        super(name, price, weight);
    }

    public Meal(String name, double price, double weight, String description) {
        super(name, price, weight, description);
    }

}
