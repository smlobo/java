package org.sheldon.location;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LineSegment {
    private Point from;
    private Point to;

    public LineSegment(Point from, Point to) {
        if (from.compareTo(to) < 0) {
            this.from = from;
            this.to = to;
        }
        else {
            this.from = to;
            this.to = from;
        }
    }

    public Point from() {
        return from;
    }

    public Point to() {
        return to;
    }

    public float slope() {
        return from.slopeTo(to);
    }

    public boolean clockwisePoint(Point x) {
        // Does not work for all cases
//        if (from.slopeTo(x) < slope())
//            return true;
//        else
//            return false;
        return Util.ccw(from, to, x) < 0;
    }

    public Point intersects(LineSegment that) {
        // Saves time for 1000 lines in binary tree
        // Both 'that' points on the same side of 'this'
        if (Util.ccw(this.from, this.to, that.from) * Util.ccw(this.from, this.to, that.to) > 0)
            return null;
        // Both 'this' points on the same side of 'that'
        if (Util.ccw(that.from, that.to, this.from) * Util.ccw(that.from, that.to, this.to) > 0)
            return null;

        float thisSlope = slope();
        float thatSlope = that.slope();

        // Degenerate cases
        if (thisSlope == Float.POSITIVE_INFINITY && thatSlope == Float.POSITIVE_INFINITY) {
            return null;
        }
        else if (thisSlope == Float.POSITIVE_INFINITY) {
            float x = this.from.xCoord();
            float y = that.from.yCoord() + thatSlope*(x - that.from.xCoord());
            return new Point(x, y);
        }
        else if (thatSlope == Float.POSITIVE_INFINITY) {
            float x = that.from.xCoord();
            float y = this.from.yCoord() + thisSlope*(x - this.from.xCoord());
            return new Point(x, y);
        }

        float y = (thisSlope*that.from.yCoord() - thatSlope*this.from.yCoord() -
                thisSlope*thatSlope*(that.from.xCoord()-this.from.xCoord())) / (thisSlope - thatSlope);

        // intersection y coord lies between both lines
        if (y < this.from.yCoord() || y > this.to.yCoord() ||
                y < that.from.yCoord() || y > that.to.yCoord())
            return null;

        float x = (y - that.from.yCoord()) / thatSlope + that.from.xCoord();

        return new Point(x, y);
    }

    @Override
    public String toString() {
        return from + " to " + to + " ; s " + String.format("%.3f", slope());
    }

    public void draw() {
        StdDraw.line(from.xCoord(), from.yCoord(), to.xCoord(), to.yCoord());
    }

    public static void main(String[] args) throws IOException {
        // Point bottom half
        Point p1 = new Point(0.5f, 0.25f);
        // Point top half
        Point p2 = new Point(0.5f, 0.75f);

        // line slope 1
        LineSegment l1 = new LineSegment(new Point(0.0f, 0.0f),
                new Point(1.0f, 1.0f));
        System.out.println("l1 = " + l1);
        System.out.println("  slope to: " + p1 + " = " + l1.from().slopeTo(p1) + "; clockwise? " +
                l1.clockwisePoint(p1));
        System.out.println("  slope to: " + p2 + " = " + l1.from().slopeTo(p2) + "; clockwise? " +
                l1.clockwisePoint(p2));

        // line slope -1
        LineSegment l2 = new LineSegment(new Point(1.0f, 0.0f),
                new Point(0.0f, 1.0f));
        System.out.println("l2 = " + l2);
        System.out.println("  slope to: " + p1 + " = " + l2.from().slopeTo(p1) + "; clockwise? " +
                l2.clockwisePoint(p1));
        System.out.println("  slope to: " + p2 + " = " + l2.from().slopeTo(p2) + "; clockwise? " +
                l2.clockwisePoint(p2));

        // Visualize
        StdDraw.setPenColor(Color.BLACK);
        l1.draw();
        l2.draw();
        StdDraw.setPenColor(Color.RED);
        p1.draw();
        p2.draw();

        // Intersection
        Point pi = l1.intersects(l2);
        System.out.println("l1 & l2 intersect at: " + pi);
        StdDraw.setPenColor(Color.GREEN);
        pi.draw();

        // Now from file
        // Read the file
        BufferedReader bufferedReader = new BufferedReader(new FileReader(args[0]));

        // Read the number of line segments
        int n = Integer.parseInt(bufferedReader.readLine());
        System.out.println("Reading in " + n + " lines");

        // Create the lines
        ArrayList<LineSegment> lines = new ArrayList<LineSegment>(n);
        for (int i = 0; i < n; i++) {
            String lineStr = bufferedReader.readLine().trim();
            String[] linePoints = lineStr.split(" +");
            assert linePoints.length == 4;
            Point pA = new Point(Float.parseFloat(linePoints[0]), Float.parseFloat(linePoints[1]));
            Point pB = new Point(Float.parseFloat(linePoints[2]), Float.parseFloat(linePoints[3]));
            LineSegment line = new LineSegment(pA, pB);
            System.out.println("  [" + i + "] " + line);
            lines.add(line);
        }

        // Close the file
        bufferedReader.close();

        // Draw the lines
        for (int i = 0; i < lines.size(); i++) {
            StdDraw.setPenColor(Solution.colorRotator[i%Solution.colorRotator.length]);
            lines.get(i).draw();
        }

        // Find and draw all intersection points
        for (int i = 0; i < lines.size(); i++) {
            for (int j = i+1; j < lines.size(); j++) {
                Point intersection = lines.get(i).intersects(lines.get(j));
                if (intersection == null)
                    continue;
                StdDraw.setPenColor(Solution.colorRotator[i%Solution.colorRotator.length]);
                intersection.draw();
            }
        }

    }
}
