package RailwayCarriages;

import java.util.ArrayList;

import Exceptions.MaxWeightExceededException;
import Objects.*;

public class BasicFreightCar extends RailwayCarriage {
    private ArrayList<Cargo> cargoList = new ArrayList<Cargo>(); // list of cargo
    private static final int maxCargo; // cargo limit
    private static final String unit; // just for visuals

    static {
        maxCargo = 50_000; // unlimited*
        unit = "Any";
    }

    public BasicFreightCar(String shipper) {
        super(shipper);
        this.type = CarriageType.BasicFreightCar;
        this.measure = "tons";
        this.grossLoad = maxCargo;
        this.grossWeight = maxCargo; // kg

    }

    public boolean loadCargo(Cargo cargo) throws MaxWeightExceededException {
        if (this.netWeight + cargo.getWeight() > grossWeight || this.netLoad + 1 > this.grossLoad) {
            throw new MaxWeightExceededException("The cargo weight limit reached!");
        }
        this.cargoList.add(cargo);
        this.netWeight += cargo.getWeight();
        this.netLoad++;
        return true;
    }

    public void loadCargos(ArrayList<Cargo> cargos) throws MaxWeightExceededException {
        for (Cargo cargo : cargos) {
            this.loadCargo(cargo);
        }
    }

    public boolean unloadCargo(Cargo cargo) {
        if (this.cargoList.remove(cargo)) {
            this.netWeight -= cargo.getWeight();
            this.netLoad--;
            return true;
        }
        return false;
    }

    public void unloadCargos(ArrayList<Cargo> cargos) {
        for (Cargo cargo : cargos) {
            this.unloadCargo(cargo);
        }
    }

    public void displayCargo() {
        int counter = 0;
        System.out.println("{\nCargo:\n    [\n");
        for (Cargo cargo : this.cargoList) {
            System.out.println(String.format("\t%d.", counter++));
            printTabLines(cargo);
            System.out.println();
        }
        System.out.println("    ]\n}");

    }

    public ArrayList<Cargo> getCargoList() {
        return cargoList;
    }

    @Override
    public String toString() {
        return String.format("Type: %s,\nIndex: %d,\nCargo quantity: %d,\nCargo total weight: %.2f",
                this.getType(), this.getId(), this.netLoad, this.netWeight);
    }

    public class Refrigerated extends RailwayCarriage {
        private ArrayList<Drink> drinkList = new ArrayList<Drink>(); // drink list
        private ArrayList<Meal> mealList = new ArrayList<Meal>(); // meal list

        public Refrigerated(String shipper) {
            super(shipper);
            this.type = CarriageType.Refrigerated;
            this.measure = "gramms";
            this.powered = true;
            this.grossLoad = maxCargo;
            this.grossWeight = maxCargo; // kg
        }

        public void loadDrink(Drink drink) throws MaxWeightExceededException {
            if ((this.netWeight + drink.getWeight()) > grossWeight) {
                throw new MaxWeightExceededException("The drink weight limit reached!");
            }
            this.drinkList.add(drink);
            this.netWeight += drink.getWeight();
            this.netLoad++;
        }

        public void loadMeal(Meal meal) throws MaxWeightExceededException {
            if ((this.netWeight + meal.getWeight()) > grossWeight) {
                throw new MaxWeightExceededException("The meal weight limit reached!");
            }
            this.mealList.add(meal);
            this.netWeight += meal.getWeight();
            this.netLoad++;
        }

        public void loadDrinks(ArrayList<Drink> drinks) throws MaxWeightExceededException { // change to arrayList
            for (Drink drink : drinks) {
                this.loadDrink(drink);
            }
        }

        public void loadMeals(ArrayList<Meal> meals) throws MaxWeightExceededException {
            for (Meal meal : meals) {
                this.loadMeal(meal);
            }
        }

        public void unloadDrink(Drink drink) {
            if (this.drinkList.remove(drink)) {
                this.netWeight -= drink.getWeight();
                this.netLoad--;
            }
        }

        public void unloadMeal(Meal meal) {
            if (this.mealList.remove(meal)) {
                this.netWeight -= meal.getWeight();
                this.netLoad--;
            }
        }

        public void unloadDrinks(ArrayList<Drink> drinks) {
            for (Drink drink : drinks) {
                this.unloadDrink(drink);
            }
        }

        public void unloadMeals(ArrayList<Meal> meals) {
            for (Meal meal : meals) {
                this.unloadMeal(meal);
            }
        }

        public void displayDrinks() {
            int counter = 0;
            System.out.println("{\nDrinks:\n    [\n");
            System.out.println();
            for (Drink drink : drinkList) {
                System.out.println(String.format("\t%d.", counter++));
                printTabLines(drink);
                System.out.println();
            }
            System.out.println("    ]\n}");
        }

