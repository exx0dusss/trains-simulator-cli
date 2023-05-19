package Objects;

public abstract class Food extends Cargo {
    private double price; // price of the food

    public Food(String name, double price, double weight) {
        super(name, weight);
        this.price = price;
    }

    public Food(String name, double price, double weight, String description) {
        super(name, weight, description);
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void set(String name, double price) {
        this.setName(name);
        this.setPrice(price);
    }

    public String toString() {
        return String.format("Name: %s\nPrice: %.2f pln\nWeight: %.2fg",
                this.name, this.price, this.weight);
    }

}
