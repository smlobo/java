package org.sheldon.location;

import java.util.List;

public class BrutePointLocator implements PointLocator {
    private List<LineSegment> lines;

    public BrutePointLocator(List<LineSegment> lines) {
        this.lines = lines;
    }

    @Override
    public LineSegment separatedBy(PointPair pointPair) {
        // Iterate over all lines, return the first line with the slopeTo each point being
        // clockwise/anticlockwise
        for (LineSegment line : lines) {
            boolean p1Clockwise = line.clockwisePoint(pointPair.p1());
            boolean p2Clockwise = line.clockwisePoint(pointPair.p2());
            if ((p1Clockwise && !p2Clockwise) ||
                    (!p1Clockwise && p2Clockwise))
                return line;
        }

        return null;
    }
}
