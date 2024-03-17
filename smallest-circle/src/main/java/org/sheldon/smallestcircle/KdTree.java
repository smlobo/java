package org.sheldon.smallestcircle;

import edu.princeton.cs.algs4.StdDraw;

import java.util.Iterator;
import java.util.LinkedList;

public class KdTree implements Iterable<Point> {

    private Node root;
    private int size;

    private static class Node {
        private Point point;
        private Node left;
        private Node right;

        public Node(Point p) {
            point = p;
        }
    }

    // construct an empty set of points
    public KdTree() {
        size = 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point p) {
        if (p == null)
            throw new NullPointerException("insert() Point is null");

        root = insert(root, p, true);
    }

    private Node insert(Node n, Point p, boolean xcoord) {
        if (n == null) {
            size++;
            return new Node(p);
        }

        if (n.point.equals(p))
            return n;

        if ((xcoord && p.xCoord() < n.point.xCoord()) ||
                (!xcoord && p.yCoord() < n.point.yCoord()))
            n.left = insert(n.left, p, !xcoord);
        else
            n.right = insert(n.right, p, !xcoord);

        return n;
    }

    // does the set contain point p?
    public boolean contains(Point p) {
        if (p == null)
            throw new NullPointerException("contains() Point is null");

        Node n = root;
        boolean xcoord = true;

        while (n != null) {

            if (n.point.equals(p))
                return true;

            if ((xcoord && p.xCoord() < n.point.xCoord()) ||
                    (!xcoord && p.yCoord() < n.point.yCoord()))
                n = n.left;
            else
                n = n.right;

            xcoord = !xcoord;
        }

        return false;
    }

    // draw all points to standard draw
    public void draw() {
        draw(root, -1.0, 1.0, -1.0, 1.0, true);
    }

    private void draw(Node n, double xmin, double xmax, double ymin, double ymax, boolean xcoord) {
        if (n == null)
            return;

        // Draw the point
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        n.point.draw();

        if (xcoord) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(n.point.xCoord(), ymin, n.point.xCoord(), n.point.yCoord());
            StdDraw.line(n.point.xCoord(), ymax, n.point.xCoord(), n.point.yCoord());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(xmin, n.point.yCoord(), n.point.xCoord(), n.point.yCoord());
            StdDraw.line(xmax, n.point.yCoord(), n.point.xCoord(), n.point.yCoord());
        }

        // Draw left tree
        if (xcoord)
            draw(n.left, xmin, n.point.xCoord(), ymin, ymax, !xcoord);
        else
            draw(n.left, xmin, xmax, ymin, n.point.yCoord(), !xcoord);

