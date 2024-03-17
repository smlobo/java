package org.sheldon.location;

import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
    private float xCoord;
    private float yCoord;

    public Point(float xCoord, float yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public float xCoord() {
        return xCoord;
    }

    public float yCoord() {
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

    public float slopeTo(Point that) {
        float xDiff = that.xCoord-this.xCoord;
        //if (xDiff < Util.DELTA)
        //    return Float.POSITIVE_INFINITY;
        return (that.yCoord-this.yCoord)/xDiff;
    }

    public void draw() {
        StdDraw.filledCircle(xCoord, yCoord, 0.005);
    }
}
