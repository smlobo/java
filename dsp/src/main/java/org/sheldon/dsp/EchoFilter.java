package org.sheldon.dsp;

import javazoom.jl.player.StdPlayer;

import java.util.ArrayDeque;
import java.util.Queue;

public class EchoFilter {
    public static void main(String[] args) {
        String filename = args[0];
        StdPlayer.open(filename);

        // Echo by playing the wave, 10 waves ago
        Queue<Wave> queue = new ArrayDeque<>(10);
        while (!StdPlayer.isEmpty()) {
            Wave w = new Wave(StdPlayer.getLeftChannel(), StdPlayer.getRightChannel());

            // Queue filled up - create the echo
            Wave echo;
            if (queue.size() == 10) {
                Wave old = queue.remove();
                echo = w.plus(old);
            }
            else {
                echo = w;
            }

            // Add the current Wave to the queue
            queue.add(w);

            echo.play();
        }

        StdPlayer.close();
        System.exit(0);
    }
}
