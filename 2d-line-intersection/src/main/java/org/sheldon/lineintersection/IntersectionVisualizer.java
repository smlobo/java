package org.sheldon.lineintersection;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class IntersectionVisualizer {
    public static void main(String[] args) throws IOException {
        // Read the file
        List<String> fileLines = Files.readAllLines(Paths.get(args[0]));

        // Brute force intersection finder
        //IntersectionsImpl intersections = new BruteIntersections();

        // Sweep Line intersection finder
        IntersectionsImpl intersections = new SweepLineIntersections();

        // Create the lines
        ArrayList<LineSegment> lines = new ArrayList<LineSegment>();
        for (String fileLine : fileLines) {
            String[] linePoints = fileLine.split(" +");
            assert linePoints.length == 4;
            Point2D pA = new Point2D(Float.parseFloat(linePoints[0]), Float.parseFloat(linePoints[1]));
            Point2D pB = new Point2D(Float.parseFloat(linePoints[2]), Float.parseFloat(linePoints[3]));
            LineSegment line = new LineSegment(pA, pB);
            System.out.println(line);
            lines.add(line);

            // Add to the intersection finder
            intersections.addLine(line);
        }

        // Draw the lines
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenRadius(0.002);
        for (int i = 0; i < lines.size(); i++) {
            StdDraw.setPenColor(StdDraw.BLUE);
            lines.get(i).draw();
        }
        StdDraw.show();

        // User draws vertical line
        double x0 = 0.0, y0 = 0.0;      // initial endpoint of line
        double x1 = 0.0, y1 = 0.0;      // current location of mouse
        boolean isDragging = false;     // is the user dragging a line

        while (true) {

            // user starts to drag a line
            if (StdDraw.mousePressed() && !isDragging) {
                x0 = StdDraw.mouseX();
                y0 = StdDraw.mouseY();
                isDragging = true;
                continue;
            }

            // user is dragging a line
            else if (StdDraw.mousePressed() && isDragging) {
                x1 = StdDraw.mouseX();
                y1 = StdDraw.mouseY();
                continue;
            }

            // mouse no longer pressed
            else if (!StdDraw.mousePressed() && isDragging) {
                isDragging = false;
            }

            // Mid point of x
            double xMid = (x0 + x1) / 2;
            LineSegment line = new LineSegment(new Point2D(xMid, y0), new Point2D(xMid, y1));
            StdDraw.setPenColor(StdDraw.RED);
            line.draw();

            // Add this line to the intersection finder
            intersections.addLine(line);

            // Run the intersection finder
            Iterable<Point2D> intersectionPoints = intersections.intersections();

            // Highlight the intersection points
            StdDraw.setPenColor(StdDraw.BLACK);
            for (Point2D intersectionPoint : intersectionPoints) {
                StdDraw.circle(intersectionPoint.x(), intersectionPoint.y(), 0.01);
            }
            StdDraw.show();
            StdDraw.pause(40);
        }
    }
}