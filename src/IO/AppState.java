package IO;

import java.util.ArrayList;
import java.io.*;
import Locomotive.Locomotive;

public class AppState extends Thread {
    private static AppState instance; // instance of AppState
    static final String path = "src/IO/"; // path to Appstate folder

    private AppState() {
    }

    @Override
    public void run() {
        ArrayList<Locomotive> trains = Menu.getTrains(); // get list of trains to iterate
        while (true) {
            try {
                File file = new File(path + "AppState.txt"); // getting file
                if (!file.exists()) {
                    file.createNewFile(); // creating file if !exists
                }
                PrintWriter pw = new PrintWriter(file); // writer initialization
                for (Locomotive locomotive : trains) { // iterating locomotives
                    // if (locomotive.getCarriages() != null)
                    pw.println(String.format("{\nTrain: %d\n\t%s\n}",
                            locomotive.getId(), locomotive.getCarriagesInfo()));

                }
                pw.close();
            } catch (IOException e) {
                System.out.println("Error in reading");
            }
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }
    }

    public static synchronized AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    public static boolean exists() {
        return instance != null;
    }

    public static String getPath() {
        return path;
    }

}