        public void displayMeals() {
            int counter = 0;
            System.out.println("{\nMeals:\n    [\n");
            for (Meal meal : mealList) {
                System.out.println(String.format("\t%d.", counter++));
                printTabLines(meal);
                System.out.println();
            }
            System.out.println("    ]\n}");
        }

        public void displayFood() {
            this.displayDrinks();
            this.displayMeals();
        }

        public ArrayList<Drink> getDrinkList() {
            return drinkList;
        }

        public ArrayList<Meal> getMealList() {
            return mealList;
        }

        @Override
        public String toString() {
            return String.format("Type: %s,\nIndex: %d,\nVolume: %d\n",
                    this.type, this.getId(), this.netLoad);
        }

    }

    public class ForLiquid extends RailwayCarriage {
        private ArrayList<Liquid> liquidList = new ArrayList<Liquid>(); // liquid list

        public ForLiquid(String shipper) {
            super(shipper);
            this.type = CarriageType.ForLiquid;
            this.measure = "liters";
            this.grossLoad = maxCargo;
            this.grossWeight = maxCargo; // kg

        }

        public void loadLiquid(Liquid liquid) throws MaxWeightExceededException {
            if (this.netWeight + liquid.getWeight() > grossWeight || this.netLoad + 1 > this.grossLoad) {
                throw new MaxWeightExceededException("The volume limit reached!");
            }
            this.liquidList.add(liquid);
            this.netWeight += liquid.getWeight();
            this.netLoad++;
        }

        public void loadLiquids(ArrayList<Liquid> liquids) throws MaxWeightExceededException {
            for (Liquid liquid : liquids) {
                this.loadLiquid(liquid);
            }
        }

        public void unloadLiquid(Liquid liquid) {
            if (this.liquidList.remove(liquid)) {
                this.netWeight -= liquid.getWeight();
                this.netLoad--;
            }
        }

        public void unloadLiquids(ArrayList<Liquid> liquids) {
            for (Liquid liquid : liquids) {
                this.unloadLiquid(liquid);
            }
        }

        public void displayLiquid() {
            int counter = 0;
            System.out.println("{\nCargo:\n    [\n");
            for (Liquid liquid : this.liquidList) {
                System.out.println(String.format("\t%d.", counter++));
                printTabLines(liquid);
                System.out.println();
            }
            System.out.println("    ]\n}");

        }

        public ArrayList<Cargo> getCargoList() {
            return cargoList;
        }

        public ArrayList<Liquid> getLiquidList() {
            return liquidList;
        }

        @Override
        public String toString() {
            return String.format("Type: %s,\nIndex: %d,\nVolume: %d\n",
                    this.type, this.getId(), this.netLoad);
        }

    }

    public class ForGaseous extends RailwayCarriage {
        private ArrayList<Gaseous> gaseousList = new ArrayList<Gaseous>(); // gaseous list

        public ForGaseous(String shipper) {
            super(shipper);
            this.type = CarriageType.ForGaseous;
            this.measure = "cubic meters";
            this.grossLoad = maxCargo;
            this.grossWeight = maxCargo; // kg

        }

        public boolean loadGaseous(Gaseous gas) throws MaxWeightExceededException {
            if (this.netWeight + gas.getWeight() > grossWeight || this.netLoad + 1 > this.grossLoad) {
                throw new MaxWeightExceededException("The volume limit reached!");
            }
            this.gaseousList.add(gas);
            this.netWeight += gas.getWeight();
            this.netLoad++;
            return true;
        }

        public void loadGas(ArrayList<Gaseous> gas) throws MaxWeightExceededException {
            for (Gaseous gaseous : gas) {
                this.loadGaseous(gaseous);
            }
        }

        public boolean unloadGaseous(Gaseous gas) {
            if (this.gaseousList.remove(gas)) {
                this.netWeight -= gas.getWeight();
                this.netLoad--;
                return true;
            }
            return false;
        }

        public void unloadGas(ArrayList<Gaseous> gas) {
            for (Gaseous gaseous : gas) {
                this.unloadGaseous(gaseous);
            }
        }

        public void displayGaseous() {
            int counter = 0;
            System.out.println("{\nGas:\n    [\n");
            for (Gaseous gas : this.gaseousList) {
                System.out.println(String.format("\t%d.", counter++));
                printTabLines(gas);
                System.out.println();
            }
            System.out.println("    ]\n}");

        }

        public ArrayList<Gaseous> getGaseousList() {
            return gaseousList;
        }

        @Override
        public String toString() {
            return String.format("Type: %s,\nIndex: %d,\nVolume: %d\n",
                    this.type, this.getId(), this.netLoad);
        }

    }

}
