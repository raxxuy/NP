package Practice;

import java.io.*;
import java.util.*;

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
    String canvasId;
    int[] canvasSizes;

    public Canvas(String canvasId, int[] canvasSizes) {
        this.canvasId = canvasId;
        this.canvasSizes = canvasSizes;
    }

    public int perimeter() {
        return 4 * Arrays.stream(canvasSizes).sum();
    }

    @Override
    public String toString() {
        return String.format("%s %d %d", canvasId, canvasSizes.length, perimeter());
    }
}

class ShapesApplication {
    List<Canvas> canvases;

    public ShapesApplication() {
        canvases = new ArrayList<>();
    }

    public int readCanvases(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        int sizes = 0;

        while (scanner.hasNextLine()) {
            String[] strings = scanner.nextLine().split(" ");
            int[] canvasSizes = new int[strings.length - 1];

            for (int i = 1; i < strings.length; i++) {
                canvasSizes[i - 1] = Integer.parseInt(strings[i]);
                sizes++;
            }

            canvases.add(new Canvas(strings[0], canvasSizes));
        }

        return sizes;
    }

    public void printLargestCanvasTo(OutputStream outputStream) {
        System.out.println(canvases.stream().max(Comparator.comparing(Canvas::perimeter)).get());
    }
}