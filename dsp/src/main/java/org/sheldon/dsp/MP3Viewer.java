package org.sheldon.dsp;

import edu.princeton.cs.algs4.StdDraw;
import javazoom.jl.player.StdPlayer;

import java.awt.*;

public class MP3Viewer {
    public static void main(String[] args) {
        String filename = args[0];
        StdPlayer.open(filename);

        StdDraw.setCanvasSize(1200, 500);
        StdDraw.setXscale(0, 1200);
        StdDraw.setYscale(-500, 500);
        StdDraw.enableDoubleBuffering();

        while (!StdPlayer.isEmpty()) {
            StdDraw.clear();
            drawAxis();
            Wave w = new Wave(StdPlayer.getLeftChannel(), StdPlayer.getRightChannel());
            w.play();
            w.draw();
            StdDraw.show();
            //StdDraw.pause(10);
        }

        StdPlayer.close();
        System.exit(0);
    }

    private static void drawAxis() {
        StdDraw.setPenColor(Color.BLACK);

        // y axis
        StdDraw.line(10, 500, 10, -500);
        
        // Left channel x axis
        StdDraw.line(10, 250, 1200, 250);

        // Right channel x axis
        StdDraw.line(10, -250, 1200, -250);
    }
}
