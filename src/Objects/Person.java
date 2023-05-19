package Objects;

public class Person {
    private int age; // age of the person
    private String name; // name of the person
    private double weight; // weight of the person

    public Person(String name, int age, double weight) {
        this.name = name;
        this.age = age;
        this.weight = weight;
    }

    public int getAge() {
        return this.age;
    }

    public String getName() {
        return this.name;
    }

    public double getWeight() {
        return this.weight;
    }

    @Override
    public String toString() {
        return String.format("Name: %s, Age: %d, Weight: %.1f",
                this.name, this.age, this.weight);
    }

}
