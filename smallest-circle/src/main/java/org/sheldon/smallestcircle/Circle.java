package org.sheldon.smallestcircle;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class Circle implements Comparable<Circle> {
    private Point center;
    private double radiusSquared;

    public Circle (Point center, double radiusSquared) {
        this.center = center;
        this.radiusSquared = radiusSquared;
    }

    public Point center() {
        return center;
    }

    public double radiusSquared() {
        return radiusSquared;
    }

    public boolean encloses(Point point) {
        // Distance to the circle center
        double xDist = Math.abs(point.xCoord() - center.xCoord());
        double yDist = Math.abs(point.yCoord() - center.yCoord());
        double distanceSquared = xDist * xDist + yDist * yDist;

        double difference = distanceSquared - radiusSquared;
        if (difference > Util.DELTA)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("(%s, %.3f)", center, Math.sqrt(radiusSquared));
    }

    @Override
    public int compareTo(Circle that) {
        if (this == that)
            return 0;

        double difference = this.radiusSquared() - that.radiusSquared();
        if (difference < -Util.DELTA)
            return -1;
        else if (difference > Util.DELTA)
            return 1;
        return 0;
    }

    public void draw() {
        StdDraw.circle(center.xCoord(), center.yCoord(), Math.sqrt(radiusSquared));
    }

    public static void main(String[] args) {
        StdDraw.setXscale(-1.0, 1.0);
        StdDraw.setYscale(-1.0, 1.0);

        // Draw a test circle
        Circle circle = new Circle(new Point(0.0, 0.0), 0.25);
        System.out.printf("Circle %s area: %.3f\n", circle, circle.radiusSquared()*Math.PI);
        StdDraw.setPenColor(Color.GREEN);
        circle.draw();

        // Points enclosed
        StdDraw.setPenColor(Color.RED);
        Point p1 = new Point(0.0, 0.0);
        System.out.println(p1 + " encloses: " + circle.encloses(p1));
        p1.draw();
        Point p2 = new Point(0.4, 0.3);
        System.out.println(p2 + " encloses: " + circle.encloses(p2));
        p2.draw();
        Point p3 = new Point(0.5, 0.3);
        System.out.println(p3 + " encloses: " + circle.encloses(p3));
        p3.draw();
        Point p4 = new Point(-0.3, 0.4);
        System.out.println(p4 + " encloses: " + circle.encloses(p4));
        p4.draw();
    }
}
