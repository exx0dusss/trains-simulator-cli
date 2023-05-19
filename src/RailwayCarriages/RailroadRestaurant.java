package RailwayCarriages;

import java.util.ArrayList;

import Exceptions.*;
import Objects.*;

public class RailroadRestaurant extends RailwayCarriage {
    private static final int maxDrinks, maxMeals, maxEmployees;
    private static final String employeeProfession;
    private ArrayList<Employee> employeesList = new ArrayList<Employee>();
    private ArrayList<Drink> drinkList = new ArrayList<Drink>();
    private ArrayList<Meal> mealList = new ArrayList<Meal>();
    private int drinksQty = 0, mealsQty = 0, employeesQty = 0;

    static {
        maxDrinks = 80;
        maxMeals = 80;
        maxEmployees = 5;
        employeeProfession = "bartender";
    }

    public RailroadRestaurant(String shipper) throws MaxEmployeesReachedException {
        super(shipper);
        this.type = CarriageType.RailroadRestaurant;
        this.powered = true;
        this.measure = "units";
        this.grossLoad = maxDrinks + maxMeals;
        this.grossWeight = (double) 20_000; // kg
        this.addEmployee(new Employee("Bob", 21, 72, employeeProfession));

    }

    public boolean addDrink(Drink drink) throws MaxLoadExceededException, MaxWeightExceededException {
        if (this.drinksQty + 1 > maxDrinks || this.netLoad + 1 > this.grossLoad) {
            throw new MaxLoadExceededException("Max drinks");
        }
        if (this.netWeight + drink.getWeight() > this.grossWeight) {
            throw new MaxWeightExceededException();

        }
        drinkList.add(drink);
        this.drinksQty++;
        this.netLoad++;
        this.netWeight += drink.getWeight();
        return true;
    }

    public void addDrinks(ArrayList<Drink> drinks) throws MaxLoadExceededException, MaxWeightExceededException {
        for (Drink drink : drinks) {
            this.addDrink(drink);
        }
    }

    public boolean removeDrink(Drink drink) {
        if (drinkList.remove(drink)) {
            this.drinksQty--;
            this.netLoad--;
            this.netWeight -= drink.getWeight();
            return true;
        }
        System.out.println("Drink not found in the list!");
        return false;

    }

    public void removeDrinks(ArrayList<Drink> drinks) {
        for (Drink drink : drinks) {
            this.removeDrink(drink);
        }

    }

    public boolean addMeal(Meal meal) throws MaxLoadExceededException, MaxWeightExceededException {
        if (this.mealsQty + 1 > maxMeals || this.netLoad + 1 > this.grossLoad) {
            throw new MaxLoadExceededException("Max meals");

        }

        if (this.netWeight + meal.getWeight() > this.grossWeight) {
            throw new MaxWeightExceededException();

        }
        mealList.add(meal);
        this.mealsQty++;
        this.netLoad++;
        this.netWeight += meal.getWeight();
        return true;

    }

    public void addMeals(ArrayList<Meal> meals) throws MaxLoadExceededException, MaxWeightExceededException {
        for (Meal meal : meals) {
            this.addMeal(meal);
        }
    }

    public boolean removeMeal(Meal meal) {
        if (mealList.remove(meal)) {
            this.mealsQty--;
            this.netLoad--;
            this.netWeight -= meal.getWeight();
            return true;
        }
        System.out.println("Meal not found in the list!");
        return false;

    }

    public void removeMeals(ArrayList<Meal> meals) {
        for (Meal meal : meals) {
            this.removeMeal(meal);
        }

    }

    public boolean addEmployee(Employee employee) throws MaxEmployeesReachedException {
        if (employeesQty + 1 > maxEmployees) {
            throw new MaxEmployeesReachedException("Max employees!");
        }
        this.employeesList.add(employee);
        employeesQty++;
        return true;

    }

    public boolean removeEmployee(Employee employee) {
        if (employeesList.remove(employee)) {
            this.employeesQty--;
            return true;
        }
        System.out.println("Employee not found in the list!");
        return false;

    }

    public Drink mixDrinks(Drink drink1, Drink drink2) {
        String newName = drink1.getName() + "-" + drink2.getName() + " mix";
        double newPrice = drink1.getPrice() + drink2.getPrice();
        double newWeight = (drink1.getWeight() + drink2.getWeight()) / 1.5;
        Drink newDrink = new Drink(
                newName,
                newPrice, newWeight);
        return newDrink;
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

    public ArrayList<Drink> getDrinksList() {
        return drinkList;
    }

    public ArrayList<Meal> getMealsList() {
        return mealList;
    }

    @Override
    public String toString() {
        return String.format("Type: %s,\nIndex: %s,\nDrinks quantity: %d,\nMeals quantity: %d",
                this.type, this.getId(), this.drinksQty, this.mealsQty);
    }

}
