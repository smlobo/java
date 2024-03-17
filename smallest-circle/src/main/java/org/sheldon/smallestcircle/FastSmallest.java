package org.sheldon.smallestcircle;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Iterator;

public class FastSmallest extends Smallest {
    // Used to find the points furthest apart
    KdTree kdTree;
    // Leftmost point. Calculate slope relative to this for the Graham scan sort
    Point leftmost = new Point(1.0 + Util.DELTA, 0.0);

    public FastSmallest(Point[] points) {
        super(points);

        // Build the KD-Tree
        // Also, find the Point with the lowest Y-coordinate
        kdTree = new KdTree();
        for (Point p : this.points) {
            kdTree.insert(p);
            if (p.xCoord() < leftmost.xCoord())
                leftmost = p;
        }
    }

    public Circle smallestEnclosingCircle() {
        // Smallest enclosing circle for all pairs
        long fastTimer = System.currentTimeMillis();
        checkPairs();
        fastTimer = System.currentTimeMillis() - fastTimer;
        StdDraw.setPenColor(Color.BLUE);
        smallest.draw();

        // See which finds the smaller circle
        System.out.println("Fast SEC Pair: " + smallest + " [took: " + fastTimer + "]");
        System.out.println("               " + pairA + " <-> " + pairB);
        Circle pairSmallest = smallest;
        smallest = new Circle(new Point(0.0, 0.0), 2.0);

        // Smallest enclosing circle for all triples
        fastTimer = System.currentTimeMillis();
        checkTriples();
        fastTimer = System.currentTimeMillis() - fastTimer;
        StdDraw.setPenColor(Color.ORANGE);
        smallest.draw();

        System.out.println("Fast SEC Triple: " + smallest + " [took: " + fastTimer + "]");
        System.out.println("                 " + tripleA + " ; " + tripleB + " ; " + tripleC);

        if (pairSmallest.compareTo(smallest) < 0)
            smallest = pairSmallest;

        System.out.println("Fast SEC: " + smallest);
        return smallest;
    }

    private void checkPairs() {
        // Iterate over all points, finding the furthest point for each
        // Find the 2 points furthest apart in this set. The circle formed by this pair is
        // the ONLY chance for enclosing all points in this set.
        double longestD = 0.0;
        Point longestPointA = new Point(0.0, 0.0);
        Point longestPointB = longestPointA;

        Iterator<Point> pointIterator = kdTree.iterator();
        while (pointIterator.hasNext()) {
            Point point = pointIterator.next();
            Point farthest = kdTree.farthest(point);
            double distance = point.distanceSquaredTo(farthest);
            if (distance > longestD) {
                longestD = distance;
                longestPointA = point;
                longestPointB = farthest;
            }
        }
        //System.out.printf("Longest distance: %.3f %s <-> %s\n", longestD, longestPointA, longestPointB);

        Circle pairCircle = getPairCircle(longestPointA, longestPointB);
        if (enclosesAll(pairCircle)) {
            smallest = pairCircle;
            pairA = longestPointA;
            pairB = longestPointB;
        }
    }

    private void checkTriples() {
        // Sort points by the angle (slope) from the lowest point
        Arrays.sort(points, leftmost.slopeOrder());

        // Get the points on the convex hull using the Graham scan algo
        Point[] convexHull = grahamScan();

        // Iterate over all triples of points - on the convex hull only
        for (int i = 0; i < convexHull.length; i++) {
            for (int j = i+1; j < convexHull.length; j++) {
                for (int k = j+1; k < convexHull.length; k++) {
                    Circle tripleCircle = getTripleCircle(convexHull[i], convexHull[j], convexHull[k]);

                    if (!enclosesAll(tripleCircle))
                        continue;

                    if (smallest.compareTo(tripleCircle) > 0) {
                        smallest = tripleCircle;
                        tripleA = convexHull[i];
                        tripleB = convexHull[j];
                        tripleC = convexHull[k];

                    }
                }
            }
        }

    }

    private Point[] grahamScan() {
        assert(leftmost == points[0]);
        //System.out.println("Graham Scan ...");

        // The Graham Scan stack
        ArrayDeque<Point> stack = new ArrayDeque<>();

        // Add the first 2 points
        stack.addFirst(points[0]);
        stack.addFirst(points[1]);

        // Iterate over all the sorted by angle to the reference (first) point
        // To close the polygon, go back to the starting point
        for (int i = 2; i <= points.length; i++) {
            Point current;
            if (i == points.length)
                current = points[0];
            else
                current = points[i];

            // Get the previous 2 points
            Point previous = stack.removeFirst();
            Point pPrevious = stack.peekFirst();

            //System.out.println(i + " : " + pPrevious + " -> " + previous + " -> " + current + " ccw: " +
            //        Util.ccw(pPrevious, previous, current));

            // While the turn is clockwise, pop the previous stack entry
            while (Util.ccw(pPrevious, previous, current) < 0.0) {
                previous = stack.pop();
                pPrevious = stack.peek();
            }

            // Anti-clockwise turn or collinear - add
            // Put the previous popped back in
            stack.addFirst(previous);
            stack.addFirst(current);
        }

        return stack.toArray(new Point[0]);
    }

    private void testGrahamScan() {
        // Sort points by the angle (slope) from the lowest point
        Arrays.sort(points, leftmost.slopeOrder());

        // Print
        System.out.println("Sorted by angle ... (reference: " + leftmost);
        for (Point p : points) {
            System.out.printf("  %s; slope: %.3f\n", p, leftmost.slopeTo(p));
        }

        // Get the points on the convex hull using the Graham scan algo
        Point[] convexHull = grahamScan();

        StdDraw.setPenColor(Color.RED);

        // Draw lines for the convex hull
        Point previous = null;
        for (Point current : convexHull) {
            // Draw a line
            if (previous != null)
                StdDraw.line(previous.xCoord(), previous.yCoord(), current.xCoord(), current.yCoord());

            previous = current;
        }
    }

    public static void main(String[] args) throws IOException {
        // Unit testing with text input files
        Point[] points = Solution.readFile(args);

        // Draw the input
        Solution.draw(points);
        Solution.drawGrid();

        FastSmallest fs = new FastSmallest(points);
        fs.testGrahamScan();
    }
}
