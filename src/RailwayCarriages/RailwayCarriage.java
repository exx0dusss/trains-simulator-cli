package RailwayCarriages;

public abstract class RailwayCarriage {
    private static int counter = 0; // counter of carriages
    private int id; // index of each carriage
    protected String shipper; // shipper of the carriage
    protected double grossWeight; // max weight
    protected double netWeight = 0.0; // pure weight
    protected double grossLoad; // max load
    protected double netLoad = 0.0; // pure load
    protected CarriageType type; // type of the carriage
    protected boolean powered = false; // connection to the e-grid;
    protected String measure; // measure points
    protected String securityInfo; // security info

    public RailwayCarriage(String shipper) {
        this.id = counter++;
        this.shipper = shipper;
        this.securityInfo = String.format("Security in a %s"
                + " involves physical security, staff training, and fire safety", this.type);
    }

    public int getId() {
        return this.id;
    }

    public double getNetWeight() {
        return this.netWeight;
    }

    public double getNetLoad() {
        return this.netLoad;
    }

    public double getGrossLoad() {
        return grossLoad;
    }

    public double getGrossWeight() {
        return grossWeight;
    }

    public boolean isPowered() {
        return powered;
    }

    public int getCounter() {
        return counter;
    }

    public CarriageType getType() {
        return type;
    }

    public String getSecurityInfo() {
        return securityInfo;
    }

    public static void setCounter(int n) {
        RailwayCarriage.counter = n;
    }

    public String getShipper() {
        return shipper;
    }

    public String getMeasure() {
        return measure;
    }

    // to split each toString() onto separate lines
    protected <Thing> void printTabLines(Thing thing) {
        String[] lines = thing.toString().split("\n");
        for (String line : lines) {
            System.out.println("\t" + line);
        }
    }

    public String displayInfo() {
        return String.format(
                "Shipper: %s,\nType: %s,\nMax weight: %d %s,\nMax Load: %d,\nConnected to electrical grid: %b",
                this.shipper, this.type, this.grossWeight, this.measure, this.grossLoad,
                this.powered);
    }

    @Override
    abstract public String toString();

}
