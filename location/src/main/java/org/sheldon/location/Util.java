package org.sheldon.location;

public class Util {
    public static final float DELTA = 0.000000001f;

    public static float ccw(Point a, Point b, Point c) {
        // Is line 'ab' counter clockwise to line 'ac'
        // == 0 -> collinear
        // > 0  -> anti-clockwise
        // < 0  -> clockwise
        // https://en.wikipedia.org/wiki/Cross_product#Applications
        return (b.xCoord() - a.xCoord()) * (c.yCoord() - a.yCoord()) -
                (c.xCoord() - a.xCoord()) * (b.yCoord() - a.yCoord());
    }
}
