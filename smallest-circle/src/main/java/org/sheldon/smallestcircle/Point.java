package org.sheldon.smallestcircle;

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    private double xCoord;
    private double yCoord;

    public Point(double xCoord, double yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public double xCoord() {
        return xCoord;
    }

    public double yCoord() {
        return yCoord;
    }

    @Override
    public String toString() {
        return String.format("<%.3f,%.3f>", xCoord, yCoord);
    }

    @Override
    public int compareTo(Point that) {
        // First by y coordinate
        if (this.yCoord < that.yCoord)
            return -1;
        else if (this.yCoord > that.yCoord)
            return 1;
        // Then by x coordinate
        if (this.xCoord < that.xCoord)
            return -1;
        else if (this.xCoord > that.xCoord)
            return 1;
        return 0;
    }

    public double slopeTo(Point that) {
        double xDiff = that.xCoord - this.xCoord;
        double yDiff = that.yCoord - this.yCoord;

        // Corner case - vertical line
        if (Math.abs(xDiff) < Util.DELTA) {
            // We want this reference leftmost AND lowest point to be first in the sort
            if (Math.abs(yDiff) < Util.DELTA)
                return Double.NEGATIVE_INFINITY;
            // Any other point on this vertical line will be above the reference point
            else
                return Double.POSITIVE_INFINITY;
        }

        return yDiff/xDiff;
    }

    public double distanceSquaredTo(Point that) {
        double dx = this.xCoord - that.xCoord;
        double dy = this.yCoord - that.yCoord;
        return dx * dx + dy * dy;
    }

    public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                // This reference point is the leftmost & lowest - sort form -oo to +oo
                if (slopeTo(o1) < slopeTo(o2))
                    return -1;
                else if (slopeTo(o1) > slopeTo(o2))
                    return 1;

                // The 3 points are collinear - place the farthest away first
                if (distanceSquaredTo(o1) > distanceSquaredTo(o2))
                    return -1;
                else if (distanceSquaredTo(o1) < distanceSquaredTo(o2))
                    return 1;

                return 0;
            }
        };
    }

    public void draw() {
        StdDraw.filledCircle(xCoord, yCoord, 0.008);
    }
}
