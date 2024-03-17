package org.sheldon.lineintersection;

import edu.princeton.cs.algs4.Point2D;

import java.util.ArrayList;

public class BruteIntersections extends IntersectionsImpl {

    @Override
    public Iterable<Point2D> intersections() {
        ArrayList<Point2D> intersectPoints = new ArrayList<>(hLines.size() + vLines.size());

        // Compare each horizontal line with each vertical line
        for (LineSegment hLine : hLines) {
            for (LineSegment vLine : vLines) {
                // Vertical line x lies between horizontal line
                // Horizontal line y lies between vertical line
                if (vLine.from().x() > hLine.from().x() &&
                    vLine.from().x() < hLine.to().x() &&
                    hLine.from().y() > vLine.from().y() &&
                    hLine.from().y() < vLine.to().y()) {
                    intersectPoints.add(new Point2D(vLine.from().x(), hLine.from().y()));
                }
            }
        }

        return intersectPoints;
    }
}
