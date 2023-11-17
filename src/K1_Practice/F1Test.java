package K1_Practice;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class F1Test {

    public static void main(String[] args) {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }

}

class Driver {
    String name;
    String[] laps;

    public Driver(String name, String lap1, String lap2, String lap3) {
        laps = Arrays.stream(new String[]{lap1, lap2, lap3}).sorted().toArray(String[]::new);
        this.name = name;
    }

    public String bestLap() {
        return laps[0];
    }

    @Override
    public String toString() {
        return String.format("%-10s%10s", name, bestLap());
    }
}

class F1Race {
    List<Driver> drivers;

    public F1Race() {
        drivers = new ArrayList<>();
    }

    public void readResults(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        bufferedReader.lines().forEach(line -> {
            String[] sections = line.split(" ");
            drivers.add(new Driver(sections[0], sections[1], sections[2], sections[3]));
        });
    }

    public void printSorted(OutputStream outputStream) {
        System.setOut(new PrintStream(outputStream));

        List<Driver> sortedDrivers = drivers.stream().sorted(Comparator.comparing(Driver::bestLap)).collect(Collectors.toList());

        for (int i = 0; i < sortedDrivers.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, sortedDrivers.get(i));
        }
    }
}