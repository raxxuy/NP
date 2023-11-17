package K1_Practice;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class RiskTester {
    public static void main(String[] args) {
        Risk risk = new Risk();
        risk.processAttacksData(System.in);
    }
}

class Risk {
    public void processAttacksData(InputStream is) {
        Scanner scanner = new Scanner(is);

        while (scanner.hasNextLine()) {
            String[] players = scanner.nextLine().split(";");

            int[] dicesX = Arrays.stream(players[0].split("\\s+")).sorted(Comparator.comparing(String::valueOf).reversed()).mapToInt(Integer::parseInt).toArray();
            int[] dicesY = Arrays.stream(players[1].split("\\s+")).sorted(Comparator.comparing(String::valueOf).reversed()).mapToInt(Integer::parseInt).toArray();

            int remainingX = 0;
            int remainingY = 0;

            for (int i = 0; i < 3; i++) {
                if (dicesX[i] > dicesY[i]) remainingX++;
                else remainingY++;
            }

            System.out.printf("%d %d%n", remainingX, remainingY);
        }
    }
}