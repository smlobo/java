package org.sheldon.smallestcircle;

public abstract class Smallest {
    protected Point[] points;
    protected Circle smallest;
    protected Point pairA;
    protected Point pairB;
    protected Point tripleA;
    protected Point tripleB;
    protected Point tripleC;

    public Smallest(Point[] points) {
        this.points = points;

        // Initialize to the circle that can fit any point in this (-1, -1) -> (1, 1) square.
        this.smallest = new Circle(new Point(0.0, 0.0), 2.0);
    }

    protected Smallest() {
    }

    // Does the given circle enclose all points
    protected boolean enclosesAll(Circle x) {
        for (int i = 0; i < points.length; i++) {
            if (!x.encloses(points[i]))
                return false;
        }
        return true;
    }

    // Return a circle given 2 points on the diameter
    protected Circle getPairCircle(Point a, Point b) {
        Point center = new Point((a.xCoord()+b.xCoord())/2, (a.yCoord()+b.yCoord())/2);
        double xDistance = a.xCoord() - b.xCoord();
        double yDistance = a.yCoord() - b.yCoord();
        double radiusSquared = (xDistance*xDistance + yDistance*yDistance) / 4;
        return new Circle(center, radiusSquared);
    }

    // Return a circle given 2 points on the diameter
    protected Circle getTripleCircle(Point x, Point y, Point z) {
        double xHypo = x.xCoord()*x.xCoord() + x.yCoord()*x.yCoord();
        double yHypo = y.xCoord()*y.xCoord() + y.yCoord()*y.yCoord();
        double zHypo = z.xCoord()*z.xCoord() + z.yCoord()*z.yCoord();

        double[][] aMatrix = {
                {x.xCoord(), x.yCoord(), 1.0},
                {y.xCoord(), y.yCoord(), 1.0},
                {z.xCoord(), z.yCoord(), 1.0}
        };
        double a = Util.matrix3DDeterminant(aMatrix);

        double[][] dMatrix = {
                {xHypo, x.yCoord(), 1.0},
                {yHypo, y.yCoord(), 1.0},
                {zHypo, z.yCoord(), 1.0}
        };
        double d = Util.matrix3DDeterminant(dMatrix);

        double[][] eMatrix = {
                {xHypo, x.xCoord(), 1.0},
                {yHypo, y.xCoord(), 1.0},
                {zHypo, z.xCoord(), 1.0}
        };
        double e = Util.matrix3DDeterminant(eMatrix);

        double[][] fMatrix = {
                {xHypo, x.xCoord(), x.yCoord()},
                {yHypo, y.xCoord(), y.yCoord()},
                {zHypo, z.xCoord(), z.yCoord()}
        };
        double f = Util.matrix3DDeterminant(fMatrix);

        Point center = new Point(d/(2.0*a), -e/(2.0*a));
        double radiusSquared = (d*d + e*e)/(4.0*a*a) + f/a;

        return new Circle(center, radiusSquared);
    }
}
