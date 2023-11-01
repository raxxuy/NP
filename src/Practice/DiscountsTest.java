package Practice;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DiscountsTest {
    public static void main(String[] args) {
        Discounts discounts = new Discounts();
        int stores = discounts.readStores(System.in);
        System.out.println("Stores read: " + stores);
        System.out.println("=== By average discount ===");
        discounts.byAverageDiscount().forEach(System.out::println);
        System.out.println("=== By total discount ===");
        discounts.byTotalDiscount().forEach(System.out::println);
    }
}

class Store {
    String name;
    double[] discounts;
    double[] prices;

    public Store(String name, double[] discounts, double[] prices) {
        this.name = name;
        this.discounts = discounts;
        this.prices = prices;
    }
}

class Discounts {
    List<Store> stores;

    public Discounts() {
        stores = new ArrayList<>();
    }

    public int readStores(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        int storesRead = 0;

        while (scanner.hasNextLine()) {
            String[] strings = scanner.nextLine().split("[ :]");
            double[] discounts = new double[(strings.length - 1) / 2];
            double[] prices = new double[discounts.length];

            for (int i = 1; i < strings.length - 1; i++) {
                discounts[i - 1] = Double.parseDouble(strings[i]);
            }
        }
    }
}