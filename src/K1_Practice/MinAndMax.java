package K1_Practice;

import java.util.Scanner;

public class MinAndMax {
    public static void main(String[] args) throws ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        MinMax<String> strings = new MinMax<String>();
        for(int i = 0; i < n; ++i) {
            String s = scanner.next();
            strings.update(s);
        }
        System.out.println(strings);
        MinMax<Integer> ints = new MinMax<Integer>();
        for(int i = 0; i < n; ++i) {
            int x = scanner.nextInt();
            ints.update(x);
        }
        System.out.println(ints);
    }
}

class MinMax<T extends Comparable<T>> {
    T min;
    T max;
    int attempts;
    int minAttempts;
    int maxAttempts;

    public MinMax() {
        attempts = 0;
        minAttempts = 0;
        maxAttempts = 0;
    }

    void update(T element) {
        if (attempts == 0) {
            min = max = element;
            minAttempts = maxAttempts = 1;
        } else {
            if (min.compareTo(element) > 0) {
                min = element;
                minAttempts = 1;
            } else if (min.compareTo(element) == 0) minAttempts++;

            if (max.compareTo(element) < 0) {
                max = element;
                maxAttempts = 1;
            } else if (max.compareTo(element) == 0) maxAttempts++;
        }

        attempts++;
    }

    public T max() {
        return max;
    }

    public T min() {
        return min;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d%n", min, max, attempts - (minAttempts + maxAttempts));
    }
}