package Objects;

public class Liquid extends Cargo {

    public Liquid(String name, double volume) {
        super(name, volume);
    }

    public Liquid(String name, double volume, String description) {
        super(name, volume, description);
    }

    public class Toxic extends Cargo {

        public Toxic(String name, double volume, String description) {
            super(name, volume, description);
        }

        public Toxic(String name, double volume) {
            super(name, volume);
        }
    }

}
