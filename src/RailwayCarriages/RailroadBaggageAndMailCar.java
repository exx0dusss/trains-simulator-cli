package RailwayCarriages;

import java.util.ArrayList;

import Exceptions.MaxLoadExceededException;
import Objects.*;

public class RailroadBaggageAndMailCar extends RailwayCarriage {
    private static final int maxBaggage, maxMails;
    private ArrayList<Baggage> baggageList = new ArrayList<>();
    private ArrayList<Mail> mailList = new ArrayList<>();
    private int baggageQty = 0, mailQty = 0;

    static {
        maxBaggage = 80;
        maxMails = 80;
    }

    public RailroadBaggageAndMailCar(String shipper) {
        super(shipper);
        this.type = CarriageType.RailroadBaggageAndMailCar;
        this.measure = "units";
        this.grossLoad = maxBaggage + maxMails;
        this.grossWeight = (double) 20_000; // kg
    }

    public boolean addBaggage(Baggage baggage) throws MaxLoadExceededException {
        if (baggageQty + 1 > maxBaggage || this.netLoad + 1 > this.grossLoad) {
            throw new MaxLoadExceededException("Max baggage");
        }

        this.baggageList.add(baggage);
        this.baggageQty++;
        this.netLoad++;
        return true;
    }

    public void addBaggage(ArrayList<Baggage> baggageList) throws MaxLoadExceededException {
        for (Baggage baggage : baggageList) {
            this.addBaggage(baggage);
        }
    }

    public boolean removeBaggage(Baggage baggage) {
        if (baggageList.remove(baggage)) {
            this.baggageQty--;
            this.netLoad--;
            return true;
        }
        System.out.println("Baggage not found in the list!");
        return false;
    }

    public void removeBaggage(ArrayList<Baggage> baggageList) {
        for (Baggage baggage : baggageList) {
            this.removeBaggage(baggage);
        }

    }

    public boolean addMail(Mail mail) throws MaxLoadExceededException {
        if (mailQty + 1 > maxMails || this.netLoad + 1 > this.grossLoad) {
            throw new MaxLoadExceededException("Max mail");
        }
        this.mailList.add(mail);
        this.mailQty++;
        this.netLoad++;
        return true;
    }

    public void addMail(ArrayList<Mail> mailList) throws MaxLoadExceededException {
        for (Mail mail : mailList) {
            this.addMail(mail);
        }
    }

    public boolean removeMail(Mail mail) {
        if (mailList.remove(mail)) {
            this.mailQty--;
            this.netLoad--;
            return true;
        }
        System.out.println("Mail not found in the list!");
        return false;
    }

    public void removeMail(ArrayList<Mail> mailList) {
        for (Mail mail : mailList) {
            this.removeMail(mail);
        }

    }

    public void displayBaggage() {
        int counter = 0;
        System.out.println("\nBaggage:\n    [\n");
        for (Baggage baggage : this.baggageList) {
            System.out.println(String.format("\t%d.", counter++));
            System.out.println("\t" + baggage);
            System.out.println();
        }
        System.out.println("    ]\n");
    }

    public void displayMail() {
        int counter = 0;
        System.out.println("\nMail:\n    [\n");
        for (Mail mail : this.mailList) {
            System.out.println(String.format("\t%d.", counter++));
            printTabLines(mail);
            System.out.println();

        }
        System.out.println("    ]\n");
    }

    public void displayStorage() {
        this.displayBaggage();
        this.displayMail();
    }

    public ArrayList<Baggage> getBaggageList() {
        return baggageList;
    }

    public ArrayList<Mail> getMailList() {
        return mailList;
    }

    @Override
    public String toString() {
        return String.format("Type: %s,\nIndex: %d,\nBaggage quantity: %d,\nMail quantity: %d",
                this.type, this.getId(), this.baggageQty, this.mailQty);
    }

}
