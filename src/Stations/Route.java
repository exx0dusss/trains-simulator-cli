package Stations;

import java.util.*;
import java.util.concurrent.Semaphore;

import Exceptions.RouteGenerationFailedException;
import Locomotive.Locomotive;

public class Route {
    private Station start; // start station
    private Station end; // end station
    private Locomotive train; // route train
    private ArrayList<Station> route; // route list
    private Route reversed; // back route
    private Semaphore semaphore; // collision

    public Route(Station start, Station end) throws RouteGenerationFailedException {
        this.start = start;
        this.end = end;
        this.semaphore = new Semaphore(1);
        this.createPath();

    }

    public void aquire(Locomotive train) throws InterruptedException {
        this.semaphore.acquire();
        this.train = train;
    }

    public void release() throws InterruptedException {
        this.semaphore.release();
        this.train = null;
    }

    public Station getStart() {
        return start;
    }

    public Station getEnd() {
        return end;
    }

    public Route getReversed() throws RouteGenerationFailedException {
        this.reversed = new Route(end, start);
        return reversed;
    }

    public Locomotive getTrain() {
        return train;
    }

    public void setTrain(Locomotive train) {
        this.train = train;
    }

    // Breadth-First Search algorithm
    private void createPath() throws RouteGenerationFailedException {
        route = new ArrayList<Station>(); // keep track of each station's parent station in the path.
        HashMap<Station, Station> parentMap = new HashMap<>();
        Queue<Station> queue = new LinkedList<>(); // store the stations to visit.
        HashSet<Station> visited = new HashSet<>(); // keep track of the stations that have already been visited.
        parentMap.put(start, null);
        queue.add(start);
        visited.add(start);
        boolean found = false;
        while (!queue.isEmpty()) {
            Station curr = queue.poll();
            if (curr == end) {
                found = true;
                break;
            }
            for (Station next : curr.getConnectedStations()) {
                if (!visited.contains(next)) {
                    parentMap.put(next, curr);
                    queue.add(next);
                    visited.add(next);
                }
            }
        }
        if (!found) {
            throw new RouteGenerationFailedException();
        }
        Stack<Station> stack = new Stack<>();
        Station curr = end;
        while (curr != null) {
            stack.push(curr);
            curr = parentMap.get(curr);
        }
        while (!stack.isEmpty()) {
            Station station = stack.pop();
            this.route.add(station);
        }
    }

    public double getDistance() {
        double distance = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            double interDistance = route.get(i).distanceTo(route.get(i + 1));
            distance += interDistance;
        }
        return distance;
    }

    public ArrayList<Station> getRouteList() {
        return route;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("{\nRoute: %s ---> %s\n    [", start.getCoordsInfo(), end.getCoordsInfo()))
                .append("\n\t o ").append(route.get(0));
        for (int i = 1; i < route.size(); i++) {
            sb.append("\n\t-> ").append(route.get(i));
        }
        sb.append("\n    ]\n}");
        return sb.toString();
    }
}
