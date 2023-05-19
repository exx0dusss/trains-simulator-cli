package IO;

import java.util.*;
import Exceptions.*;
import Generators.*;
import Locomotive.*;
import Objects.*;
import RailwayCarriages.*;
import Stations.*;

public abstract class Menu {
    private static StationMap map; // map of stations
    private static ArrayList<Station> stations; // list of stations
    private static ArrayList<Locomotive> trains; // list of trains
    private static ArrayList<Thread> threads; // list of threads
    private static AppState appState = AppState.getInstance(); // getting instance

    static {
        trains = new ArrayList<Locomotive>();
        threads = new ArrayList<Thread>();
    }

    public static void start() {
        // I dont clsoe the scanner because the program will stop working (IDK why)
        Scanner scan = new Scanner(System.in);
        int num;
        mainloop: while (true) {
            while (true) {
                System.out.print("Enter min x/y position: ");
                num = scan.nextInt();
                if (num > 0) {
                    break;
                }
                System.out.println("Invalid input!\n");
            }
            Station.setMin(num);
            System.out.println();

            while (true) {
                System.out.print("Enter max x/y position: ");
                num = scan.nextInt();
                if (num > 0 && num >= Station.getMin()) {
                    break;
                }
                System.out.println("Invalid input!\n");
            }
            System.out.println();
            Station.setMax(num);
            while (true) {
                System.out.print(String.format("Enter station number to generate: ",
                        Station.getMax() * Station.getMax()));
                num = scan.nextInt();
                if (num < 0)
                    continue;
                break;
            }
            System.out.println();
            System.out.println("Generating...");
            stations = Generator.generateStations(num);
            int count = 0;
            for (Station station : stations) {
                System.out.println(String.format("%2s. %s", count, station));
                count++;
            }
            System.out.println("Generating map..."); // may take a lot of time
            map = new StationMap(stations);
            System.out.println();
            if (map.getStations().size() > 0) {
                System.out.print("Print station map? Y/N: ");
                char input = scan.next().charAt(0);
                System.out.println();
                if (Character.toUpperCase(input) == 'Y') {
                    map.printMap();
                }
            }
            while (true) {
                System.out.print("Enter number of trains to generate: ");
                num = scan.nextInt();
                if (num < 0)
                    continue;
                break;
            }
            System.out.println();

            for (int i = 0; i < num; i++) {
                Locomotive loc;
                try {
                    loc = Generator.generateLocomotive(map);
                    trains.add(loc);
                    threads.add(new Thread(loc));
                } catch (RouteGenerationFailedException e) {
                    e.printStackTrace();
                }

            }
            System.out.println();
            for (Locomotive train : trains) {
                System.out.println(train);
                System.out.println();
            }

            while (true) {
                System.out.println("Choose next action (number): ");
                System.out.println("1. Manage trains");
                System.out.println("2. Manage stations");
                System.out.println("3. Reconfigure");
                System.out.println("4. Launch");
                num = scan.nextInt();
                System.out.println();
                switch (num) {
                    case 1:
                        manageTrains();
                        break;
                    case 2:
                        manageStations();
                        break;
                    case 3:
                        trains.clear();
                        stations.clear();
                        Locomotive.setCounter(0);
                        continue mainloop;
                    case 4:
                        break mainloop;
                    default:
                        System.out.println("Invalid input!\n");
                        break;

                }

            }

        }
        launch();
        System.out.println("\n\nEnter train id to get info\nEnter -1 to exit\n\n");
        while (true) {
            int n = scan.nextInt();
            if (n == -1) {
                System.exit(0);
            }
            try {
                Locomotive train = trains.get(n);
                showTrainState(train);
            } catch (Exception e) {
                System.out.println("Train does not exist!\n");
                continue;
            }

        }

    }

