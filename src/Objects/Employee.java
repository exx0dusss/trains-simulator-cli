package Objects;

public class Employee extends Person {
    protected String job; // person`s job

    public Employee(String name, int age, int weight, String job) {
        super(name, age, weight);
        this.job = job;
    }

    public String getJob() {
        return this.job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return String.format("Name: %s, Age: %d, Weight: %d\nJob: %s",
                this.getName(), this.getAge(), this.getWeight(), this.job);
    }
}
