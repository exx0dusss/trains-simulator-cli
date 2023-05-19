package Generators;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import Objects.*;
import Locomotive.*;
import RailwayCarriages.*;
import Stations.*;
import Exceptions.*;

public abstract class Generator {
    static final String path = "src/TextFiles/"; // path to the directory with
                                                                                           // text files

    public static ArrayList<Mail> generateMails(int n) {
        ArrayList<Mail> mails = new ArrayList<Mail>();
        for (int i = 0; i < n; i++) {
            mails.add(generateMail());
        }
        return mails;
    }

    public static ArrayList<Station> generateStations(int n) {
        ArrayList<Station> stations = new ArrayList<Station>();
        int num = 0;
        while (num < n) {
            Station station = generateStation();
            boolean found = false;
            for (Station s : stations) {
                if (s.getLatitude() == station.getLatitude() &&
                        s.getLongitude() == station.getLongitude()) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                stations.add(station);
                num++;
            }
        }
        return stations;
    }

    public static Station generateStation() {
        int min = Station.getMin();
        int max = Station.getMax();
        int x = generateRandomNumber(min, max);
        int y = generateRandomNumber(min, max);
        return new Station(generateString("Stations"), x, y);

    }

    public static int generateRandomNumber(int min, int max) {
        int range = max - min + 1;
        return (int) (Math.random() * range) + min;
    }

    public static double generateRandomNumber(double min, double max) {
        double range = max - min + 1;
        return (Math.random() * range) + min;
    }

    public static String generateString(String filename) {
        Path thisPath = Paths.get(path + filename + ".txt");
        List<String> lines;
        try {
            lines = Files.readAllLines(thisPath);
        } catch (IOException e) {
            System.out.println("Error in reading file");
            return "";
        }

        if (lines.isEmpty()) {
            return "";
        }

        int n = generateRandomNumber(0, lines.size() - 1);
        return lines.get(n);
    }

    public static ArrayList<RailwayCarriage> generateCarriages(int n) {
        int maxPowered = Locomotive.getMaxpowered();
        ArrayList<RailwayCarriage> carriages = new ArrayList<RailwayCarriage>();

        int poweredQty = generateRandomNumber(1, maxPowered);
        for (int i = 0; i < poweredQty; i++) {
            carriages.add(generatePowered(generateString("Shippers")));

        }

        for (int i = 0; i < n - poweredQty; i++) {
            carriages.add(generateNonPowered(generateString("Shippers")));

        }

        return carriages;
    }

    public static RailwayCarriage generatePowered(String shipper) {
        int min = 1;
        int max = 4;
        int randomNumber = generateRandomNumber(min, max);
        RailwayCarriage carriage;
        try {
            switch (randomNumber) {
                case 1:
                    carriage = new PassengerRailroadCar(shipper);
                    break;
                case 2:
                    carriage = new BasicFreightCar(shipper).new Refrigerated(shipper);
                    break;
                case 3:
                    carriage = new RailroadPostOffice(shipper);
                    break;
                case 4:
                    carriage = new RailroadRestaurant(shipper);
                    break;
                default:
                    System.out.println("Invalid number!");
                    carriage = null;
                    break;
            }
        } catch (Exception e) {
            carriage = null;
        }
        return carriage;
    }

    public static RailwayCarriage generateNonPowered(String shipper) {
        int min = 1;
        int max = 8;
        int randomNumber = generateRandomNumber(min, max);
        RailwayCarriage carriage;
        try {
            switch (randomNumber) {
                case 1:
                    carriage = new RailroadBaggageAndMailCar(shipper);
                    break;
                case 2:
                    carriage = new BasicFreightCar(shipper);
                    break;
                case 3:
                    carriage = new HeavyFreightCar(shipper);
                    break;
                case 4:
                    carriage = new BasicFreightCar(shipper).new ForLiquid(shipper);
                    break;
                case 5:
                    carriage = new BasicFreightCar(shipper).new ForGaseous(shipper);
                    break;
                case 6:
                    carriage = new HeavyFreightCar(shipper).new ForExplosives(shipper);
                    break;
                case 7:
                    carriage = new HeavyFreightCar(shipper).new ForToxic(shipper);
                    break;
                case 8:
                    carriage = new HeavyFreightCar(shipper).new ForLiquidToxic(shipper);
                    break;
                default:
                    System.out.println("Invalid number!");
                    carriage = null;
                    break;
            }
        } catch (Exception e) {
            carriage = null;
        }
        return carriage;
    }

    public static ArrayList<Passanger> generatePassangers(int n) {
        ArrayList<Passanger> passangers = new ArrayList<Passanger>();

        for (int i = 0; i < n; i++) {
            passangers.add(generatePassanger());
        }

        return passangers;
    }

    public static Passanger generatePassanger() {
        int age = generateRandomNumber(1, 100);
        int weight = generateRandomNumber(50, 100);
        return new Passanger(generateString("Names"), age, weight, generateBaggage());
    }