    private static void showTrainState(Locomotive train) {
        Scanner scan = new Scanner(System.in);
        int num;
        while (true) {
            double completedDistance = ((((train.getCompletedFullDistance())) * 100.0)
                    / (train.getFullDistance()));
            double completedInterDistance = 0;
            if (train.getInterDistance() != 0) {
                completedInterDistance = ((((train.getCompletedInterDistance())) * 100.0)
                        / (train.getInterDistance()));
            }
            System.out.println("Choose next action (number): ");
            System.out.println("1. Show basic info");
            System.out.println("2. Show completed distance");
            System.out.println("3. Show summary");
            System.out.println("4. Show completed inter distance");
            System.out.println("5. Back");
            num = scan.nextInt();
            System.out.println();
            switch (num) {
                case 1:
                    System.out.println();
                    System.out.println(String.format("Route distance: %.1fkm",
                            train.getFullDistance()));
                    System.out.println(train);
                    System.out.println();
                    break;
                case 2:
                    System.out.println();
                    System.out.println(String.format("Completed distance: %.1f%%",
                            completedDistance));
                    System.out.println();
                    break;
                case 3:
                    int people = 0;
                    for (RailwayCarriage carriage : train.getCarriagesList()) {
                        if (carriage instanceof PassengerRailroadCar) {
                            PassengerRailroadCar passengerCar = (PassengerRailroadCar) carriage;
                            people += passengerCar.getPassangerQty();
                        }
                    }
                    System.out.println();
                    System.out.println(train.getCarriagesInfo());
                    System.out.println(String.format("Passanger quantity: %d", people));
                    System.out.println();
                    break;
                case 4:
                    System.out.println();
                    System.out.println(String.format("Completed inter distance: %.1f%%",
                            completedInterDistance));
                    System.out.println();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid input!\n");
                    break;

            }

        }
    }

