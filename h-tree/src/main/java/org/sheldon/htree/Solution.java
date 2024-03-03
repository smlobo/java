package org.sheldon.htree;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.Random;

public class Solution {
    private static final int FULL_SIZE = 512;
    private static final int RGB_MAX = 256;
    private static Random random;

    static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static void htree(int n, int size, Point center) {
        // Stop drawing
        if (n == 0)
            return;

        // Set a random color
        StdDraw.setPenColor(new Color(random.nextInt(RGB_MAX), random.nextInt(RGB_MAX), random.nextInt(RGB_MAX)));

        // Draw H to size, and centered
        // Horizontal
        Point hLineFrom = new Point(center.x-size/2, center.y);
        Point hLineTo = new Point(center.x+size/2, center.y);
        StdDraw.line(hLineFrom.x, hLineFrom.y, hLineTo.x, hLineTo.y);
        // Vertical left
        Point vLineLFrom = new Point(hLineFrom.x, center.y-size/2);
        Point vLineLTo = new Point(hLineFrom.x, center.y+size/2);
        StdDraw.line(vLineLFrom.x, vLineLFrom.y, vLineLTo.x, vLineLTo.y);
        // Vertical right
        Point vLineRFrom = new Point(hLineTo.x, center.y-size/2);
        Point vLineRTo = new Point(hLineTo.x, center.y+size/2);
        StdDraw.line(vLineRFrom.x, vLineRFrom.y, vLineRTo.x, vLineRTo.y);

        // Draw 4 H's at the 4 corners
        htree(n-1, size/2, vLineLFrom);
        htree(n-1, size/2, vLineLTo);
        htree(n-1, size/2, vLineRFrom);
        htree(n-1, size/2, vLineRTo);
    }

    public static void main(String args[]) {
        int n = Integer.parseInt(args[0]);
        System.out.println("H-tree depth: " + n);

        StdDraw.setXscale(0, 1024);
        StdDraw.setYscale(0, 1024);

        random = new Random(System.currentTimeMillis());

        htree(n, FULL_SIZE, new Point(1024/2, 1024/2));
    }
}