package Stations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class StationMap {
    protected ArrayList<Station> stations = new ArrayList<Station>();
    private static int min; // min value of map coords
    private static int max; // max value of map coords

    public StationMap() {
    }

    public StationMap(ArrayList<Station> stations) {
        this.stations = stations;
        this.sort();
        this.connectStations();

    }

    private void sort() {
        if (stations.size() == 0) {
            return;
        }
        for (int i = 0; i < stations.size() - 1; i++) {
            if (stations.get(i).getLatitude() > stations.get(i + 1).getLatitude()) {
                Collections.swap(stations, i, i + 1);
                i = -1;
            }
        }
    }

    private void connectStations() { // minimum spanning tree algorithm
        if (stations.size() == 0) { // Check if there are any stations to connect
            System.out.println("Nothing to connect!");
            return;
        }
        for (Station station : stations) { // Remove all existing connections
            station.removeConnections();
        }
        Set<Station> connected = new HashSet<>(); // Connect the stations
        connected.add(stations.get(0)); // Start with the first station

        while (connected.size() < stations.size()) {
            double minDistance = Double.POSITIVE_INFINITY;
            Station closestStation = null;
            Station currentStation = null;
            // Find the closest unconnected station to the current connected set
            for (Station station : connected) {
                for (Station otherStation : stations) {
                    if (!connected.contains(otherStation)) {
                        double distance = station.distanceTo(otherStation);
                        if (distance < minDistance) {
                            minDistance = distance;
                            closestStation = otherStation;
                            currentStation = station;
                        }
                    }
                }
            }

            // Connect the closest station to the current connected set
            connected.add(closestStation);
            currentStation.addNext(closestStation);
        }
    }

    public ArrayList<Station> getStations() {
        return stations;
    }

    public void printStations() {
        System.out.println("Nothing to print!");

        for (Station station : stations) {
            System.out.println(station);
        }
    }

    public void printConnections() {
        System.out.println("Nothing to print!");
        for (Station station : stations) {
            station.printConnectedStations();

        }
    }

    private boolean draw(int x, int y) {
        for (Station station : stations) {
            if (station.getLongitude() == y) {
                if (station.getLatitude() == x) {
                    return true;
                }
            }
        }
        return false;
    }

    private void formatter(int max) {
        System.out.print("   ");
        for (int x = 0; x < max * 3; x++) {
            System.out.print("_");
        }
        System.out.println();

    }

    public boolean add(Station station) {
        if (stations.add(station)) {
            return true;
        }
        return false;
    }

    public void add(ArrayList<Station> stations) {
        for (Station station : stations) {
            this.add(station);
        }
    }

    public boolean removeStation(Station station) {
        if (stations.size() == 0) {
            System.out.println("Nothing to connect!");
            return false;
        }
        for (Station s : this.stations) {
            s.removeConnection(station);
        }
        if (this.stations.remove(station)) {
            this.connectStations();
            return true;
        }
        return false;

    }

    public void printMap() {
        if (stations == null) {
            System.out.println("No stations!");
            return;
        }
        min = Station.getMin();
        max = Station.getMax();
        // FORMAT
        formatter(max);
        // ALGORITHM
        for (int y = min; y <= max; y++) {
            System.out.print(String.format("%2d|", y));
            for (int x = min; x <= max; x++) {
                if (draw(x, y)) {
                    System.out.print("*  ");
                } else {
                    System.out.print("   ");
                }

            }
            System.out.println();
        }

        // FORMAT
        formatter(max);
        System.out.print("   ");
        for (int x = min; x <= max; x++) {
            System.out.print(String.format("%2d", x) + " ");
        }
        System.out.println();

    }
}
