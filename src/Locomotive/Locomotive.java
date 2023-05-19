package Locomotive;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import Exceptions.*;
import Generators.Generator;
import RailwayCarriages.RailwayCarriage;
import Stations.Route;
import Stations.Station;

public class Locomotive implements Runnable {
    private static int counter; // counter for id
    private Carriage head; // head of trainSet
    private int id; // id
    private String name; // name of the train
    private Route route; // current route
    private Station startStation; // initial station
    private Station currentStation; // current station
    private Station endStation; // end station
    private ArrayList<RailwayCarriage> carriagesList; // list of carriages
    private static final String shipper; // shipper of the train
    private static final int speedLimit; // speed limit
    private static final int maxCarriages; // max carriages for the trainSet
    private static final double maxWeight; // kg
    private static final int maxPowered; // maximum powered railway carriages
    private static final int initialSpeed; // initial speed of train
    private int poweredQty = 0; // powered carriages quantity
    private int carriagesQty = 0; // carriages quantity
    private int weight = 0; // weight
    private int speed; // current speed of train
    private double fullDistance; // full route distance
    private double completedFullDistance = 0; // completed route distance
    private double interDistance = 0; // distance between two stations
    private double completedInterDistance = 0; // completed distance between two stations
    private boolean goesFromInitialStation; // does train go from initial station or not

    static {
        initialSpeed = 150;
        speedLimit = 200; // kmh
        counter = 0; // counter for id
        maxCarriages = 11;
        maxWeight = ((double) maxCarriages) * 100_000.0; // kg
        maxPowered = 5; // initializing maximum powered carriages
        shipper = Generator.generateString("Shippers"); // generate a shipper for a train
    }

    public Locomotive(String name, Route route) {
        this.id = counter++;
        this.name = name;
        this.route = route;
        this.startStation = route.getStart();
        this.endStation = route.getEnd();
        this.currentStation = this.startStation;
        this.carriagesList = new ArrayList<RailwayCarriage>();
        this.fullDistance = this.route.getDistance();
        System.out.println(String.format("Train: \"%s\" created!", name));

    }

    public boolean add(RailwayCarriage carriage)
            throws MaxCarriagesExceededException, MaxPoweredCarriagesException, MaxWeightExceededException,
            CarriageAlreadyConnectedException {
        if (findCarriage(carriage)) {
            throw new CarriageAlreadyConnectedException();
        }
        if (carriagesQty + 1 > maxCarriages) {
            throw new MaxCarriagesExceededException();
        }

        if (carriage.isPowered() && poweredQty + 1 > maxPowered) {
            throw new MaxPoweredCarriagesException();
        }

        if (carriage.getNetWeight() + weight > maxWeight) {
            throw new MaxWeightExceededException("Locomotive`s weight limit reached!");
        }
        head = new Carriage(carriage, head);
        this.carriagesList.add(carriage);
        this.weight += carriage.getNetWeight();
        carriagesQty++;
        if (carriage.isPowered()) {
            poweredQty++;
        }
        return true;

    }

    public void add(ArrayList<RailwayCarriage> carriage)
            throws MaxCarriagesExceededException, MaxPoweredCarriagesException, MaxWeightExceededException,
            CarriageAlreadyConnectedException {
        for (RailwayCarriage railwayCarriage : carriage) {
            this.add(railwayCarriage);
        }
    }

    public boolean remove(RailwayCarriage carriage) throws CarriageNotFoundException {
        Carriage tmp = head;
        while (tmp != null) {
            if (tmp.carriage == carriage) {
                carriagesList.remove(carriage);
                carriagesQty--;
                weight -= carriage.getNetWeight();
                if (carriage.isPowered()) {
                    poweredQty--;
                }
                return true;
            }
            tmp = tmp.next;
        }
        return false;

    }

    private boolean findCarriage(RailwayCarriage carriage) {
        Carriage tmp = head;
        while (tmp != null) {
            if (tmp.carriage == carriage) {
                return true;
            }
            tmp = tmp.next;
        }
        return false;
    }

