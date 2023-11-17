package K1_Practice;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class TripleTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        Triple<Integer> tInt = new Triple<Integer>(a, b, c);
        System.out.printf("%.2f\n", tInt.max());
        System.out.printf("%.2f\n", tInt.avarage());
        tInt.sort();
        System.out.println(tInt);
        float fa = scanner.nextFloat();
        float fb = scanner.nextFloat();
        float fc = scanner.nextFloat();
        Triple<Float> tFloat = new Triple<Float>(fa, fb, fc);
        System.out.printf("%.2f\n", tFloat.max());
        System.out.printf("%.2f\n", tFloat.avarage());
        tFloat.sort();
        System.out.println(tFloat);
        double da = scanner.nextDouble();
        double db = scanner.nextDouble();
        double dc = scanner.nextDouble();
        Triple<Double> tDouble = new Triple<Double>(da, db, dc);
        System.out.printf("%.2f\n", tDouble.max());
        System.out.printf("%.2f\n", tDouble.avarage());
        tDouble.sort();
        System.out.println(tDouble);
    }
}

class Triple<T extends Number> {
    List<T> triple;

    public Triple(T num1, T num2, T num3) {
        triple = new ArrayList<>();

        triple.add(num1);
        triple.add(num2);
        triple.add(num3);
    }

    public double max() {
        return triple.stream().max(Comparator.comparing(Number::doubleValue)).get().doubleValue();
    }

    public double avarage() {
        return triple.stream().mapToDouble(Number::doubleValue).average().getAsDouble();
    }

    public void sort() {
        triple.sort(Comparator.comparing(Number::doubleValue));
    }

    @Override
    public String toString() {
        return String.format("%.2f %.2f %.2f", triple.get(0).doubleValue(), triple.get(1).doubleValue(), triple.get(2).doubleValue());
    }
}