package org.sheldon.dsp;

import edu.princeton.cs.algs4.StdDraw;
import javazoom.jl.player.StdPlayer;

public class Wave {
    private static final int SAMPLING_RATE = 44100;
    private double[] left;
    private double[] right;

    public Wave(double hz, double seconds, double amplitude) {
        int size = (int) (seconds * SAMPLING_RATE);

        left = new double[size];
        right = new double[size];

        for (int i = 0; i < size; i++) {
            left[i] = amplitude * Math.sin(2 * Math.PI * hz * (double) i / SAMPLING_RATE);
            right[i] = left[i];
        }
    }

    public Wave(double[] left, double[] right) {
        this.left = left;
        this.right = right;
    }

    public void play() {
        StdPlayer.playWave(left, right);
    }

    public Wave plus(Wave x) {
        assert(left.length == x.left.length);
        assert(left.length == right.length);
        assert(x.left.length == x.right.length);

        double[] left = new double[this.left.length];
        double[] right = new double[this.right.length];

        for (int i = 0; i < left.length; i++) {
            left[i] = (this.left[i] + x.left[i])/2;
            right[i] = (this.right[i] + x.right[i])/2;
        }

        return new Wave(left, right);
    }

    public Wave plus(Wave x, Wave y) {
        assert(left.length == x.left.length);
        assert(left.length == y.left.length);
        assert(left.length == right.length);
        assert(x.left.length == x.right.length);
        assert(y.left.length == y.right.length);

        double[] left = new double[this.left.length];
        double[] right = new double[this.right.length];

        for (int i = 0; i < left.length; i++) {
            left[i] = (this.left[i] + x.left[i] + y.left[i])/3;
            right[i] = (this.right[i] + x.right[i] + y.right[i])/3;
        }

        return new Wave(left, right);
    }

    public void draw() {
        // Draw both channels
        for (int i = 0; i < left.length; i++) {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.filledCircle(i+10, left[i]*250 + 250, 1);
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.filledCircle(i+10, right[i]*250 - 250, 1);
        }
    }
}
