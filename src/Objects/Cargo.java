package Objects;

public class Cargo implements Freight {
    protected String name; // name of the cargo
    protected double weight; // weight of the cargo
    protected String description; // description of the cargo

    public Cargo(String name, double weight, String description) {
        this.name = name;
        this.weight = weight;
        this.description = description;
    }

    public Cargo(String name, double weight) {
        this(name, weight, "no description");
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("Cargo: %s\nWeight: %.1fkg\nDescription: %s",
                this.name, this.weight, this.description);
    }
}
