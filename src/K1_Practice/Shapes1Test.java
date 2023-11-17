package K1_Practice;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Shapes1Test {

    public static void main(String[] args) {
        ShapesApplication shapesApplication = new ShapesApplication();

        System.out.println("===READING SQUARES FROM INPUT STREAM===");
        System.out.println(shapesApplication.readCanvases(System.in));
        System.out.println("===PRINTING LARGEST CANVAS TO OUTPUT STREAM===");
        shapesApplication.printLargestCanvasTo(System.out);

    }
}

class Canvas {
    String id;
    List<Integer> sizes;

    public Canvas(String id, List<Integer> sizes) {
        this.id = id;
        this.sizes = sizes;
    }

    public int perimeter() {
        return 4 * sizes.stream().mapToInt(i -> i).sum();
    }

    @Override
    public String toString() {
        return String.format("%s %d %d", id, sizes.size(), perimeter());
    }
}

class ShapesApplication {
    List<Canvas> canvases;

    public ShapesApplication() {
        canvases = new ArrayList<>();
    }

    public int readCanvases (InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        bufferedReader.lines().forEach(line -> {
            String[] sections = line.split("\\s+");

            String id = sections[0];
            List<Integer> sizes = new ArrayList<>();

            for (int i = 1; i < sections.length; i++) {
                sizes.add(Integer.parseInt(sections[i]));
            }

            canvases.add(new Canvas(id, sizes));
        });

        return canvases.stream().mapToInt(canvas -> canvas.sizes.size()).sum();
    }

    public void printLargestCanvasTo (OutputStream outputStream) {
        System.setOut(new PrintStream(outputStream));
        System.out.println(canvases.stream().max(Comparator.comparing(Canvas::perimeter)).get().toString());
    }
}