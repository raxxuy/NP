package K1_Practice;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ShoppingTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ShoppingCart cart = new ShoppingCart();

        int items = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < items; i++) {
            try {
                cart.addItem(sc.nextLine());
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        }

        List<Integer> discountItems = new ArrayList<>();
        int discountItemsCount = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < discountItemsCount; i++) {
            discountItems.add(Integer.parseInt(sc.nextLine()));
        }

        int testCase = Integer.parseInt(sc.nextLine());
        if (testCase == 1) {
            cart.printShoppingCart(System.out);
        } else if (testCase == 2) {
            try {
                cart.blackFridayOffer(discountItems, System.out);
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Invalid test case");
        }
    }
}

class InvalidOperationException extends Exception {
    public InvalidOperationException() {
        super("There are no products with discount.");
    }

    public InvalidOperationException(String id) {
        super(String.format("The quantity of the product with id %s can not be 0.", id));
    }
}

abstract class Item {
    int id;
    String name;
    int price;

    public Item(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    abstract public double totalPrice();

    public void blackFridayOffer() {
        System.out.printf("%d - %.2f%n", id, totalPrice() / 10);
    }

    @Override
    public String toString() {
        return String.format("%d - %.2f", id, totalPrice());
    }
}

class WSItem extends Item {
    int quantity;

    public WSItem(int id, String name, int price, int quantity) {
        super(id, name, price);
        this.quantity = quantity;
    }

    @Override
    public double totalPrice() {
        return price * quantity;
    }
}

class PSItem extends Item {
    double quantity;

    public PSItem(int id, String name, int price, double quantity) {
        super(id, name, price);
        this.quantity = quantity;
    }

    @Override
    public double totalPrice() {
        return price * quantity / 1000;
    }
}

class ShoppingCart {
    List<Item> items;

    public ShoppingCart() {
        items = new ArrayList<>();
    }

    public void addItem(String itemData) throws InvalidOperationException {
        String[] sections = itemData.split(";");

        if (sections[4].equals("0")) throw new InvalidOperationException(sections[1]);

        if (sections[0].equals("WS")) items.add(new WSItem(Integer.parseInt(sections[1]), sections[2], Integer.parseInt(sections[3]), Integer.parseInt(sections[4])));
        else items.add(new PSItem(Integer.parseInt(sections[1]), sections[2], Integer.parseInt(sections[3]), Double.parseDouble(sections[4])));
    }

    public void printShoppingCart(OutputStream os) {
        System.setOut(new PrintStream(os));
        items.stream().sorted(Comparator.comparing(Item::totalPrice).reversed()).forEach(System.out::println);
    }

    public void blackFridayOffer(List<Integer> discountItems, OutputStream os) throws InvalidOperationException {
        System.setOut(new PrintStream(os));

        if (discountItems.isEmpty()) throw new InvalidOperationException();

        items.stream().filter(item -> discountItems.stream().anyMatch(discount -> item.id == discount)).forEach(Item::blackFridayOffer);
    }
}