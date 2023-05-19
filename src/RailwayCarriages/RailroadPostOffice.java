package RailwayCarriages;

import Exceptions.MailStackFullException;
import Exceptions.MaxEmployeesReachedException;
import Objects.*;
import java.util.*;

public class RailroadPostOffice extends RailwayCarriage {
    private static final int maxEmployees, maxMailStacks;
    private static final String employeeProfession;
    private ArrayList<MailStack> postList = new ArrayList<MailStack>();
    private ArrayList<Employee> employeesList = new ArrayList<Employee>();
    private int employeeQty = 0, mailStacksQty = 0;

    static {
        maxEmployees = 5;
        maxMailStacks = 160;
        employeeProfession = "PostMan";
    }

    public RailroadPostOffice(String shipper) throws MaxEmployeesReachedException {
        super(shipper);
        this.type = CarriageType.RailroadPostOffice;
        this.powered = true;
        this.measure = "units";
        this.grossLoad = maxMailStacks;
        this.grossWeight = (double) 20_000; // kg
        this.addEmployee(new Employee("Bob", 21, 72, employeeProfession));

    }

    public boolean addMailStack(MailStack mailStack) throws MailStackFullException {
        if (mailStacksQty + 1 > maxMailStacks || this.netLoad + 1 > this.grossLoad) {
            throw new MailStackFullException();
        }
        this.postList.add(mailStack);
        this.mailStacksQty++;
        this.netLoad++;
        return true;

    }

    public void addMailStacks(ArrayList<MailStack> mailStacks) throws MailStackFullException {
        for (MailStack mailStack : mailStacks) {
            this.addMailStack(mailStack);
        }
    }

    public boolean removeMailStack(MailStack mailStack) {
        if (postList.remove(mailStack)) {
            mailStacksQty--;
            System.out.println("Successfully removed!");
            return true;
        }
        System.out.println("Mailstack not found!");
        return false;

    }

    public void removeMailStacks(ArrayList<MailStack> mailStacks) {
        for (MailStack mailStack : mailStacks) {
            this.removeMailStack(mailStack);
        }
    }

    public void addEmployee(Employee employee) throws MaxEmployeesReachedException {
        if (employeeQty >= maxEmployees) {
            throw new MaxEmployeesReachedException("Max employees!");
        }
        this.employeesList.add(employee);
        employeeQty++;

    }

    private int getMailQuantity() {
        int count = 0;
        for (MailStack mailStack : postList) {
            count += mailStack.getMailQty();
        }
        return count;
    }

    public void displayPost() {
        int count = 0;
        for (MailStack mailStack : postList) {
            System.out.println("{\nStack number " + (count++) + ":\n    [\n");
            mailStack.displayMails();
            System.out.println("    ]\n}");
        }
    }

    public ArrayList<MailStack> getPostList() {
        return postList;
    }

    public ArrayList<Employee> getEmployeesList() {
        return employeesList;
    }

    public int getMailStacksQty() {
        return mailStacksQty;
    }

    @Override
    public String toString() {
        return String.format("Type: %s,\nIndex: %d,\nMailStack quantity: %d,\nMail quantity: %d",
                this.type, this.getId(), mailStacksQty, this.getMailQuantity());
    }

}
