package RailwayCarriages;

import java.util.ArrayList;

import Exceptions.MaxWeightExceededException;
import Objects.*;

public class HeavyFreightCar extends RailwayCarriage {
    private ArrayList<Cargo> cargoList = new ArrayList<Cargo>(); // list of cargo
    private static final int maxCargo; // cargo limit
    private static final String unit; // just for visuals
    static {
        maxCargo = 100_000; // unlimited*
        unit = "Any";
    }

    public HeavyFreightCar(String shipper) {
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

    public class ForExplosives extends RailwayCarriage {
        private ArrayList<Explosive> explosivesList = new ArrayList<Explosive>(); // explosives list

        public ForExplosives(String shipper) {
            super(shipper);
            this.type = CarriageType.ForExplosives;
            this.measure = "kilograms";
            this.grossLoad = maxCargo;
            this.grossWeight = maxCargo; // kg
        }

        public void loadExplosive(Explosive explosive) throws MaxWeightExceededException {
            if (this.netWeight + explosive.getWeight() > grossWeight || this.netLoad + 1 > this.grossLoad) {
                throw new MaxWeightExceededException("The volume limit reached!");
            }
            this.explosivesList.add(explosive);
            this.netWeight += explosive.getWeight();
            this.netLoad++;
        }

        public void loadExplosives(ArrayList<Explosive> explosives) throws MaxWeightExceededException {
            for (Explosive explosive : explosives) {
                this.loadExplosive(explosive);
            }
        }

        public void unloadExplosive(Explosive explosive) {
            if (this.explosivesList.remove(explosive)) {
                this.netWeight -= explosive.getWeight();
                this.netLoad--;
            }
        }

        public void unloadExplosives(ArrayList<Explosive> explosives) {
            for (Explosive explosive : explosives) {
                this.unloadExplosive(explosive);
            }
        }

        public void displayExplosives() {
            int counter = 0;
            System.out.println("{\nExplosives:\n    [\n");
            for (Explosive explosive : this.explosivesList) {
                System.out.println(String.format("\t%d.", counter++));
                printTabLines(explosive);
                System.out.println();
            }
            System.out.println("    ]\n}");
        }

        public ArrayList<Explosive> getExplosivesList() {
            return explosivesList;
        }

        @Override
        public String toString() {
            return String.format("Type: %s,\nIndex: %d,\nVolume: %d\n",
                    this.type, this.getId(), this.netLoad);
        }
    }

    public class ForToxic extends RailwayCarriage {
        private ArrayList<Toxic> toxicList = new ArrayList<Toxic>(); // toxic list

        public ForToxic(String shipper) {
            super(shipper);
            this.type = CarriageType.ForToxic;
            this.measure = "cubic meters";
            this.grossLoad = maxCargo;
            this.grossWeight = maxCargo; // kg
        }

        public boolean loadToxic(Toxic toxic) throws MaxWeightExceededException {
            if (this.netWeight + toxic.getWeight() > grossWeight || this.netLoad + 1 > this.grossLoad) {
                throw new MaxWeightExceededException("The volume limit reached!");
            }
            this.toxicList.add(toxic);
            this.netWeight += toxic.getWeight();
            this.netLoad++;
            return true;
        }

        public void loadToxic(ArrayList<Toxic> toxics) throws MaxWeightExceededException {
            for (Toxic toxic : toxics) {
                this.loadToxic(toxic);
            }
        }

        public boolean unloadToxic(Toxic toxic) {
            if (this.toxicList.remove(toxic)) {
                this.netWeight -= toxic.getWeight();
                this.netLoad--;
                return true;
            }
            return false;
        }

        public void unloadToxic(ArrayList<Toxic> toxics) {
            for (Toxic toxic : toxics) {
                this.unloadToxic(toxic);
            }
        }

        public void displayToxic() {
            int counter = 0;
            System.out.println("{\nToxic:\n    [\n");
            for (Toxic toxic : this.toxicList) {
                System.out.println(String.format("\t%d.", counter++));
                printTabLines(toxic);
                System.out.println();
            }
            System.out.println("    ]\n}");
        }

        public ArrayList<Toxic> getToxicList() {
            return toxicList;
        }

        @Override
        public String toString() {
            return String.format("Type: %s,\nIndex: %d,\nVolume: %d\n",
                    this.type, this.getId(), this.netLoad);
        }
    }

    public class ForLiquidToxic extends RailwayCarriage {
        private ArrayList<Liquid.Toxic> liquidToxicList = new ArrayList<>(); // liquid toxic list

        public ForLiquidToxic(String shipper) {
            super(shipper);
            this.type = CarriageType.ForLiquidToxic;
            this.measure = "liters";
            this.grossLoad = maxCargo;
            this.grossWeight = maxCargo; // kg
        }

        public boolean loadLiquidToxic(Liquid.Toxic liquidToxic) throws MaxWeightExceededException {
            if (this.netWeight + liquidToxic.getWeight() > grossWeight || this.netLoad + 1 > this.grossLoad) {
                throw new MaxWeightExceededException("The volume limit reached!");
            }
            this.liquidToxicList.add(liquidToxic);
            this.netWeight += liquidToxic.getWeight();
            this.netLoad++;
            return true;
        }

        public void loadLiquidToxic(ArrayList<Liquid.Toxic> liquidToxic) throws MaxWeightExceededException {
            for (Liquid.Toxic el : liquidToxic) {
                this.loadLiquidToxic(el);
            }
        }

        public boolean unloadLiquidToxic(Liquid.Toxic liquidToxic) {
            if (this.liquidToxicList.remove(liquidToxic)) {
                this.netWeight -= liquidToxic.getWeight();
                this.netLoad--;
                return true;
            }
            return false;
        }

        public void unloadLiquidToxic(ArrayList<Liquid.Toxic> liquidToxic) {
            for (Liquid.Toxic el : liquidToxic) {
                this.unloadLiquidToxic(el);
            }
        }

        public void displayLiquidToxic() {
            int counter = 0;
            System.out.println("{\nLiquid Toxic:\n    [\n");
            for (Liquid.Toxic liquidToxic : this.liquidToxicList) {
                System.out.println(String.format("\t%d.", counter++));
                printTabLines(liquidToxic);
                System.out.println();
            }
            System.out.println("    ]\n}");
        }

        public ArrayList<Liquid.Toxic> getLiquidToxicList() {
            return liquidToxicList;
        }

        @Override
        public String toString() {
            return String.format("Type: %s,\nIndex: %d,\nVolume: %d\n",
                    this.type, this.getId(), this.netLoad);
        }
    }

}
