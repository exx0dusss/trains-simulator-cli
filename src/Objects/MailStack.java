package Objects;

import Exceptions.MailStackFullException;

import java.util.ArrayList;

public class MailStack {
    private ArrayList<Mail> mailStack = new ArrayList<Mail>();
    private static final int maxMails; // mail quantity limit
    private int mailQty = 0; // mail quantity
    static {
        maxMails = 15;
    }

    public MailStack() {
    }

    public boolean addMail(Mail mail) throws MailStackFullException {
        if (this.mailQty + 1 > maxMails) {
            throw new MailStackFullException("MailStack is full!");
        }
        this.mailStack.add(mail);
        this.mailQty++;
        return true;
    }

    public boolean removeMail(Mail mail) {
        if (this.mailStack.remove(mail)) {
            System.out.println("Successfully removed!");
            this.mailQty--;
            return true;
        }
        return false;
    }

    public MailStack(ArrayList<Mail> mailList) throws MailStackFullException {
        for (Mail mail : mailList) {
            this.addMail(mail);
        }
    }

    public void displayMails() {
        for (Mail mail : mailStack) {
            String[] lines = mail.toString().split("\n");
            for (String line : lines) {
                System.out.println("\t" + line);
            }
            System.out.println();
        }

    }

    public int getMailQty() {
        return mailQty;
    }

    public ArrayList<Mail> getMailStack() {
        return mailStack;
    }

}