    private static void launch() {
        appState.start();
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public static ArrayList<Locomotive> getTrains() {
        return trains;
    }

    private static void manageStations() {
        Scanner scan = new Scanner(System.in);
        int num;
        while (true) {
            System.out.println("Choose next action (number): ");
            System.out.println("1. Show stations");
            System.out.println("2. Print station map");
            System.out.println("3. Add stations");
            System.out.println("4. Remove stations");
            System.out.println("5. Back");
            num = scan.nextInt();
            System.out.println();
            switch (num) {
                case 1:
                    showStations();
                    break;
                case 2:
                    map.printMap();
                    break;
                case 3:
                    showStations();
                    addStation();
                    break;
                case 4:
                    showStations();
                    removeStation();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid input!\n");
                    break;

            }

        }
    }

    private static void addStation() {
        Scanner scan = new Scanner(System.in);
        String name;
        int lat;
        int lon;
        System.out.print("Enter name: ");
        name = scan.nextLine();
        System.out.println();
        while (true) {
            System.out.print("Enter latitude: ");
            lat = scan.nextInt();
            if (lat < 0)
                continue;
            break;
        }
        System.out.println();
        while (true) {
            System.out.print("Enter longitude: ");
            lon = scan.nextInt();
            if (lon < 0)
                continue;
            break;
        }
        System.out.println();
        if (lat > Station.getMax())

        {
            Station.setMax(lat);
        }

        if (lon > Station.getMax()) {
            Station.setMax(lon);
        }

        if (lat < Station.getMin()) {
            Station.setMin(lat);
        }

        if (lon < Station.getMin()) {
            Station.setMin(lon);
        }
        if (map.add(new Station(name, lat, lon))) {
            System.out.println("Successfully added!");
            return;
        }

    }

    private static void removeStation() {
        Scanner scan = new Scanner(System.in);
        int stationIndex;
        while (true) {
            System.out.print("Choose station to remove: ");
            try {
                stationIndex = scan.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid station!\n");
                continue;
            }
            break;
        }
        System.out.println();
        if (map.removeStation(map.getStations().get(stationIndex))) {
            System.out.println("Successfully removed!");
            return;
        }
    }

    private static void manageTrains() {
        Scanner scan = new Scanner(System.in);
        int num;
        while (true) {
            System.out.println("Choose next action (number): ");
            System.out.println("1. Show trains");
            System.out.println("2. Add trains");
            System.out.println("3. Remove trains");
            System.out.println("4. Configure trains");
            System.out.println("5. Back");
            num = scan.nextInt();
            System.out.println();
            switch (num) {
                case 1:
                    showTrains();
                    break;
                case 2:
                    showStations();
                    addTrain();
                    break;
                case 3:
                    showTrains();
                    removeTrain();
                    break;
                case 4:
                    showTrains();
                    Locomotive train = trains.get(chooseTrain());
                    configureTrain(train);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid input!\n");
                    break;

            }

        }

    }

    private static int chooseTrain() {
        Scanner scan = new Scanner(System.in);
        int train;
        while (true) {
            System.out.print("Choose train (number): ");
            try {
                train = scan.nextInt();
                if (train >= 0 && train < trains.size())
                    break;
                System.out.println("Invalid input!\n");
            } catch (Exception e) {
                System.out.println("Invalid train!\n");
            }

        }
        return train;
    }

    private static void configureTrain(Locomotive train) {
        Scanner scan = new Scanner(System.in);
        int num;
        while (true) {
            System.out.println("Choose next action (number): ");
            System.out.println("1. Show carriages");
            System.out.println("2. Add carriage");
            System.out.println("3. Remove carriage");
            System.out.println("4. Configure carriages");
            System.out.println("5. Back");
            num = scan.nextInt();
            System.out.println();
            switch (num) {
                case 1:
                    showCarriages(train);
                    break;
                case 2:
                    addCarriage(train);
                    break;
                case 3:
                    showCarriages(train);
                    removeCarriage(train);
                    break;
                case 4:
                    showCarriages(train);
                    configureCarriages(train);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid input!\n");
                    break;
            }

        }
    }

    private static void showCarriages(Locomotive train) {
        System.out.println(train.getCarriagesInfo());
    }

    private static void showCarriageTypes() {
        int count = 0;
        for (CarriageType carriage : CarriageType.values()) {
            System.out.println(String.format("%2s. %s", count, carriage));
            count++;
        }
    }

    private static void addCarriage(Locomotive train) {
        Scanner scan = new Scanner(System.in);
        int carriage;
        showCarriageTypes();
        System.out.print("Choose carriage (number): ");
        carriage = scan.nextInt();
        String shipper = Generator.generateString("Shippers");
        try {
            switch (carriage) {
                case 0:
                    train.add(new PassengerRailroadCar(shipper));
                    break;
                case 1:
                    train.add(new RailroadPostOffice(shipper));
                    break;
                case 2:
                    train.add(new RailroadBaggageAndMailCar(shipper));
                    break;
                case 3:
                    train.add(new RailroadRestaurant(shipper));
                    break;
                case 4:
                    train.add(new BasicFreightCar(shipper));
                    break;
                case 5:
                    train.add(new HeavyFreightCar(shipper));
                    break;
                case 6:
                    train.add(new BasicFreightCar(shipper).new Refrigerated(shipper));
                    break;
                case 7:
                    train.add(new BasicFreightCar(shipper).new ForLiquid(shipper));
                    break;
                case 8:
                    train.add(new BasicFreightCar(shipper).new ForGaseous(shipper));
                    break;
                case 9:
                    train.add(new HeavyFreightCar(shipper).new ForExplosives(shipper));
                    break;
                case 10:
                    train.add(new HeavyFreightCar(shipper).new ForToxic(shipper));
                    break;
                case 11:
                    train.add(new HeavyFreightCar(shipper).new ForLiquidToxic(shipper));
                    break;
                default:
                    System.out.println("Carriage not found!\n");
                    return;
            }
        } catch (MaxCarriagesExceededException e) {
            System.err.println(e);
        } catch (MaxPoweredCarriagesException e) {
            System.err.println(e);
        } catch (MaxWeightExceededException e) {
            System.err.println(e);
        } catch (CarriageAlreadyConnectedException e) {
            System.err.println(e);
        } catch (MaxEmployeesReachedException e) {
            System.err.println(e);
        }

    }

    private static void removeCarriage(Locomotive train) {
        Scanner scan = new Scanner(System.in);
        int carriage;
        while (true) {
            System.out.print("Choose carriage (number): ");
            carriage = scan.nextInt();
            if (train.getCarriagesQty() > carriage && carriage >= 0) {
                break;
            }
            System.out.println("Invalid carriage!");
            System.out.println();
        }
        try {
            train.remove(train.getCarriagesList().get(carriage));
        } catch (CarriageNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Successfully removed!");
    }

    private static void configureCarriages(Locomotive train) {
        Scanner scan = new Scanner(System.in);
        int num;
        int carriage;
        System.out.print("Choose carriage (number): ");
        while (true) {
            System.out.print("Choose carriage (number): ");
            carriage = scan.nextInt();
            if (train.getCarriagesQty() > carriage && carriage >= 0) {
                break;
            }
            System.out.println("Invalid input!\n");
        }
        configureCarriage(train, carriage);

    }

    public static void configureCarriage(Locomotive train, int carriage) {
        Scanner scan = new Scanner(System.in);
        int num;
        while (true) {
            System.out.println("Choose next action (number): ");
            System.out.println("1. Show cargo");
            System.out.println("2. Add cargo");
            System.out.println("3. Remove cargo");
            System.out.println("4. Back");
            num = scan.nextInt();
            System.out.println();
            switch (num) {
                case 1:
                    showCargo(train, carriage);
                    break;
                case 2:
                    showCargo(train, carriage);
                    addCargo(train, carriage);
                    break;
                case 3:
                    showCargo(train, carriage);
                    removeCargo(train, carriage);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid input!\n");
                    break;
            }

        }
    }

    private static void showCargo(Locomotive train, int carriageIndex) {
        RailwayCarriage carriage = train.getCarriagesList().get(carriageIndex);
        switch (carriage.getType()) {
            case BasicFreightCar:
                ((BasicFreightCar) carriage).displayCargo();
                break;
            case ForExplosives:
                ((HeavyFreightCar.ForExplosives) carriage).displayExplosives();
                break;
            case ForGaseous:
                ((BasicFreightCar.ForGaseous) carriage).displayGaseous();
                break;
            case ForLiquid:
                ((BasicFreightCar.ForLiquid) carriage).displayLiquid();
                break;
            case ForLiquidToxic:
                ((HeavyFreightCar.ForLiquidToxic) carriage).displayLiquidToxic();
                break;
            case ForToxic:
                ((HeavyFreightCar.ForToxic) carriage).displayToxic();
                break;
            case HeavyFreightCar:
                ((HeavyFreightCar) carriage).displayCargo();
                break;
            case PassengerRailroadCar:
                ((PassengerRailroadCar) carriage).displayPassangers();
                break;
            case RailroadBaggageAndMailCar:
                ((RailroadBaggageAndMailCar) carriage).displayStorage();
                break;
            case RailroadPostOffice:
                ((RailroadPostOffice) carriage).displayPost();
                break;
            case RailroadRestaurant:
                ((RailroadRestaurant) carriage).displayFood();
                break;
            case Refrigerated:
                ((BasicFreightCar.Refrigerated) carriage).displayFood();
                break;
            default:
                break;

        }

    }

    private static void addCargo(Locomotive train, int carriageIndex) {
        Scanner scan = new Scanner(System.in);
        int choice;
        RailwayCarriage carriage = train.getCarriagesList().get(carriageIndex);
        try {
            switch (carriage.getType()) {
                case BasicFreightCar:
                    ((BasicFreightCar) carriage).loadCargo(createCargo());
                    break;
                case ForExplosives:
                    ((HeavyFreightCar.ForExplosives) carriage).loadExplosive(createExplosive());
                    break;
                case ForGaseous:
                    ((BasicFreightCar.ForGaseous) carriage).loadGaseous(createGaseous());
                    break;
                case ForLiquid:
                    ((BasicFreightCar.ForLiquid) carriage).loadLiquid(createLiquid());
                    break;
                case ForLiquidToxic:
                    ((HeavyFreightCar.ForLiquidToxic) carriage).loadLiquidToxic(createLiquidToxic());
                    break;
                case ForToxic:
                    ((HeavyFreightCar.ForToxic) carriage).loadToxic(createToxic());
                    break;
                case HeavyFreightCar:
                    ((HeavyFreightCar) carriage).loadCargo(createCargo());
                    break;
                case PassengerRailroadCar:
                    ((PassengerRailroadCar) carriage).addPassanger(createPassanger());
                    break;
                case RailroadBaggageAndMailCar:
                    System.out.print("Baggage or Mail? 1/2 : ");
                    choice = scan.nextInt();
                    switch (choice) {
                        case 1:
                            System.out.println("Enter Baggage data");
                            ((RailroadBaggageAndMailCar) carriage).addBaggage(createBaggage());
                            break;
                        case 2:
                            ((RailroadBaggageAndMailCar) carriage).addMail(createMail());
                            break;
                        default:
                            System.out.println("Invalid input!\n");
                            break;
                    }
                    break;
                case RailroadPostOffice:
                    ArrayList<Mail> mailList = createMailStack();
                    ((RailroadPostOffice) carriage).addMailStack(new MailStack(mailList));
                    break;
                case RailroadRestaurant:
                    System.out.print("Drink or Meal? 1/2 : ");
                    choice = scan.nextInt();
                    switch (choice) {
                        case 1:
                            ((RailroadRestaurant) carriage).addDrink(createDrink());
                            break;
                        case 2:
                            ((RailroadRestaurant) carriage).addMeal(createMeal());
                            break;
                        default:
                            System.out.println("Invalid input!\n");
                            break;
                    }
                    break;
                case Refrigerated:
                    System.out.print("Drink or Meal? 1/2 : ");
                    choice = scan.nextInt();
                    switch (choice) {
                        case 1:
                            ((BasicFreightCar.Refrigerated) carriage).loadDrink(createDrink());
                            break;
                        case 2:
                            ((BasicFreightCar.Refrigerated) carriage).loadMeal(createMeal());
                            break;
                        default:
                            System.out.println("Invalid input!\n");
                            break;
                    }

                    break;
                default:
                    System.out.println("Invalid input!\n");
                    break;

            }
        } catch (MaxWeightExceededException e) {
            System.err.println(e);
        } catch (MaxLoadExceededException e) {
            System.err.println(e);
        } catch (MailStackFullException e) {
            System.err.println(e);
        }

        System.out.println("Successfully added!");
    }

    private static void removeCargo(Locomotive train, int carriageIndex) {
        Scanner scan = new Scanner(System.in);
        RailwayCarriage carriage = train.getCarriagesList().get(carriageIndex);
        int choice;
        int cargoIndex;
        System.out.print("Choose cargo to remove (number): ");
        cargoIndex = scan.nextInt();
        System.out.println();
        switch (carriage.getType()) {
            case BasicFreightCar:
                ((BasicFreightCar) carriage).unloadCargo(
                        ((BasicFreightCar) carriage).getCargoList().get(cargoIndex));
                break;
            case ForExplosives:
                ((HeavyFreightCar.ForExplosives) carriage).unloadExplosive(
                        ((HeavyFreightCar.ForExplosives) carriage).getExplosivesList().get(cargoIndex));
                break;
            case ForGaseous:
                ((BasicFreightCar.ForGaseous) carriage).unloadGaseous(
                        ((BasicFreightCar.ForGaseous) carriage).getGaseousList().get(cargoIndex));
                break;
            case ForLiquid:
                ((BasicFreightCar.ForLiquid) carriage).unloadLiquid(
                        ((BasicFreightCar.ForLiquid) carriage).getLiquidList().get(cargoIndex));
                break;
            case ForLiquidToxic:
                ((HeavyFreightCar.ForLiquidToxic) carriage).unloadLiquidToxic(
                        ((HeavyFreightCar.ForLiquidToxic) carriage).getLiquidToxicList().get(cargoIndex));
            case ForToxic:
                ((HeavyFreightCar.ForToxic) carriage).unloadToxic(
                        ((HeavyFreightCar.ForToxic) carriage).getToxicList().get(cargoIndex));
                break;
            case HeavyFreightCar:
                ((HeavyFreightCar) carriage).unloadCargo(
                        ((HeavyFreightCar) carriage).getCargoList().get(cargoIndex));
                break;
            case PassengerRailroadCar:
                ((PassengerRailroadCar) carriage).removePassanger(
                        ((PassengerRailroadCar) carriage).getPassangerArrayList().get(cargoIndex));
                break;
            case RailroadBaggageAndMailCar:
                System.out.print("Baggage or Mail? 1/2 : ");
                choice = scan.nextInt();
                switch (choice) {
                    case 1:
                        ((RailroadBaggageAndMailCar) carriage).removeBaggage(
                                ((RailroadBaggageAndMailCar) carriage).getBaggageList().get(cargoIndex));
                        break;
                    case 2:
                        ((RailroadBaggageAndMailCar) carriage).removeMail(
                                ((RailroadBaggageAndMailCar) carriage).getMailList().get(cargoIndex));
                        break;
                    default:
                        System.out.println("Invalid input!\n");
                        break;
                }
                break;
            case RailroadPostOffice:
                ((RailroadPostOffice) carriage).removeMailStack(
                        ((RailroadPostOffice) carriage).getPostList().get(cargoIndex));
                break;
            case RailroadRestaurant://
                System.out.print("Drink or Meal? 1/2 : ");
                choice = scan.nextInt();
                switch (choice) {
                    case 1:
                        ((RailroadRestaurant) carriage).removeDrink(
                                ((RailroadRestaurant) carriage).getDrinksList().get(cargoIndex));
                        break;
                    case 2:
                        ((RailroadRestaurant) carriage).removeMeal(
                                (((RailroadRestaurant) carriage).getMealsList().get(cargoIndex)));
                        break;
                    default:
                        System.out.println("Invalid input!\n");
                        break;
                }
                break;
            case Refrigerated://
                System.out.print("Drink or Meal? 1/2 : ");
                choice = scan.nextInt();
                switch (choice) {
                    case 1:
                        ((BasicFreightCar.Refrigerated) carriage).unloadDrink(
                                ((BasicFreightCar.Refrigerated) carriage).getDrinkList().get(cargoIndex));
                        break;
                    case 2:
                        ((BasicFreightCar.Refrigerated) carriage).unloadMeal(
                                ((BasicFreightCar.Refrigerated) carriage).getMealList().get(cargoIndex));
                        break;
                    default:
                        System.out.println("Invalid input!\n");
                        break;
                }

                break;
            default:
                System.out.println("Invalid input!\n");
                break;

        }

        System.out.println("Successfully removed!");
    }

    private static Cargo createCargo() {
        Scanner scan = new Scanner(System.in);
        String name;
        double weight;
        String description;
        System.out.println("Enter Cargo data ");
        System.out.print("Name: ");
        name = scan.nextLine();
        while (true) {
            System.out.print("Weight: ");
            weight = scan.nextDouble();
            if (weight >= 0)
                break;
            System.out.println("Invalid input!\n");
        }

        scan.nextLine();
        System.out.println("Description: ");
        description = scan.nextLine();
        return new Cargo(name, weight, description);
    }

    private static Liquid createLiquid() {
        Scanner scan = new Scanner(System.in);
        String name;
        double weight;
        String description;
        System.out.println("Enter Liquid data");
        System.out.print("Name: ");
        name = scan.nextLine();
        while (true) {
            System.out.print("Volume: ");
            weight = scan.nextDouble();
            if (weight >= 0)
                break;
            System.out.println("Invalid input!\n");
        }
        scan.nextLine();
        System.out.println("Description: ");
        description = scan.nextLine();
        return new Liquid(name, weight, description);
    }

    private static Gaseous createGaseous() {
        Scanner scan = new Scanner(System.in);
        String name;
        double weight;
        String description;
        System.out.println("Enter Gaseous data");
        System.out.print("Name: ");
        name = scan.nextLine();
        while (true) {
            System.out.print("Volume: ");
            weight = scan.nextDouble();
            if (weight >= 0)
                break;
            System.out.println("Invalid input!\n");
        }
        scan.nextLine();
        System.out.println("Description: ");
        description = scan.nextLine();
        return new Gaseous(name, weight, description);
    }

    private static Explosive createExplosive() {
        Scanner scan = new Scanner(System.in);
        String name;
        double weight;
        String description;
        System.out.println("Enter Explosive data");
        System.out.print("Name: ");
        name = scan.nextLine();
        while (true) {
            System.out.print("Weight: ");
            weight = scan.nextDouble();
            if (weight >= 0)
                break;
            System.out.println("Invalid input!\n");
        }
        scan.nextLine();
        System.out.println("Description: ");
        description = scan.nextLine();
        return new Explosive(name, weight, description);
    }

    private static Toxic createToxic() {
        Scanner scan = new Scanner(System.in);
        String name;
        double weight;
        String description;
        System.out.println("Enter Toxic data");
        System.out.print("Name: ");
        name = scan.nextLine();
        while (true) {
            System.out.print("Volume: ");
            weight = scan.nextDouble();
            if (weight >= 0)
                break;
            System.out.println("Invalid input!\n");
        }
        scan.nextLine();
        System.out.println("Description: ");
        description = scan.nextLine();
        return new Toxic(name, weight, description);
    }

    private static Liquid.Toxic createLiquidToxic() {
        Scanner scan = new Scanner(System.in);
        String name;
        double weight;
        String description;
        System.out.println("Enter Liquid.Toxic data");
        System.out.print("Name: ");
        name = scan.nextLine();
        while (true) {
            System.out.print("Volume: ");
            weight = scan.nextDouble();
            if (weight >= 0)
                break;
            System.out.println("Invalid input!\n");
        }
        scan.nextLine();
        System.out.println("Description: ");
        description = scan.nextLine();
        return createLiquid().new Toxic(name, weight, description);
    }

    private static Drink createDrink() {
        Scanner scan = new Scanner(System.in);
        String name;
        double price;
        double weight;
        System.out.println("Enter drink data");
        System.out.print("Name: ");
        name = scan.nextLine();
        while (true) {
            System.out.print("Price: ");
            price = scan.nextDouble();
            if (price >= 0)
                break;
            System.out.println("Invalid input!\n");
        }
        while (true) {
            System.out.print("Weight: ");
            weight = scan.nextDouble();
            if (weight >= 0)
                break;
            System.out.println("Invalid input!\n");
        }
        return new Drink(name, price, weight);
    }

    private static Meal createMeal() {
        Scanner scan = new Scanner(System.in);
        String name;
        double price;
        double weight;
        System.out.println("Enter meal data");
        System.out.print("Name: ");
        name = scan.nextLine();
        while (true) {
            System.out.print("Price: ");
            price = scan.nextDouble();
            if (price >= 0)
                break;
            System.out.println("Invalid input!\n");
        }
        while (true) {
            System.out.print("Weight: ");
            weight = scan.nextDouble();
            if (weight >= 0)
                break;
            System.out.println("Invalid input!\n");
        }
        return new Meal(name, price, weight);
    }

    private static Baggage createBaggage() {
        Scanner scan = new Scanner(System.in);
        String baggage;
        System.out.println("Enter Baggage data");
        System.out.println("Baggage (item, item, item): ");
        baggage = scan.nextLine();
        return new Baggage(baggage);
    }

    private static Passanger createPassanger() {
        Scanner scan = new Scanner(System.in);
        String name;
        int age;
        double weight;
        String baggage;
        System.out.println("Enter passanger`s data");
        System.out.print("Name: ");
        name = scan.nextLine();
        while (true) {
            System.out.print("Age: ");
            age = scan.nextInt();
            if (age >= 0)
                break;
            System.out.println("Invalid input!\n");
        }
        while (true) {
            System.out.print("Weight: ");
            weight = scan.nextDouble();
            if (weight >= 0)
                break;
            System.out.println("Invalid input!\n");
        }
        scan.nextLine();
        System.out.println("Baggage (item, item, item): ");
        baggage = scan.nextLine();
        return new Passanger(name, age, weight, new Baggage(baggage));
    }

    private static ArrayList<Mail> createMailStack() {
        Scanner scan = new Scanner(System.in);
        int mails;
        ArrayList<Mail> mailList = new ArrayList<>();
        System.out.print("Mail qty: ");
        mails = scan.nextInt();
        for (int i = 0; i < mails; i++) {
            mailList.add(createMail());
        }
        return mailList;
    }

    private static Mail createMail() {
        Scanner scan = new Scanner(System.in);
        String address;
        String message;
        System.out.println("Enter Mail data");
        System.out.print("Address: ");
        address = scan.nextLine();
        System.out.println("Message: ");
        message = scan.nextLine();
        return new Mail(address, message);
    }

    private static void showTrains() {
        if (trains.size() == 0) {
            System.out.println("No trains!\n");
            return;
        }
        int counter = 0;
        for (Locomotive train : trains) {
            System.out.println(counter++ + ". " + train.getBriefTrainInfo());

        }
        System.out.println();
    }

    private static void showStations() {
        if (stations.size() == 0) {
            System.out.println("No stations!\n");
            return;
        }
        int counter = 0;
        for (Station station : stations) {
            System.out.println(counter++ + ". " + station);
        }
    }

    private static void addTrain() {
        Scanner scan = new Scanner(System.in);
        int num;
        while (true) {
            System.out.print("Choose start station (number): ");
            num = scan.nextInt();
            if (stations.size() > num && num >= 0)
                break;
            System.out.println("Station not found!\n");
        }
        System.out.println();
        Station start = stations.get(num);
        while (true) {
            System.out.print("Choose end station (number): ");
            num = scan.nextInt();
            if (stations.size() > num && num >= 0)
                break;
            System.out.println("Station not found!\n");

        }
        System.out.println();
        Station end = stations.get(num);
        System.out.print("Enter name ");
        scan.nextLine();
        String str = scan.nextLine();
        System.out.println();
        Route route;
        try {
            route = new Route(start, end);
            trains.add(new Locomotive(str, route));
        } catch (RouteGenerationFailedException e) {
            e.printStackTrace();
        }

        System.out.println(trains.get(trains.size() - 1).getName() + " was Successfully created");
    }

    private static boolean removeTrain() {
        Scanner scan = new Scanner(System.in);
        int num;
        // while (true) {
        // System.out.print("Enter train index: ");
        // num = scan.nextInt();
        // if (trains.size() > num && num >= 0) {
        // break;
        // }
        // System.out.println("Invalid carriage!");
        // System.out.println();
        // }
        System.out.print("Enter train index: ");
        num = scan.nextInt();
        System.out.println();
        if (trains.remove(trains.get(num))) {
            System.out.println("Successfully removed!");
            return true;
        }
        System.out.println("Train not found!\n");
        return false;

    }

    public static AppState getAppState() {
        return appState;
    }

    public static StationMap getMap() {
        return map;
    }

    public static ArrayList<Station> getStations() {
        return stations;
    }

}
