package org.sheldon.location;

import java.util.List;

public class BinaryTreePointLocator implements PointLocator {
    class TreeNode {
        LineSegment subLine;
        LineSegment original;
        TreeNode clockwise;
        TreeNode antiClockwise;

        public TreeNode(LineSegment subLine, LineSegment original) {
            this.subLine = subLine;
            this.original = original;
        }
    }
    private TreeNode root;

    // Recursive insert a line into the binary tree
    private TreeNode insert(LineSegment subLine, LineSegment original, TreeNode node) {
        // null node
        if (node == null) {
            return new TreeNode(subLine, original);
        }

        // Does the given (sub)line intersect with the node
        Point intersection = node.subLine.intersects(subLine);

        // Does not intersect - add the whole line to the clock/anticlock side
        if (intersection == null) {
            if (node.original.clockwisePoint(subLine.from())) {
                node.clockwise = insert(subLine, original, node.clockwise);
            }
            else {
                node.antiClockwise = insert(subLine, original, node.antiClockwise);
            }

            // Return the existing node - do not modify the tree
            return node;
        }

        // Intersects - create 2 subLines
        LineSegment fromSubLine = new LineSegment(subLine.from(), intersection);
        LineSegment toSubLine = new LineSegment(intersection, subLine.to());

        // Add the appropriate subLine to the clock AND anticlock portions of the tree
        if (node.original.clockwisePoint(fromSubLine.from())) {
            node.clockwise = insert(fromSubLine, original, node.clockwise);
            node.antiClockwise = insert(toSubLine, original, node.antiClockwise);
        }
        else {
            node.antiClockwise = insert(fromSubLine, original, node.antiClockwise);
            node.clockwise = insert(toSubLine, original, node.clockwise);
        }

        // Return the existing node - do not modify the tree
        return node;
    }

    // Given a valid node, check if the points are on opposite sides.
    // If yes, return the original line
    // If no, search the clock/anticlock region of this node
    private LineSegment lookup(PointPair pair, TreeNode node) {
        // Found the line separating the point pair
        boolean p1Clockwise = node.original.clockwisePoint(pair.p1());
        boolean p2Clockwise = node.original.clockwisePoint(pair.p2());
        if ((p1Clockwise && !p2Clockwise) ||
                (!p1Clockwise && p2Clockwise)) {
            return node.original;
        }

        // Continue the search
        if (p1Clockwise && node.clockwise != null) {
            return lookup(pair, node.clockwise);
        }
        else if (!p1Clockwise && node.antiClockwise != null) {
            return lookup(pair, node.antiClockwise);
        }

        // No line segment separates the points
        return null;
    }

    public BinaryTreePointLocator(List<LineSegment> lines) {
        // Insert lines into the binary tree
        for (LineSegment line : lines) {
            root = insert(line, line, root);
        }
    }

    @Override
    public LineSegment separatedBy(PointPair pointPair) {
        // Empty tree
        if (root == null)
            return null;

        return lookup(pointPair, root);
    }
}
