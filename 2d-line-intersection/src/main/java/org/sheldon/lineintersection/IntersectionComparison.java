package org.sheldon.lineintersection;

import edu.princeton.cs.algs4.Point2D;

import java.util.Random;

public class IntersectionComparison {
    public static void main(String[] args) {
        // args[0] is the number of horizontal & vertical lines being simulated
        int n = Integer.parseInt(args[0]);

        Random random = new Random();
        long timer;
        Iterable<Point2D> iPoints;

        BruteIntersections bruteIntersections = new BruteIntersections();
        SweepLineIntersections sweepLineIntersections = new SweepLineIntersections();

        // Horizontal & vertical lines
        for (int i = 0; i < n; i++) {
            double xFrom = random.nextDouble();
            while (xFrom < LineSegment.DELTA || Math.abs(1.0-xFrom) < LineSegment.DELTA)
                xFrom = random.nextDouble();
            double xTo = random.nextDouble();
            while (Math.abs(xFrom-xTo) < LineSegment.DELTA)
                xTo = random.nextDouble();
            double y = random.nextDouble();
            LineSegment hLine = new LineSegment(new Point2D(xFrom, y), new Point2D(xTo, y));
            bruteIntersections.addLine(hLine);
            sweepLineIntersections.addLine(hLine);

            double x = random.nextDouble();
            double yFrom = random.nextDouble();
            while (yFrom < LineSegment.DELTA || Math.abs(1.0-yFrom) < LineSegment.DELTA)
                yFrom = random.nextDouble();
            double yTo = random.nextDouble();
            while (Math.abs(yFrom-yTo) < LineSegment.DELTA)
                yTo = random.nextDouble();
            LineSegment vLine = new LineSegment(new Point2D(x, yFrom), new Point2D(x, yTo));
            bruteIntersections.addLine(vLine);
            sweepLineIntersections.addLine(vLine);
        }

        // Brute force - quadratic algorithm
        timer = System.currentTimeMillis();
        iPoints = bruteIntersections.intersections();
        timer = System.currentTimeMillis() - timer;
        System.out.println("Brute: Intersections: " + iPoints.spliterator().getExactSizeIfKnown() +
                " {took: " + timer + "}");
//        iPoints.sort(Point2D::compareTo);
//        System.out.println(Arrays.toString(iPoints.toArray()));

        // Sweep line - linearithmetic
        timer = System.currentTimeMillis();
        iPoints = sweepLineIntersections.intersections();
        timer = System.currentTimeMillis() - timer;
        System.out.println("Sweep: Intersections: " + iPoints.spliterator().getExactSizeIfKnown() +
                " {took: " + timer + "}");
//        iPoints.sort(Point2D::compareTo);
//        System.out.println(Arrays.toString(iPoints.toArray()));
    }
}
