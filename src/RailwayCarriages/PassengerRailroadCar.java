package RailwayCarriages;

import java.util.ArrayList;

import Exceptions.*;
import Objects.*;

// Passenger RailRoad Car 
public class PassengerRailroadCar extends RailwayCarriage {
    private static final int maxPassangers;
    private ArrayList<Passanger> passangerArrayList = new ArrayList<Passanger>(); // list of passangers
    private Passanger[][][] passangerList = new Passanger[2][20][2]; // physical passangers
    private Baggage[][][] baggageList = new Baggage[2][20][2]; // physical baggage
    private int passangerQty = 0, baggageQty = 0; // passanger and baggage quantity

    /*
     * 01 ⬛⬛ ⬛⬛
     * 02 ⬛⬛ ⬛⬛
     * 03 ⬛⬛ ⬛⬛
     * 04 ⬛⬛ ⬛⬛
     * 05 ⬛⬛ ⬛⬛
     * 06 ⬛⬛ ⬛⬛
     * 07 ⬛⬛ ⬛⬛
     * 08 ⬛⬛ ⬛⬛
     * 09 ⬛⬛ ⬛⬛
     * 10 ⬛⬛ ⬛⬛
     * 12 ⬛⬛ ⬛⬛
     * 13 ⬛⬛ ⬛⬛
     * 14 ⬛⬛ ⬛⬛
     * 15 ⬛⬛ ⬛⬛
     * 16 ⬛⬛ ⬛⬛
     * 17 ⬛⬛ ⬛⬛
     * 18 ⬛⬛ ⬛⬛
     * 19 ⬛⬛ ⬛⬛
     * 20 ⬛⬛ ⬛⬛
     */

    static {
        maxPassangers = 80;
    }

    public PassengerRailroadCar(String shipper) {
        super(shipper);
        this.type = CarriageType.PassengerRailroadCar;
        this.powered = true;
        this.measure = "kg";
        this.grossLoad = maxPassangers; // seats
        this.grossWeight = 200 * grossLoad; // kg (weight * quantity )
    }

    public boolean addPassanger(Passanger passanger) throws MaxLoadExceededException {
        if (this.netLoad + 1 > grossLoad) {
            throw new MaxLoadExceededException("No free seats!");
        }
        if (this.netWeight + passanger.getWeight() > grossWeight) {
            throw new MaxLoadExceededException("Passenger weight limit exceeded!");
        }
        for (int side = 0; side < passangerList.length; side++) {
            for (int column = 0; column < passangerList[side].length; column++) {
                for (int row = 0; row < passangerList[side][column].length; row++) {
                    if (this.passangerList[side][column][row] == null) {
                        this.passangerList[side][column][row] = passanger;
                        this.baggageList[side][column][row] = passanger.getBaggage();
                        this.passangerArrayList.add(passanger);
                        this.passangerQty++;
                        this.netWeight += passanger.getWeight();
                        this.netLoad++;
                        System.out.println("Passanger " + passanger.getName() + " got on the train!");
                        return true;
                    }
                }
            }
        }
        return false;

    }

    public void addPassangers(ArrayList<Passanger> passangerList) throws MaxLoadExceededException {
        for (Passanger passanger : passangerList) {
            this.addPassanger(passanger);
        }
    }

    public boolean removePassanger(Passanger passanger) {
        if (this.passangerArrayList.remove(passanger)) {
            this.netLoad--;
            this.netWeight -= passanger.getWeight();
        }
        for (int side = 0; side < passangerList.length; side++) {
            for (int column = 0; column < passangerList[side].length; column++) {
                for (int row = 0; row < passangerList[side][column].length; row++) {
                    if (this.passangerList[side][column][row] == passanger) {
                        this.passangerList[side][column][row] = null;
                        this.baggageList[side][column][row] = null;
                        System.out.println("Removed successfully!");
                        return true;
                    }
                }
            }
        }
        return false;

    }

    public void removePassanger(ArrayList<Passanger> passangerList) {
        for (Passanger passanger : passangerList) {
            this.removePassanger(passanger);
        }

    }

    public void displayPassangers() {
        int counter = 0;
        if (this.passangerList[0][0][0] == null) {
            System.out.println("No passangers!");
            return;
        }
        System.out.println("{\nPassengers:\n    [\n");
        mainloop: for (int side = 0; side < this.passangerList.length; side++) {
            for (int column = 0; column < this.passangerList[side].length; column++) {
                for (int row = 0; row < this.passangerList[side][column].length; row++) {
                    if (this.passangerList[side][column][row] == null)
                        break mainloop;
                    Passanger passanger = this.passangerList[side][column][row];
                    System.out.println(String.format("\t%d.", counter++));
                    printTabLines(passanger);
                    System.out.println(
                            String.format("\tSide: [ %5s, %3s ]",
                                    (side == 0 ? "left" : "right"),
                                    (row % 2 == 0 ? ((column + 1) + "a") : ((column + 1) + "b"))));
                    System.out.println();

                }

            }

        }
        System.out.println("    ]\n}");
    }

    public void displaySeats() {
        System.out.println("{\nSeats\n    [\n");
        for (int side = 0; side < this.passangerList.length; side++) {
            for (int column = 0; column < this.passangerList[side].length; column++) {
                for (int row = 0; row < this.passangerList[side][column].length; row++) {
                    if (this.passangerList[side][column][row] == null) {
                        System.out.println("\tfree");
                    } else {
                        Passanger passanger = this.passangerList[side][column][row];
                        printTabLines(passanger);
                    }
                    System.out.println(
                            String.format(
                                    "\tSide: [ %5s, %3s ]",
                                    (side == 0 ? "left" : "right"),
                                    (row % 2 == 0 ? ((column + 1) + "a") : ((column + 1) + "b"))));
                    System.out.println();
                }

            }

        }
        System.out.println("    ]\n}");
    }

    public Passanger[][][] getPassangerList() {
        return passangerList;
    }

    public ArrayList<Passanger> getPassangerArrayList() {
        return passangerArrayList;
    }

    public int getPassangerQty() {
        return passangerQty;
    }

    public int getBaggageQty() {
        return baggageQty;
    }

    @Override
    public String toString() {
        return String.format("Type: %s,\nIndex: %d,\nCurrent weight load: %.2fkg,\nPeople quantity: %d",
                this.getType(), this.getId(), this.netWeight, this.netLoad);
    }

}