        // Draw the right tree
        if (xcoord)
            draw(n.right, n.point.xCoord(), xmax, ymin, ymax, !xcoord);
        else
            draw(n.right, xmin, xmax, n.point.yCoord(), ymax, !xcoord);
    }

    // all points that are inside the rectangle
    public Iterable<Point> range(Rect rect) {
        if (rect == null)
            throw new NullPointerException("range() RectHV is null");

        LinkedList<Point> rp = new LinkedList<>();

        range(root, rp, rect, new Rect(-1.0, -1.0, 1.0, 1.0), true);

        return rp;
    }

    private void range(Node n, LinkedList<Point> rp, Rect q, Rect nr, boolean xcoord) {
        if (n == null)
            return;

        // Does not intersect
        if (!q.intersects(nr))
            return;

        // This Node (Point) is in this rectangle
        if (q.contains(n.point))
            rp.add(n.point);

        Rect lRect;
        if (xcoord)
            lRect = new Rect(nr.xmin(), nr.ymin(), n.point.xCoord(), nr.ymax());
        else
            lRect = new Rect(nr.xmin(), nr.ymin(), nr.xmax(), n.point.yCoord());
        range(n.left, rp, q, lRect, !xcoord);


        Rect rRect;
        if (xcoord)
            rRect = new Rect(n.point.xCoord(), nr.ymin(), nr.xmax(), nr.ymax());
        else
            rRect = new Rect(nr.xmin(), n.point.yCoord(), nr.xmax(), nr.ymax());
        range(n.right, rp, q, rRect, !xcoord);
    }

    private static class DistancePoint {
        private Point p;
        private double d;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point nearest(Point p) {
        if (p == null)
            throw new NullPointerException("nearest() Point2D is null");

        DistancePoint np = new DistancePoint();

        nearest(root, p, np, new Rect(-1.0, -1.0, 1.0, 1.0), true);

        return np.p;
    }

    private void nearest(Node n, Point p, DistancePoint np, Rect r, boolean xcoord) {
        if (n == null)
            return;

        // First Point2D recorded
        if (np.p == null) {
            np.d = n.point.distanceSquaredTo(p);
            np.p = n.point;
        }

        // Rectangle further than shortest distance, ignore Node and subtree
        if (np.d < r.shortestDistanceSquaredTo(p))
            return;

        // Check current Node distance
        double cd = n.point.distanceSquaredTo(p);
        if (cd < np.d) {
            np.d = cd;
            np.p = n.point;
        }

        Rect lRect;
        if (xcoord)
            lRect = new Rect(r.xmin(), r.ymin(), n.point.xCoord(), r.ymax());
        else
            lRect = new Rect(r.xmin(), r.ymin(), r.xmax(), n.point.yCoord());

        Rect rRect;
        if (xcoord)
            rRect = new Rect(n.point.xCoord(), r.ymin(), r.xmax(), r.ymax());
        else
            rRect = new Rect(r.xmin(), n.point.yCoord(), r.xmax(), r.ymax());

        if (lRect.shortestDistanceSquaredTo(p) < rRect.shortestDistanceSquaredTo(p)) {
            nearest(n.left, p, np, lRect, !xcoord);
            nearest(n.right, p, np, rRect, !xcoord);
        }
        else {
            nearest(n.right, p, np, rRect, !xcoord);
            nearest(n.left, p, np, lRect, !xcoord);
        }
    }


    // a farthest point in the set to point p; null if the set is empty
    public Point farthest(Point p) {
        if (p == null)
            throw new NullPointerException("farthest() Point2D is null");

        DistancePoint fp = new DistancePoint();

        farthest(root, p, fp, new Rect(-1.0, -1.0, 1.0, 1.0), true);

        return fp.p;
    }

    private void farthest(Node n, Point p, DistancePoint fp, Rect r, boolean xcoord) {
        if (n == null)
            return;

        // First Point2D recorded
        if (fp.p == null) {
            fp.d = n.point.distanceSquaredTo(p);
            fp.p = n.point;
        }

        // Rectangle closer than longest distance, ignore Node and subtree
        if (fp.d > r.longestDistanceSquaredTo(p))
            return;

        // Check current Node distance
        double cd = n.point.distanceSquaredTo(p);
        if (cd > fp.d) {
            fp.d = cd;
            fp.p = n.point;
        }

        Rect lRect;
        if (xcoord)
            lRect = new Rect(r.xmin(), r.ymin(), n.point.xCoord(), r.ymax());
        else
            lRect = new Rect(r.xmin(), r.ymin(), r.xmax(), n.point.yCoord());

        Rect rRect;
        if (xcoord)
            rRect = new Rect(n.point.xCoord(), r.ymin(), r.xmax(), r.ymax());
        else
            rRect = new Rect(r.xmin(), n.point.yCoord(), r.xmax(), r.ymax());

        if (lRect.longestDistanceSquaredTo(p) > rRect.longestDistanceSquaredTo(p)) {
            farthest(n.left, p, fp, lRect, !xcoord);
            farthest(n.right, p, fp, rRect, !xcoord);
        }
        else {
            farthest(n.right, p, fp, rRect, !xcoord);
            farthest(n.left, p, fp, lRect, !xcoord);
        }
    }

    @Override
    public Iterator iterator() {
        Iterable<Point> points = range(new Rect(-1.0, -1.0, 1.0, 1.0));
        return points.iterator();
    }
}
