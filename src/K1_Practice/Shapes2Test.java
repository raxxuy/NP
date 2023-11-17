package K1_Practice;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Shapes2Test {

    public static void main(String[] args) {

        ShapesApplication shapesApplication = new ShapesApplication(10000);

        System.out.println("===READING CANVASES AND SHAPES FROM INPUT STREAM===");
        shapesApplication.readCanvases(System.in);

        System.out.println("===PRINTING SORTED CANVASES TO OUTPUT STREAM===");
        shapesApplication.printCanvases(System.out);


    }
}

class IrregularCanvasException extends Exception {
    public IrregularCanvasException(String id, double maxArea) {
        super(String.format("Canvas %s has a shape with area larger than %.2f", id, maxArea));
    }
}

abstract class Shape {
    String type;
    int size;

    public Shape(String type, int size) {
        this.type = type;
        this.size = size;
    }

    abstract public double area();
}

class Circle extends Shape {
    public Circle(String type, int size) {
        super(type, size);
    }

    @Override
    public double area() {
        return Math.PI * size * size;
    }
}

class Square extends Shape {
    public Square(String type, int size) {
        super(type, size);
    }

    @Override
    public double area() {
        return size * size;
    }
}

class Canvas {
    String id;
    List<Shape> shapes;

    public Canvas(String id, List<Shape> shapes) {
        this.id = id;
        this.shapes = shapes;
    }

    public double minArea() {
        return shapes.stream().min(Comparator.comparing(Shape::area)).get().area();
    }

    public double maxArea() {
        return shapes.stream().max(Comparator.comparing(Shape::area)).get().area();
    }

    public double averageArea() {
        return shapes.stream().mapToDouble(Shape::area).average().getAsDouble();
    }

    public double totalArea() {
        return shapes.stream().mapToDouble(Shape::area).sum();
    }

    @Override
    public String toString() {
        return String.format("%s %d %d %d %.2f %.2f %.2f", id, shapes.size(),
                shapes.stream().filter(shape -> shape.type.equals("C")).count(),
                shapes.stream().filter(shape -> shape.type.equals("S")).count(),
                minArea(),
                maxArea(),
                averageArea());
    }
}

class ShapesApplication {
    List<Canvas> canvases;
    double maxArea;

    public ShapesApplication(double maxArea) {
        canvases = new ArrayList<>();
        this.maxArea = maxArea;
    }

    public void readCanvases (InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        bufferedReader.lines().forEach(line -> {
            String[] sections = line.split("\\s+");

            String id = sections[0];
            List<Shape> shapes = new ArrayList<>();

            for (int i = 1; i < sections.length; i += 2) {
                if (sections[i].equals("C")) shapes.add(new Circle(sections[i], Integer.parseInt(sections[i + 1])));
                else shapes.add(new Square(sections[i], Integer.parseInt(sections[i + 1])));
            }

            if (shapes.stream().anyMatch(shape -> shape.area() > maxArea)) try {
                throw new IrregularCanvasException(id, maxArea);
            } catch (IrregularCanvasException e) {
                System.out.println(e.getMessage());
            } else canvases.add(new Canvas(id, shapes));
        });
    }

    public void printCanvases (OutputStream os) {
        System.setOut(new PrintStream(os));
        canvases.stream().sorted(Comparator.comparing(Canvas::totalArea).reversed()).forEach(System.out::println);
    }
}