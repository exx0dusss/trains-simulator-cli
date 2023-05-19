package Stations;

import java.util.*;

import Locomotive.Locomotive;

public class Station {
    private String name; // station name
    private int latitude; // latitude - x
    private int longitude; // longitude - y
    private ArrayList<Station> connectedStations; // list of connected stations
    private Locomotive train; // train on that route
    private static int min, max; // min max coordinate
    static {
        min = 1;
        max = 20;
    }

    public Station(String name, int latitude, int longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        connectedStations = new ArrayList<Station>();

    }

    public void addNext(Station station) { // connect stations vice-versa
        this.connectedStations.add(station);
        station.connectedStations.add(this);
    }

    public boolean removeConnection(Station station) { // remove connection to some station
        if (this.connectedStations.remove(station)
                && station.connectedStations.remove(this)) {
            return true;
        }
        return false;

    }

    public void removeConnections() { // use only iterating all stations
        this.connectedStations = new ArrayList<Station>();
    }

    public ArrayList<Station> getConnectedStations() {
        return this.connectedStations;
    }

    public void printConnectedStations() {
        if (connectedStations != null) {
            System.out.print(String.format("(%2d, %2d) ->", this.latitude, this.longitude));
            for (Station station : connectedStations) {
                System.out.print(String.format(" (%2d, %2d), ", station.latitude, station.longitude));
            }
            System.out.println();
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public static void setMax(int max) {
        Station.max = max;
    }

    public static void setMin(int min) {
        Station.min = min;
    }

    public static int getMax() {
        return max;
    }

    public static int getMin() {
        return min;
    }

    public void setTrain(Locomotive train) {
        this.train = train;
    }

    public Locomotive getTrain() {
        return train;
    }

    public double distanceTo(Station other) { // Euclidean distance
        int dx = latitude - other.latitude; // delta x
        int dy = longitude - other.longitude; // delta y
        return Math.sqrt(dx * dx + dy * dy);
    }

    public String getCoordsInfo() {
        return String.format("(%2d, %2d)",
                latitude, longitude);
    }

    @Override
    public String toString() {
        return String.format("Station: --- [%s] --- Coordinates: (%2d, %2d)",
                this.name, this.latitude, this.longitude);

    }

}
