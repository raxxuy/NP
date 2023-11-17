package K1_Practice;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

public class RiskTester {
    public static void main(String[] args) {

        Risk risk = new Risk();

        System.out.println(risk.processAttacksData(System.in));

    }
}

class Risk {
    public int processAttacksData(InputStream is) {
        Scanner scanner = new Scanner(is);

        int successfulAttacks = 0;

        while (scanner.hasNextLine()) {
            String[] players = scanner.nextLine().split(";");

            int[] dicesX = Arrays.stream(players[0].split("\\s+")).mapToInt(Integer::parseInt).sorted().toArray();
            int[] dicesY = Arrays.stream(players[1].split("\\s+")).mapToInt(Integer::parseInt).sorted().toArray();

            if (dicesX[0] > dicesY[0] && dicesX[1] > dicesY[1] && dicesX[2] > dicesY[2]) successfulAttacks++;
        }

        return successfulAttacks;
    }
}
