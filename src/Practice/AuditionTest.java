package Practice;

import java.util.*;

public class AuditionTest {
    public static void main(String[] args) {
        Audition audition = new Audition();
        List<String> cities = new ArrayList<String>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            if (parts.length > 1) {
                audition.addParticpant(parts[0], parts[1], parts[2],
                        Integer.parseInt(parts[3]));
            } else {
                cities.add(line);
            }
        }
        for (String city : cities) {
            System.out.printf("+++++ %s +++++\n", city);
            audition.listByCity(city);
        }
        scanner.close();
    }
}

class Canditate {
    private String city;
    private String code;
    private String name;
    private int age;

    public Canditate(String city, String code, String name, int age) {
        this.city = city;
        this.code = code;
        this.name = name;
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d", code, name, age);
    }
}

class Audition {
    private Set<Canditate> canditateSet;

    public Audition() {
        canditateSet = new HashSet<>();
    }

    public void addParticpant(String city, String code, String name, int age) {
        if (canditateSet.stream().filter(canditate -> canditate.getCity().equals(city)).anyMatch(canditate -> canditate.getCode().equals(code))) return;

        canditateSet.add(new Canditate(city, code, name, age));
    }

    public void listByCity(String city) {
        canditateSet.stream()
                .filter(canditate -> canditate.getCity().equals(city))
                .sorted(Comparator.comparing(Canditate::getName).thenComparing(Canditate::getAge).thenComparing(Canditate::getCode))
                .forEach(System.out::println);
    }
}