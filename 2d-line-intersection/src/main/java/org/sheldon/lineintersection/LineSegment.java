package org.sheldon.lineintersection;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LineSegment {
    private Point2D from;
    private Point2D to;
    static final double DELTA = 0.00001;

    public LineSegment(Point2D from, Point2D to) {
        if (from.compareTo(to) < 0) {
            this.from = from;
            this.to = to;
        }
        else {
            this.from = to;
            this.to = from;
        }
    }

    public Point2D from() {
        return from;
    }

    public Point2D to() {
        return to;
    }

    @Override
    public String toString() {
        return from + " to " + to + "";
    }

    public void draw() {
        StdDraw.line(from.x(), from.y(), to.x(), to.y());
    }

    public boolean isHorizontal() {
        return Math.abs(from.y()-to.y()) < DELTA;
    }

    public boolean isVertical() {
        return Math.abs(from.x()-to.x()) < DELTA;
    }

    public static void main(String[] args) throws IOException {
        // Read the file
        List<String> fileLines = Files.readAllLines(Paths.get(args[0]));

        // Create the lines
        ArrayList<LineSegment> lines = new ArrayList<LineSegment>();
        for (String fileLine : fileLines) {
            String[] linePoints = fileLine.split(" +");
            assert linePoints.length == 4;
            Point2D pA = new Point2D(Float.parseFloat(linePoints[0]), Float.parseFloat(linePoints[1]));
            Point2D pB = new Point2D(Float.parseFloat(linePoints[2]), Float.parseFloat(linePoints[3]));
            LineSegment line = new LineSegment(pA, pB);
            System.out.println(line);
            lines.add(line);
        }

        // Draw the lines
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenRadius(0.005);
        for (int i = 0; i < lines.size(); i++) {
            StdDraw.setPenColor(StdDraw.BLUE);
            lines.get(i).draw();
        }
        StdDraw.show();
    }
}
