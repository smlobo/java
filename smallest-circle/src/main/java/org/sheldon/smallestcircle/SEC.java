/*************************************************************************
 *  Compilation:  javac SEC.java
 *  Execution:    java SEC
 *
 *  Computes the smallest enclosing circle of the points that the
 *  user clicks in the standard drawing window.
 *  
 *************************************************************************/

package org.sheldon.smallestcircle;

import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class SEC { 

    public static void main(String[] args) {

        // scale coordinates and turn on animation mode
        StdDraw.setXscale(-1, +1);
        StdDraw.setYscale(-1, +1); 
        StdDraw.show(0);

        // read in points that the user clicks and draw the smallest enclosing circle
        ArrayList<Point> points = new ArrayList<Point>();
        while (true) {

            // wait for next point to be clicked and add that point
            while (!StdDraw.mousePressed()) {
                StdDraw.show(10);
            }
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            Point point = new Point(x, y);
            points.add(point);

            // Wait for at least 2 points
            if (points.size() < 2)
                continue;
            
            // convert ArrayList to array
            Point[] a = new Point[points.size()];
            for (int i = 0; i < points.size(); i++)
                a[i] =  points.get(i);

            // compute smallest enclosing circle
            FastSmallest fast = new FastSmallest(a);
            Circle circle = fast.smallestEnclosingCircle();

            // draw points and smallest enclosing circle
            StdDraw.clear(StdDraw.WHITE);
            StdDraw.setPenColor(StdDraw.BLACK);
            for (Point p : points)
                p.draw();
            StdDraw.setPenColor(StdDraw.RED);
            circle.draw();
            StdDraw.show(0);
        }
    }
}
