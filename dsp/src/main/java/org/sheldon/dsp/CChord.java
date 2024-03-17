package org.sheldon.dsp;

import javazoom.jl.player.StdPlayer;

public class CChord {
    public static void main(String[] args) throws InterruptedException {
        StdPlayer.open();

        // Play C
        Wave c = new Wave(523.25, 1.0, .8);
        c.play();
        Thread.sleep(1000);

        // Play E
        Wave e = new Wave(659.26, 1.0, .8);
        e.play();
        Thread.sleep(1000);

        // Play G
        Wave g = new Wave(783.99, 1.0, .8);
        g.play();
        Thread.sleep(1000);

        // Play the C chord
        Wave cChord1 = c.plus(e).plus(g);
        cChord1.play();
        Thread.sleep(1000);

        // Play the fixed C chord
        Wave cChord2 = c.plus(e, g);
        cChord2.play();
        Thread.sleep(1000);

        StdPlayer.close();
        System.exit(0);
    }
}
