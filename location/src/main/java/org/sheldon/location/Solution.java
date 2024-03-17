package org.sheldon.location;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Solution {
    public static final Color colorRotator[] = new Color[]
            {Color.BLACK, Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.MAGENTA};

    private static void draw(List<LineSegment> lines, List<PointPair> pairs) {
        for (int i = 0; i < lines.size(); i++) {
            StdDraw.setPenColor(colorRotator[i%colorRotator.length]);
            lines.get(i).draw();
        }
        for (int i = 0; i < pairs.size(); i++) {
            StdDraw.setPenColor(colorRotator[i%colorRotator.length]);
            pairs.get(i).draw();
        }
    }

    public static void main(String[] args) throws IOException {
        // input file is the argument
        if (args.length != 1) {
            System.out.println("Usage: java -jar ... <inputfile.txt>");
            System.exit(-1);
        }

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
            Point p1 = new Point(Float.parseFloat(linePoints[0]), Float.parseFloat(linePoints[1]));
            Point p2 = new Point(Float.parseFloat(linePoints[2]), Float.parseFloat(linePoints[3]));
            LineSegment line = new LineSegment(p1, p2);
            //System.out.println("  [" + i + "] " + line);
            lines.add(line);
        }

        // Create the point pairs
        ArrayList<PointPair> pairs = new ArrayList<PointPair>();
        String pairStr;
        while ((pairStr = bufferedReader.readLine()) != null) {
            String[] pairPoints = pairStr.split(" +");

            // Skip empty lines
            if (pairPoints.length != 4)
                continue;
            Point p1 = new Point(Float.parseFloat(pairPoints[0]), Float.parseFloat(pairPoints[1]));
            Point p2 = new Point(Float.parseFloat(pairPoints[2]), Float.parseFloat(pairPoints[3]));
            PointPair pair = new PointPair(p1, p2);
            //System.out.println("  -> " + pair);
            pairs.add(pair);
        }

        // Close the file stream
        bufferedReader.close();

        // Draw the input
        draw(lines, pairs);

        // Brute force
        long bruteTimer = System.currentTimeMillis();
        BrutePointLocator brutePointLocator = new BrutePointLocator(lines);
        int bruteCount = 0;
        //for (PointPair pair : pairs) {
        for (int i = 0; i < pairs.size(); i++) {
            PointPair pair = pairs.get(i);
            LineSegment separator = brutePointLocator.separatedBy(pair);
            if (separator == null) {
                //System.out.println("[" + i + "] NO:  " + pair + " are not separable");
            }
            else {
                //System.out.println("[" + i + "] YES: " + pair + " are separable by " + separator);
                bruteCount++;
            }
        }
        bruteTimer = System.currentTimeMillis() - bruteTimer;
        System.out.println("Brute: " + bruteCount + " separable of " + pairs.size() + "; took: " +
                bruteTimer);

        // Binary tree force
        long binaryTreeTimer = System.currentTimeMillis();
        BinaryTreePointLocator binaryTreePointLocator = new BinaryTreePointLocator(lines);
        int binaryTreeCount = 0;
        //for (PointPair pair : pairs) {
        for (int i = 0; i < pairs.size(); i++) {
            PointPair pair = pairs.get(i);
            LineSegment separator = binaryTreePointLocator.separatedBy(pair);
            if (separator == null) {
                //System.out.println("[" + i + "] NO:  " + pair + " are not separable");
            }
            else {
                //System.out.println("[" + i + "] YES: " + pair + " are separable by " + separator);
                binaryTreeCount++;
            }
        }
        binaryTreeTimer = System.currentTimeMillis() - binaryTreeTimer;
        System.out.println("Binary tree: " + binaryTreeCount + " separable of " + pairs.size() +
                "; took: " + binaryTreeTimer);
    }
}
