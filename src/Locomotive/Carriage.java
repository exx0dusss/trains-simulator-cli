package Locomotive;

import RailwayCarriages.RailwayCarriage;

public class Carriage {
    Carriage next; // link to the next carriage (node)
    RailwayCarriage carriage; // data of the carriage (data)

    Carriage(RailwayCarriage carriage, Carriage next) {
        this.carriage = carriage;
        this.next = next;
    }

    Carriage(RailwayCarriage carriage) {
        this(carriage, null);
    }

}
