package org.sheldon.smallestcircle;

import edu.princeton.cs.algs4.StdDraw;

public final class Rect {
    private final double xmin;
    private final double ymin;
    private final double xmax;
    private final double ymax;

    public Rect(double xmin, double ymin, double xmax, double ymax) {
        this.xmin = xmin;
        this.ymin = ymin;
        this.xmax = xmax;
        this.ymax = ymax;
        if (!Double.isNaN(xmin) && !Double.isNaN(xmax)) {
            if (!Double.isNaN(ymin) && !Double.isNaN(ymax)) {
                if (xmax < xmin) {
                    throw new IllegalArgumentException("xmax < xmin: " + this.toString());
                } else if (ymax < ymin) {
                    throw new IllegalArgumentException("ymax < ymin: " + this.toString());
                }
            } else {
                throw new IllegalArgumentException("y-coordinate is NaN: " + this.toString());
            }
        } else {
            throw new IllegalArgumentException("x-coordinate is NaN: " + this.toString());
        }
    }

    public double xmin() {
        return this.xmin;
    }

    public double xmax() {
        return this.xmax;
    }

    public double ymin() {
        return this.ymin;
    }

    public double ymax() {
        return this.ymax;
    }

    public double width() {
        return this.xmax - this.xmin;
    }

    public double height() {
        return this.ymax - this.ymin;
    }

    public boolean intersects(Rect that) {
        return this.xmax >= that.xmin && this.ymax >= that.ymin && that.xmax >= this.xmin && that.ymax >= this.ymin;
    }

    public boolean contains(Point p) {
        return p.xCoord() >= this.xmin && p.xCoord() <= this.xmax &&
                p.yCoord() >= this.ymin && p.yCoord() <= this.ymax;
    }

    public double shortestDistanceSquaredTo(Point p) {
        double dx = 0.0D;
        double dy = 0.0D;
        if (p.xCoord() < this.xmin) {
            dx = p.xCoord() - this.xmin;
        } else if (p.xCoord() > this.xmax) {
            dx = p.xCoord() - this.xmax;
        }

        if (p.yCoord() < this.ymin) {
            dy = p.yCoord() - this.ymin;
        } else if (p.yCoord() > this.ymax) {
            dy = p.yCoord() - this.ymax;
        }

        return dx * dx + dy * dy;
    }

    public double longestDistanceSquaredTo(Point p) {
        double dxMin = Math.abs(p.xCoord() - this.xmin);
        double dxMax = Math.abs(p.xCoord() - this.xmax);
        double dx = Math.max(dxMin, dxMax);

        double dyMin = Math.abs(p.yCoord() - this.ymin);
        double dyMax = Math.abs(p.yCoord() - this.ymax);
        double dy = Math.max(dyMin, dyMax);

        return dx * dx + dy * dy;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other == null) {
            return false;
        } else if (other.getClass() != this.getClass()) {
            return false;
        } else {
            Rect that = (Rect) other;
            if (this.xmin != that.xmin) {
                return false;
            } else if (this.ymin != that.ymin) {
                return false;
            } else if (this.xmax != that.xmax) {
                return false;
            } else {
                return this.ymax == that.ymax;
            }
        }
    }

    public String toString() {
        return "[" + this.xmin + ", " + this.xmax + "] x [" + this.ymin + ", " + this.ymax + "]";
    }

    public void draw() {
        StdDraw.line(this.xmin, this.ymin, this.xmax, this.ymin);
        StdDraw.line(this.xmax, this.ymin, this.xmax, this.ymax);
        StdDraw.line(this.xmax, this.ymax, this.xmin, this.ymax);
        StdDraw.line(this.xmin, this.ymax, this.xmin, this.ymin);
    }
}
