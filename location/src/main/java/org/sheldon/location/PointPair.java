package org.sheldon.location;

import edu.princeton.cs.algs4.StdDraw;

public class PointPair {
    private Point p1;
    private Point p2;

    public PointPair(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Point p1() {
        return p1;
    }

    public Point p2() {
        return p2;
    }

    @Override
    public String toString() {
        return p1 + "," + p2;
    }

    public void draw() {
        StdDraw.filledCircle(p1.xCoord(), p1.yCoord(), 0.005);
        StdDraw.filledCircle(p2.xCoord(), p2.yCoord(), 0.005);
    }
}
