package org.sheldon.sierpinski;

import edu.princeton.cs.algs4.StdDraw;

public class Solution {

    static class Point {
        double x;
        double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    private static double height(double side) {
        return Math.sqrt(3)/2*side;
    }
    private static void triangle(Point mid, double side) {
        double height = height(side);
        Point topLeft = new Point(mid.x-side/2, mid.y);
        Point topRight = new Point(mid.x+side/2, mid.y);
        Point bottom = new Point(mid.x, mid.y-height);

        // Draw
        double x[] = new double[] {topLeft.x, topRight.x, bottom.x};
        double y[] = new double[] {topLeft.y, topRight.y, bottom.y};
        StdDraw.filledPolygon(x, y);
    }

    private static void sierpinski(int n, Point mid, double side) {
        if (n == 0)
            return;

        triangle(mid, side);

        double height = height(side);

        // Triangle above
        sierpinski(n-1, new Point(mid.x, mid.y+height/2), side/2);
        // Triangle side left
        sierpinski(n-1, new Point(mid.x-side/2, mid.y-height/2), side/2);
        // Triangle side right
        sierpinski(n-1, new Point(mid.x+side/2, mid.y-height/2), side/2);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        System.out.println("Sierpinski triangle depth: " + n);

        //triangle(new Point(0.5, 0.5), 0.4);
        sierpinski(n, new Point(0.5, 0.5), 0.4);
    }
}
