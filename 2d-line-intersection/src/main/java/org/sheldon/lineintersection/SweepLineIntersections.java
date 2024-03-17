package org.sheldon.lineintersection;

import edu.princeton.cs.algs4.BST;
import edu.princeton.cs.algs4.Point2D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.SortedSet;
import java.util.TreeSet;

public class SweepLineIntersections extends IntersectionsImpl {
    private enum EventType {
        HORIZONTAL_BEGIN,
        HORIZONTAL_END,
        VERTICAL
    }

    private class Event implements Comparable<Event> {
        EventType eventType;
        LineSegment lineSegment;

        public Event(EventType eventType, LineSegment lineSegment) {
            this.eventType = eventType;
            this.lineSegment = lineSegment;
        }

        private double typeBasedXCoord() {
            double xCoord;
            switch (this.eventType) {
                case HORIZONTAL_BEGIN:
                    xCoord = lineSegment.from().x();
                    break;
                case HORIZONTAL_END:
                case VERTICAL:
                    xCoord = lineSegment.to().x();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + this.eventType);
            }
            return xCoord;
        }

        @Override
        public int compareTo(Event that) {
            double xCoordThis = this.typeBasedXCoord();
            double xCoordThat = that.typeBasedXCoord();
            return xCoordThis < xCoordThat ? -1 : 1;
        }
    }

    @Override
    public Iterable<Point2D> intersections() {
        // Iterate over all lines, creating Events, add them to an array
        // Do not use a PQ - since:
        // * array size is known
        // * array sort and iterate is faster than PQ add & poll (remove)
        Event[] events = new Event[hLines.size()*2 + vLines.size()];
        int index = 0;

        // Horizontal lines
        for (LineSegment hLine : hLines) {
            events[index++] = new Event(EventType.HORIZONTAL_BEGIN, hLine);
            events[index++] = new Event(EventType.HORIZONTAL_END, hLine);
        }

        // Vertical lines
        for (LineSegment vLine : vLines) {
            events[index++] = new Event(EventType.VERTICAL, vLine);
        }

        Arrays.sort(events);

        // ArrayList much faster than LinkedList
        //LinkedList<Point2D> intersectPoints = new LinkedList<>();
        ArrayList<Point2D> intersectPoints = new ArrayList<>(events.length);

        // In order (left to right) traversal of Events
        TreeSet<Double> searchTree = new TreeSet<>();
        for (Event event : events) {
            // Horizontal line begin
            if (event.eventType == EventType.HORIZONTAL_BEGIN) {
                searchTree.add(event.lineSegment.from().y());
            }
            // Horizontal line end
            else if (event.eventType == EventType.HORIZONTAL_END) {
                searchTree.remove(event.lineSegment.to().y());
            }
            // Vertical line
            else if (event.eventType == EventType.VERTICAL) {
                SortedSet<Double> range = searchTree.subSet(
                        event.lineSegment.from().y(), true,
                        event.lineSegment.to().y(), true);
                // Streams/parallel streams both have similar performance to an Iterator
                // The bottleneck is the point creation
                // Foreach
//                for (Double yCoord : range) {
//                    intersectPoints.add(new Point2D(event.lineSegment.from().x(), yCoord));
//                }
                // Streams
                range.stream().
                        forEach(yCoord -> intersectPoints.add(
                                new Point2D(event.lineSegment.from().x(), yCoord)));
            }
        }

        return intersectPoints;
    }
}
