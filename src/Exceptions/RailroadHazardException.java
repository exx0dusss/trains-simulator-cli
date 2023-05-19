package Exceptions;

import Locomotive.Locomotive;

public class RailroadHazardException extends Exception {

    public RailroadHazardException(String message) {
        super(message);

    }

    public RailroadHazardException(Locomotive train) {
        this("Warning! Train exceeded 200 km/h!\n" + train);

    }

    public RailroadHazardException() {
        this("Warning! Train exceeded 200 km/h!");

    }

}