    public void run() {
        while (!Thread.interrupted()) {
            try {
                route.aquire(this);
            } catch (InterruptedException e) {
                System.err.println(e);
            }
            this.speed = 150;
            for (int station = 0; station < route.getRouteList().size(); station++) {
                currentStation = route.getRouteList().get(station);
                currentStation.setTrain(this);
                // System.out.println("\no " + this.name + " is on " + currentStation + "\n");
                if (station != route.getRouteList().size() - 1) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        System.err.println(e);
                    }
                    try {
                        moveNext(route.getRouteList().get(station), route.getRouteList().get(station + 1));
                    } catch (InterruptedException e) {
                        System.err.println(e);
                    } catch (RailroadHazardException e) {
                        System.err.println(e);
                    }
                }
            }

            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                System.err.println(e);
            }
            this.reset();

            if (this.goesFromInitialStation) {
                this.goesFromInitialStation = false;
            } else {
                this.goesFromInitialStation = true;
            }

            try {
                route.release();
            } catch (InterruptedException e) {
                System.err.println(e);
            }

            try {
                this.route = route.getReversed();
            } catch (RouteGenerationFailedException e) {
                System.err.println(e);
            }
        }

    }

    public void moveNext(Station currentStation, Station nextStation)
            throws InterruptedException, RailroadHazardException {
        this.completedInterDistance = 0;
        double distanceTo = currentStation.distanceTo(nextStation);
        this.interDistance = distanceTo;
        int speed = this.speed;
        int time = (int) (((this.interDistance) * 1000) / speed);
        // System.out.println(String.format("Distance to the next station: %.1fkm ",
        // this.interDistance));
        // System.out.println(String.format("Time: %ds", time));
        for (int i = 0; i < time; i++) {
            changeSpeed();
            Thread.sleep(1000);
            this.completedInterDistance += ((this.interDistance) / time);
            this.completedFullDistance += ((this.interDistance) / time);
            // System.out.println(this.completedInterDistance);

        }
        // System.out.println("Completed completedInterDistance: " + (int)
        // this.completedInterDistance);

    }

    public void reset() {
        this.completedFullDistance = 0;
        this.interDistance = 0;
        this.completedInterDistance = 0;
        this.speed = initialSpeed;
    }

    private void changeSpeed() throws RailroadHazardException {
        int changeSpeed = Generator.generateRandomNumber(0, 2);
        if (changeSpeed == 0) {
            this.speed += (int) (this.speed * (3 / 100));
        } else {
            this.speed -= (int) (this.speed * (3 / 100));
        }

        if (this.speed >= 200) {
            throw new RailroadHazardException(String.format("Train %s exceeded the max limit of %d", name, speedLimit));
        }

    }

    public String getCarriagesInfo() {
        // Sort the carriagesList by net weight
        List<RailwayCarriage> sortedCarriages = new ArrayList<>(carriagesList);
        sortedCarriages.sort(Comparator.comparingDouble(RailwayCarriage::getNetWeight));

        // Construct the string representation of the sorted carriagesList
        StringBuilder sb = new StringBuilder("[\n");
        int counter = 0;
        for (RailwayCarriage carriage : sortedCarriages) {
            sb.append("\t\t" + counter + ". " + carriage.getType()).append(",\n");
            counter++;
        }
        sb.append("\t]");
        return sb.toString();
    }

    public String getBriefTrainInfo() {
        return String.format(
                "Id: %s Name: %s",
                this.id, this.name);
    }

    public static int getMaxcarriages() {
        return maxCarriages;
    }

    public static int getMaxpowered() {
        return maxPowered;
    }

    public double getInterDistance() {
        return interDistance;
    }

    public static double getMaxweight() {
        return maxWeight;
    }

    public int getCarriagesQty() {
        return carriagesQty;
    }

    public ArrayList<RailwayCarriage> getCarriagesList() {
        return carriagesList;
    }

    public double getCompletedFullDistance() {
        return completedFullDistance;
    }

    public double getCompletedInterDistance() {
        return completedInterDistance;
    }

    public Station getCurrentStation() {
        return currentStation;
    }

    public Station getEndStation() {
        return endStation;
    }

    public double getFullDistance() {
        return fullDistance;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPoweredQty() {
        return poweredQty;
    }

    public Route getRouteList() {
        return route;
    }

    public static String getShipper() {
        return shipper;
    }

    public int getSpeed() {
        return speed;
    }

    public static int getSpeedLimit() {
        return speedLimit;
    }

    public Station getStartStation() {
        return startStation;
    }

    public int getWeight() {
        return weight;
    }

    public static void setCounter(int counter) {
        Locomotive.counter = counter;
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %s\nTrain: %s\nHome %s\nCurrent %s\nEnd %s\nCarriages: %d,\nPowered carriagesList: %d,\nWeight: %d tones",
                this.id, this.name, this.route.getStart(), this.currentStation, this.route.getEnd(), this.carriagesQty,
                this.poweredQty,
                this.weight);
    }

}
