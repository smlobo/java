package org.sheldon.smallestcircle;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Solution {

    static void draw(Point[] points) {
        // Set the scale
        StdDraw.setXscale(-1.0, 1.0);
        StdDraw.setYscale(-1.0, 1.0);

        // Draw all points
        StdDraw.setPenColor(Color.BLUE);
        for (int i = 0; i < points.length; i++)
            points[i].draw();
    }

    static void drawGrid() {
        StdDraw.setPenColor(Color.GREEN);
        for (int i = 0; i < 10; i++) {
            double d = ((double)i)/10;
            StdDraw.line(-1.0, d, 1.0, d);
            StdDraw.line(-1.0, -d, 1.0, -d);
            StdDraw.line(d, -1.0, d, 1.0);
            StdDraw.line(-d, -1.0, -d, 1.0);
            StdDraw.setPenColor(Color.LIGHT_GRAY);
        }
    }
    public static void main(String[] args) throws IOException {
        Point[] points = readFile(args);

        // Draw the input
        draw(points);

        // Brute force
        long bruteTimer = System.currentTimeMillis();
        BruteSmallest brute = new BruteSmallest(points);
        Circle bruteSolution = brute.smallestEnclosingCircle();
        bruteTimer = System.currentTimeMillis() - bruteTimer;
        System.out.println("Brute solution: " + bruteSolution + " [took: " + bruteTimer + "]");

        // Fast
        long fastTimer = System.currentTimeMillis();
        FastSmallest fast = new FastSmallest(points);
        Circle fastSolution = fast.smallestEnclosingCircle();
        fastTimer = System.currentTimeMillis() - fastTimer;
        System.out.println("Fast solution: " + fastSolution + " [took: " + fastTimer + "]");
    }

    static Point[] readFile(String[] args) throws IOException {
        // input file is the argument
        if (args.length != 1) {
            System.out.println("Usage: java -jar ... <inputfile.txt>");
            System.exit(-1);
        }

        // Read the file
        BufferedReader bufferedReader = new BufferedReader(new FileReader(args[0]));

        // Read the number of line segments
        int n = Integer.parseInt(bufferedReader.readLine().trim());
        System.out.println("Reading in " + n + " lines");

        // Create the points
        ArrayList<Point> points = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String lineStr = bufferedReader.readLine().trim();
            String[] linePoints = lineStr.split(" ");
            //assert linePoints.length == 2;
            Point point = new Point(Double.parseDouble(linePoints[0]), Double.parseDouble(linePoints[linePoints.length-1]));
            points.add(point);
            System.out.println("Added point: " + point);
        }

        // Close the file stream
        bufferedReader.close();

        return points.toArray(new Point[0]);
    }
}
