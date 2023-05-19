package Main;

import java.util.*;

import Exceptions.*;
import Objects.*;
import RailwayCarriages.RailroadRestaurant;
import Stations.*;
import Generators.*;
import Locomotive.Locomotive;

public class Presentation {
    public static void main(String[] args) {
        // use the method here | classNamePresentation() |
        // railroadRestaurantPresentation();
        // railroadHazardPresentation();

    }

    private static void railroadHazardPresentation() {
        try {
            Locomotive train = new Locomotive("null", Generator.generateRoute(Generator.generateStationMap(25)));
            throw new RailroadHazardException(train);
        } catch (RouteGenerationFailedException e) {
            e.printStackTrace();
        } catch (RailroadHazardException e) {
            e.printStackTrace();
        }
    }

    private static void railroadRestaurantPresentation() {
        try {
            RailroadRestaurant car1 = new RailroadRestaurant(Generator.generateString("Shippers"));
            car1.addDrinks(Generator.generateDrinks(10));
            car1.addMeals(Generator.generateMeals(10));
            car1.displayFood();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private static void personPresentation() {
        Person person = new Person("Bob", 20, 72.0);
        System.out.println(person.getAge());
        System.out.println(person.getName());
        System.out.println(person.getWeight());
        System.out.println(person);
        System.out.println();
    }

    private static void passangerPresentation() {
        Passanger passanger = new Passanger("Michael", 20, 72.0, new Baggage("thing, thing, thing"));
        System.out.println(passanger.getAge());
        System.out.println(passanger.getName());
        System.out.println(passanger.getWeight());
        System.out.println(passanger.getBaggage());
        System.out.println(passanger);
        passanger.setBaggage(new Baggage("newThing, thing, thing"));
        System.out.println(passanger);
    }

    private static void baggagePresentation() {
        Baggage baggage = new Baggage("string, string, string");
        System.out.println(baggage.getItems());
        System.out.println(baggage);
        baggage.setItems("newString, string, string");
        System.out.println(baggage);
    }

    private static void cargoPresentation() {
        Cargo cargo1 = new Cargo("Box of books", 25.5, "A box full of Java programming books.");
        System.out.println(cargo1);
        cargo1.setName("Box of clothes");
        System.out.println(cargo1);
        cargo1.setWeight(32.0);
        System.out.println(cargo1);
        Cargo cargo2 = new Cargo("Large crate", 200.0);
        System.out.println(cargo2);
    }

    private static void foodPresentation() {
        Meal meal = new Meal("Meal", 30.0, 0.3, "Just meal");
        Drink drink = new Drink("drink", 30.0, 0.3, "Just drink");
        System.out.println(meal);
        System.out.println();
        System.out.println(drink);
    }

    private static void mailPresentation() {
        MailStack mailStack = new MailStack();
        Mail mail = new Mail("Address", "message");
        try {
            mailStack.addMail(mail);
        } catch (MailStackFullException e) {
            e.printStackTrace();
        }
        mailStack.displayMails();
    }

    private static void generatorPresentation() {
        ArrayList<Station> stations = new ArrayList<Station>();
        StationMap stationMap;
        stations = Generator.generateStations(400);
        stationMap = new StationMap(stations);
        stationMap.printMap();
        Station.setMax(50); // get ready to open console full screen ;D
        System.out.println();
        stations = Generator.generateStations(400);
        stationMap = new StationMap(stations);
        stationMap.printMap();
        // Also you can just type | Generator. | and see which methods it has (A LOT...)

    }

    private static void stationsPresentation() {
        ArrayList<Station> stations = new ArrayList<Station>();
        stations = Generator.generateStations(400);
        StationMap stationMap = new StationMap(stations);
        for (Station station : stations) {
            station.printConnectedStations();
        }

    }

}
