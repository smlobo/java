package org.sheldon.smallestcircle;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class BruteSmallest extends Smallest {

    public BruteSmallest(Point[] points) {
        super(points);
    }

    public Circle smallestEnclosingCircle() {
        // Smallest enclosing circle for all pairs
        long bruteTimer = System.currentTimeMillis();
        checkPairs();
        bruteTimer = System.currentTimeMillis()  - bruteTimer;
        StdDraw.setPenColor(Color.MAGENTA);
        smallest.draw();

        // See which finds the smaller circle
        System.out.println("Brute SEC Pair: " + smallest + " [took: " + bruteTimer + "]");
        System.out.println("                " + pairA + " <-> " + pairB);
        Circle pairSmallest = smallest;
        smallest = new Circle(new Point(0.0, 0.0), 2.0);

        // Smallest enclosing circle for all triples
        bruteTimer = System.currentTimeMillis();
        checkTriples();
        bruteTimer = System.currentTimeMillis() - bruteTimer;
        StdDraw.setPenColor(Color.GREEN);
        smallest.draw();

        System.out.println("Brute SEC Triple: " + smallest + " [took: " + bruteTimer + "]");
        System.out.println("                  " + tripleA + " ; " + tripleB + " ; " + tripleC);

        if (pairSmallest.compareTo(smallest) < 0)
            smallest = pairSmallest;

        System.out.println("Brute SEC: " + smallest);
        return smallest;
    }

    private void checkPairs() {
        // Iterate over all pairs of points
        for (int i = 0; i < points.length; i++) {
            for (int j = i+1; j < points.length; j++) {
                Circle pairCircle = getPairCircle(points[i], points[j]);

                if (!enclosesAll(pairCircle))
                    continue;

                if (smallest.compareTo(pairCircle) > 0) {
                    smallest = pairCircle;
                    pairA = points[i];
                    pairB = points[j];
                }
            }
        }
    }

    private void checkTriples() {
        // Iterate over all triples of points
        for (int i = 0; i < points.length; i++) {
            for (int j = i+1; j < points.length; j++) {
                for (int k = j+1; k < points.length; k++) {
                    Circle tripleCircle = getTripleCircle(points[i], points[j], points[k]);

                    if (!enclosesAll(tripleCircle))
                        continue;

                    if (smallest.compareTo(tripleCircle) > 0) {
                        smallest = tripleCircle;
                        tripleA = points[i];
                        tripleB = points[j];
                        tripleC = points[k];
                    }
                }
            }
        }
    }
}
