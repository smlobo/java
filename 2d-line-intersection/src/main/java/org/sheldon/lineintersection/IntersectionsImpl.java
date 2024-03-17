package org.sheldon.lineintersection;

import java.util.ArrayList;

public abstract class IntersectionsImpl implements Intersections {
    protected ArrayList<LineSegment> hLines;
    protected ArrayList<LineSegment> vLines;

    public IntersectionsImpl() {
        hLines = new ArrayList<>();
        vLines = new ArrayList<>();
    }

    public void addLine(LineSegment l) {
        if (l.isHorizontal()) {
            hLines.add(l);
        }
        else if (l.isVertical()) {
            vLines.add(l);
        }
        else {
            System.out.println(l + " is not H/V");
        }
    }
}
