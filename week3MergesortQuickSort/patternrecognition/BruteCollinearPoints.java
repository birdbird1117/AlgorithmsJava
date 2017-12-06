import edu.princeton.cs.algs4.*;
import java.lang.*;
import java.util.*;

public class BruteCollinearPoints {
    private final Point[] testPoints;
    private int cnt;
    private final ArrayList<LineSegment> segs;
    //private int segs_index;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("argument is null");
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("argument is null");
        }
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("repeated points");
            }
        }
        testPoints = points.clone();//.copyOf(points, points.length);
        segs = new ArrayList<LineSegment>();// too long
        cnt = 0;
    } // finds all line segments containing 4 points

    public int numberOfSegments() {
        return cnt;
    } // the number of line segments

    public LineSegment[] segments() {

        Point start, end;
        for (int i = 0; i < testPoints.length; i++) {
            for (int j = i + 1; j < testPoints.length; j++) {
                for (int k = j + 1; k < testPoints.length; k++) {
                    if (testPoints[i].slopeTo(testPoints[j]) == testPoints[j].slopeTo(testPoints[k])) {
                        for (int l = k + 1; l < testPoints.length; l++) {
                            if (testPoints[i].slopeTo(testPoints[j]) == testPoints[k].slopeTo(testPoints[l])) {
                                // update start and end
                                if (testPoints[i].compareTo(testPoints[j]) < 0) {
                                    start = testPoints[i];
                                    end = testPoints[j];
                                } else {
                                    start = testPoints[j];
                                    end = testPoints[i];
                                }

                                if (start.compareTo(testPoints[k]) > 0) {
                                    start = testPoints[k];
                                }
                                if (end.compareTo(testPoints[k]) < 0) {
                                    end = testPoints[k];
                                }

                                if (start.compareTo(testPoints[l]) > 0) {
                                    start = testPoints[l];
                                }
                                if (end.compareTo(testPoints[l]) < 0) {
                                    end = testPoints[l];
                                }
                                cnt++;
                                segs.add(new LineSegment(start, end));
                            }
                        }
                    }
                }
            }
        }
        LineSegment[] res = new LineSegment[segs.size()];
        for (int i = 0; i < segs.size(); i++)
            res[i] = segs.get(i);
        return res.clone();
    } // the line segments

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}