package K1_Practice;

import java.util.*;

enum Color {
    RED, GREEN, BLUE
}

public class ShapesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Canvas canvas = new Canvas();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            int type = Integer.parseInt(parts[0]);
            String id = parts[1];
            if (type == 1) {
                Color color = Color.valueOf(parts[2]);
                float radius = Float.parseFloat(parts[3]);
                canvas.add(id, color, radius);
            } else if (type == 2) {
                Color color = Color.valueOf(parts[2]);
                float width = Float.parseFloat(parts[3]);
                float height = Float.parseFloat(parts[4]);
                canvas.add(id, color, width, height);
            } else if (type == 3) {
                float scaleFactor = Float.parseFloat(parts[2]);
                System.out.println("ORIGNAL:");
                System.out.print(canvas);
                canvas.scale(id, scaleFactor);
                System.out.printf("AFTER SCALING: %s %.2f\n", id, scaleFactor);
                System.out.print(canvas);
            }

        }
    }
}

interface Scalable {
    void scale(float scaleFactor);
}

interface Stackable {
    float weight();
}

class Shape implements Scalable, Stackable {
    String id;
    Color color;

    public Shape(String id, Color color) {
        this.id = id;
        this.color = color;
    }

    @Override
    public void scale(float scaleFactor) {}

    @Override
    public float weight() {
        return 0;
    }

    @Override
    public String toString() {
        return String.format("%-5s%-10s%10.2f%n", id, color, weight());
    }
}

class Circle extends Shape {
    float radius;

    public Circle(String id, Color color, float radius) {
        super(id, color);
        this.radius = radius;
    }

    @Override
    public void scale(float scaleFactor) {
        radius *= scaleFactor;
    }

    @Override
    public float weight() {
        return (float) (Math.PI * radius * radius);
    }

    @Override
    public String toString() {
        return "C: " + super.toString();
    }
}

class Rectangle extends Shape {
    float width;
    float height;

    public Rectangle(String id, Color color, float width, float height) {
        super(id, color);
        this.width = width;
        this.height = height;
    }

    @Override
    public void scale(float scaleFactor) {
        width *= scaleFactor;
        height *= scaleFactor;
    }

    @Override
    public float weight() {
        return width * height;
    }

    @Override
    public String toString() {
        return "R: " + super.toString();
    }
}

class Canvas {
    List<Shape> shapes;

    public Canvas() {
        shapes = new ArrayList<>();
    }

    public int findIndex(Shape shape) {
        for (int i = 0; i < shapes.size(); i++) {
            if (shapes.get(i).weight() < shape.weight()) return i;
        }

        return shapes.size();
    }

    public void add(String id, Color color, float radius) {
        Circle circle = new Circle(id, color, radius);
        shapes.add(findIndex(circle), circle);
    }

    public void add(String id, Color color, float width, float height) {
        Rectangle rectangle = new Rectangle(id, color, width, height);
        shapes.add(findIndex(rectangle), rectangle);
    }

    public void scale(String id, float scaleFactor) {
        Shape scaledShape = shapes.stream().filter(shape -> shape.id.equals(id)).findFirst().get();
        shapes.remove(scaledShape);

        scaledShape.scale(scaleFactor);
        shapes.add(findIndex(scaledShape), scaledShape);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        shapes.forEach(sb::append);
        return sb.toString();
    }
}