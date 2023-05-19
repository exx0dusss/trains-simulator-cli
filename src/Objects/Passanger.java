package Objects;

public class Passanger extends Person {
    private Baggage baggage; // baggage of the passsanger

    public Passanger(String name, int age, double weight, Baggage baggage) {
        super(name, age, weight);
        this.baggage = baggage;
    }

    public Passanger(String name, int age, double weight) {
        super(name, age, weight);
    }

    public Baggage getBaggage() {
        return baggage;
    }

    public void setBaggage(Baggage baggage) {
        this.baggage = baggage;
    }

    @Override
    public String toString() {
        return String.format("Name: %s, Age: %d, Weight: %.1f\nBaggage: %s",
                this.getName(), this.getAge(), this.getWeight(), this.baggage);
    }

}
