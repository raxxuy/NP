package K1_Practice;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

class Line {
    Double coeficient;
    Double x;
    Double intercept;

    public Line(Double coeficient, Double x, Double intercept) {
        this.coeficient = coeficient;
        this.x = x;
        this.intercept = intercept;
    }

    public static Line createLine(String line) {
        String[] parts = line.split("\\s+");
        return new Line(
                Double.parseDouble(parts[0]),
                Double.parseDouble(parts[1]),
                Double.parseDouble(parts[2])
        );
    }

    public double calculateLine() {
        return coeficient * x + intercept;
    }

    @Override
    public String toString() {
        return String.format("%.2f * %.2f + %.2f", coeficient, x, intercept);
    }
}

public class EquationTester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCase = Integer.parseInt(sc.nextLine());

        if (testCase == 1) { // Testing with Integer, Integer
            List<Equation<Integer, Integer>> equations1 = new ArrayList<>();
            List<Integer> inputs = new ArrayList<>();
            while (sc.hasNext()) {
                inputs.add(Integer.parseInt(sc.nextLine()));
            }

            // TODO: Add an equation where you get the 3rd integer from the inputs list, and the result is the sum of that number and the number 1000.
            equations1.add(new Equation<>(() -> inputs.get(2), input -> input + 1000));

            // TODO: Add an equation where you get the 4th integer from the inputs list, and the result is the maximum of that number and the number 100.
            equations1.add(new Equation<>(() -> inputs.get(3), input -> Integer.max(input, 100)));
            EquationProcessor.process(inputs, equations1);

        } else { // Testing with Line, Integer
            List<Equation<Line, Double>> equations2 = new ArrayList<>();
            List<Line> inputs = new ArrayList<>();
            while (sc.hasNext()) {
                inputs.add(Line.createLine(sc.nextLine()));
            }

            //TODO Add an equation where you get the 2nd line, and the result is the value of y in the line equation.
            equations2.add(new Equation<>(() -> inputs.get(1), Line::calculateLine));

            //TODO Add an equation where you get the 1st line, and the result is the sum of all y values for all lines that have a greater y value than that equation.
            equations2.add(new Equation<>(() -> inputs.get(0), line -> inputs.stream().filter(input -> input.calculateLine() > line.calculateLine()).mapToDouble(Line::calculateLine).sum()));
            EquationProcessor.process(inputs, equations2);
        }
    }
}

class Equation<Input, Output> {
    Supplier<Input> supplier;
    Function<Input, Output> function;

    public Equation(Supplier<Input> supplier, Function<Input, Output> function) {
        this.supplier = supplier;
        this.function = function;
    }

    public Optional<Output> calculate() {
        return Optional.of(function.apply(supplier.get()));
    }
}

class EquationProcessor {
    public static <Input, Output> void process(List<Input> inputs, List<Equation<Input, Output>> equations) {
        inputs.forEach(input -> {
            System.out.printf("Input: %s%n", input);

            equations.forEach(equation -> {
                Optional<Output> output = equation.calculate();

                if (input.equals(inputs.get(inputs.size() - 1))) System.out.printf("Result: %s%n", output.get());
            });
        });
    }
}