    public static Baggage generateBaggage() {
        return new Baggage(generateString("Baggage"));
    }

    public static Mail generateMail() {
        return new Mail(generateString("Addresses"), generateString("Mails"));
    }

    public static Route generateRoute(StationMap stations) throws RouteGenerationFailedException {
        Station start = stations.getStations().get(generateRandomNumber(0, stations.getStations().size() - 1));
        Station end = stations.getStations().get(generateRandomNumber(0, stations.getStations().size() - 1));
        return new Route(start, end);

    }

    public static StationMap generateStationMap(int n) {
        ArrayList<Station> stations = generateStations(n);
        return new StationMap(stations);
    }

    public static Locomotive generateLocomotive(Route route) {
        return new Locomotive(generateString("Trains"), route);
    }

    public static Locomotive generateLocomotive(StationMap map) throws RouteGenerationFailedException {
        Route route = generateRoute(map);
        return new Locomotive(generateString("Trains"), route);
    }

    public static Cargo generateCargo() {
        double weight = generateRandomNumber(0.0, 1000.0);
        return new Cargo(generateString("Cargo"), weight);
    }

    public static ArrayList<Cargo> generateCargo(int n) {
        ArrayList<Cargo> cargo = new ArrayList<Cargo>();
        for (int i = 0; i < n; i++) {
            cargo.add(generateCargo());
        }
        return cargo;
    }

    public static Toxic generateToxic() {
        double weight = generateRandomNumber(0.0, 1000.0);
        return new Toxic(generateString("Toxic"), weight);
    }

    public static Liquid.Toxic generateLiquidToxic() {
        double weight = generateRandomNumber(0.0, 1000.0);
        Liquid liquid = new Liquid("", 0);
        return liquid.new Toxic(generateString("Toxic"), weight);
    }

    public static Liquid generateLiquid() {
        double weight = generateRandomNumber(0.0, 1000.0);
        return new Liquid(generateString("Liquid"), weight);
    }

    public static Gaseous generateGaseous() {
        double weight = generateRandomNumber(0.0, 1000.0);
        return new Gaseous(generateString("Gaseous"), weight);
    }

    public static Explosive generateExplosive() {
        double weight = generateRandomNumber(0.0, 1000.0);
        return new Explosive(generateString("Explosive"), weight);
    }

    public static Drink generateDrink() {
        double price = generateRandomNumber(10, 200);
        double weight = generateRandomNumber(300, 100);
        return new Drink(generateString("Drinks"), price, weight);
    }

    public static Meal generateMeal() {
        double price = generateRandomNumber(10, 200);
        double weight = generateRandomNumber(300, 100);
        return new Meal(generateString("Meals"), price, weight);
    }

    public static Food generateFood() {
        int choice = generateRandomNumber(0, 1);
        if (choice == 0) {
            return generateDrink();
        }
        return generateMeal();
    }

    public static ArrayList<Toxic> generateToxic(int n) {
        ArrayList<Toxic> toxicList = new ArrayList<Toxic>();
        for (int i = 0; i < n; i++) {
            toxicList.add(generateToxic());
        }
        return toxicList;
    }

    public static ArrayList<Liquid.Toxic> generateLiquidToxic(int n) {
        ArrayList<Liquid.Toxic> liquidToxicList = new ArrayList<Liquid.Toxic>();
        for (int i = 0; i < n; i++) {
            liquidToxicList.add(generateLiquidToxic());
        }
        return liquidToxicList;
    }

    public static ArrayList<Liquid> generateLiquids(int n) {
        ArrayList<Liquid> liquidList = new ArrayList<Liquid>();
        for (int i = 0; i < n; i++) {
            liquidList.add(generateLiquid());
        }
        return liquidList;
    }

    public static ArrayList<Gaseous> generateGaseous(int n) {
        ArrayList<Gaseous> gaseousList = new ArrayList<Gaseous>();
        for (int i = 0; i < n; i++) {
            gaseousList.add(generateGaseous());
        }
        return gaseousList;
    }

    public static ArrayList<Explosive> generateExplosives(int n) {
        ArrayList<Explosive> explosiveList = new ArrayList<Explosive>();
        for (int i = 0; i < n; i++) {
            explosiveList.add(generateExplosive());
        }
        return explosiveList;
    }

    public static ArrayList<Drink> generateDrinks(int n) {
        ArrayList<Drink> drinkList = new ArrayList<Drink>();
        for (int i = 0; i < n; i++) {
            drinkList.add(generateDrink());
        }
        return drinkList;
    }

    public static ArrayList<Meal> generateMeals(int n) {
        ArrayList<Meal> mealList = new ArrayList<Meal>();
        for (int i = 0; i < n; i++) {
            mealList.add(generateMeal());
        }
        return mealList;
    }

    public static ArrayList<Food> generateFood(int n) {
        ArrayList<Food> foodList = new ArrayList<Food>();
        for (int i = 0; i < n; i++) {
            foodList.add(generateFood());
        }
        return foodList;
    }

}
