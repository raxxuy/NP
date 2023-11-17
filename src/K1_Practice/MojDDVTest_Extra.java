package K1_Practice;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MojDDVTest {

    public static void main(String[] args) {

        MojDDV mojDDV = new MojDDV();

        System.out.println("===READING RECORDS FROM INPUT STREAM===");
        mojDDV.readRecords(System.in);

        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
        mojDDV.printTaxReturns(System.out);

        System.out.println("===PRINTING SUMMARY STATISTICS FOR TAX RETURNS TO OUTPUT STREAM===");
        mojDDV.printStatistics(System.out);

    }
}

class AmountNotAllowedException extends Exception {
    public AmountNotAllowedException(int amount) {
        super(String.format("Receipt with amount %d is not allowed to be scanned", amount));
    }
}

class Item {
    int price;
    String type;

    public Item(int price, String type) {
        this.price = price;
        this.type = type;
    }

    public double tax() {
        if (type.equals("A")) return price * 0.18 * 0.15;
        else if (type.equals("B")) return price * 0.05 * 0.15;
        else return 0;
    }
}

class Receipt {
    String id;
    List<Item> items;

    public Receipt(String id, List<Item> items) {
        this.id = id;
        this.items = items;
    }

    public int amount() {
        return items.stream().mapToInt(item -> item.price).sum();
    }

    public double taxReturn() {
        return items.stream().mapToDouble(Item::tax).sum();
    }

    @Override
    public String toString() {
        return String.format("%10s\t%10d\t%10.5f", id, amount(), taxReturn());
    }
}

class MojDDV {
    List<Receipt> receipts;

    public MojDDV() {
        receipts = new ArrayList<>();
    }

    public void readRecords (InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        bufferedReader.lines().forEach(line -> {
            String[] sections = line.split("\\s+");

            String id = sections[0];
            List<Item> items = new ArrayList<>();

            for (int i = 1; i < sections.length; i += 2) {
                items.add(new Item(Integer.parseInt(sections[i]), sections[i + 1]));
            }

            Receipt receipt = new Receipt(id, items);

            if (receipt.amount() > 30000) try {
                throw new AmountNotAllowedException(receipt.amount());
            } catch (AmountNotAllowedException e) {
                System.out.println(e.getMessage());
            } else receipts.add(receipt);
        });
    }

    public void printTaxReturns (OutputStream outputStream) {
        System.setOut(new PrintStream(outputStream));
        receipts.forEach(System.out::println);
    }

    public void printStatistics (OutputStream outputStream) {
        System.setOut(new PrintStream(outputStream));
        System.out.printf("min:\t%.3f%n", receipts.stream().mapToDouble(Receipt::taxReturn).min().getAsDouble());
        System.out.printf("max:\t%.3f%n", receipts.stream().mapToDouble(Receipt::taxReturn).max().getAsDouble());
        System.out.printf("sum:\t%.3f%n", receipts.stream().mapToDouble(Receipt::taxReturn).sum());
        System.out.printf("count:\t%d%n", receipts.size());
        System.out.printf("avg:\t%.3f", receipts.stream().mapToDouble(Receipt::taxReturn).average().getAsDouble());
    }
}