package org.sheldon.smallestcircle;

import java.util.Arrays;

public class Util {
    static final double DELTA = 0.0000001;

    static double matrix3DDeterminant(double[][] a) {
        return a[0][0] * (a[2][2]*a[1][1] - a[2][1]*a[1][2]) -
                a[0][1] * (a[2][2]*a[1][0] - a[2][0]*a[1][2]) +
                a[0][2] * (a[2][1]*a[1][0] - a[2][0]*a[1][1]);
    }

    static double ccw(Point a, Point b, Point c) {
        // Is line 'ab' counter clockwise to line 'ac'
        // Or, is a -> b -> c a counter clockwise turn?
        // == 0 -> collinear
        // > 0  -> anti-clockwise
        // < 0  -> clockwise
        // https://en.wikipedia.org/wiki/Cross_product#Applications
        return matrix3DDeterminant(new double[][] {
                {a.xCoord(), a.yCoord(), 1.0},
                {b.xCoord(), b.yCoord(), 1.0},
                {c.xCoord(), c.yCoord(), 1.0}
        });
    }

    public static void main(String[] args) {
        double x[][] = {
                {1.0, 1.0, 1.0},
                {1.0, 1.0, 1.0},
                {1.0, 1.0, 1.0} };
        System.out.println(Arrays.deepToString(x) + " = " + matrix3DDeterminant(x));
        x = new double[][] {
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0},
                {7.0, 8.0, 9.0} };
        System.out.println(Arrays.deepToString(x) + " = " + matrix3DDeterminant(x));
        x = new double[][] {
                {1.0, 0.0, 0.0},
                {0.0, 1.0, 0.0},
                {0.0, 0.0, 1.0} };
        System.out.println(Arrays.deepToString(x) + " = " + matrix3DDeterminant(x));
    }
}
