import edu.princeton.cs.algs4.*;
import java.lang.*;
import java.util.*;

public class FastCollinearPoints {
    private int cnt = 0;
    private Point[] testPoints;
    private Point[] originaltestPoints;
    private ArrayList<LineSegment> segs;
    private int segs_index = 0;
    private ArrayList<Double> slopeArrayList;
    private ArrayList<Point> startPoints;
    private ArrayList<Point> endPoints;

    public FastCollinearPoints(Point[] points) {
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
        testPoints = points;
        originaltestPoints = new Point[testPoints.length];
        for (int i = 0; i < points.length; i++) {
            originaltestPoints[i] = points[i];
        }
        slopeArrayList = new ArrayList<>();
        startPoints = new ArrayList<>();
        endPoints = new ArrayList<>();
        segs = new ArrayList<>();// too long
    } // finds all line segments containing 4 points

    public int numberOfSegments() {
        return cnt;
    } // the number of line segments

    public LineSegment[] segments() {

        Point start = null, end = null;
        int tmp = 0;

        // debuging
        //        for (int i = 0; i < testPoints.length; i++)
        //            StdOut.println("originaltestpoints1" +originaltestPoints[i]);   

        // sort testPoints according to each point in originalPoints
        for (int i = 0; i < testPoints.length; i++) {

            Arrays.sort(testPoints, originaltestPoints[i].slopeOrder());

            StdOut.println("testpoints,i round " + i + " sort with points: " + originaltestPoints[i].toString()
                    + "----------------------------");
            for (Point var : testPoints) {
                StdOut.println(var.toString());
            }
            tmp = 0; //FIXME
            for (int j = 1; j < testPoints.length - 2; j++) {
                StdOut.println("j , round " + j);

                if ((testPoints[0].slopeTo(testPoints[j]) == testPoints[j].slopeTo(testPoints[j + 1]))
                        && ((testPoints[j].slopeTo(testPoints[j + 1]) == testPoints[j].slopeTo(testPoints[j + 2])))) {
                    // update start and end
                    if (testPoints[0].compareTo(testPoints[j]) < 0) {
                        start = testPoints[0];
                        end = testPoints[j];
                    } else {
                        start = testPoints[j];
                        end = testPoints[0];
                    }

                    if (start.compareTo(testPoints[j + 1]) > 0) {
                        start = testPoints[j + 1];
                    }
                    if (end.compareTo(testPoints[j + 1]) < 0) {
                        end = testPoints[j + 1];
                    }

                    if (start.compareTo(testPoints[j + 2]) > 0) {
                        start = testPoints[j + 2];
                    }
                    if (end.compareTo(testPoints[j + 2]) < 0) {
                        end = testPoints[j + 2];
                    }
                    j += 2;
                    for (int l = j + 1; l < testPoints.length; l++) {
                        if (start.slopeTo(end) == testPoints[l].slopeTo(start)) {
                            if (start.compareTo(testPoints[l]) > 0) {
                                start = testPoints[l];
                            }
                            if (end.compareTo(testPoints[l]) < 0) {
                                end = testPoints[l];
                            }
                            j++;
                        } else
                            break;
                    }
                    StdOut.println("j: " + j);

                    StdOut.println("start: " + start.toString() + " end: " + end.toString());

                    LineSegment temp = new LineSegment(start, end);

                    if (slopeArrayList.indexOf(start.slopeTo(end)) == -1) {
                        segs.add(temp);
                        startPoints.add(start);
                        endPoints.add(end);
                        slopeArrayList.add(start.slopeTo(end));
                    } else {
                        if (start.compareTo(startPoints.get(slopeArrayList.indexOf(start.slopeTo(end)))) != 0) {
                            segs.add(temp);
                            startPoints.add(start);
                            endPoints.add(end);
                            slopeArrayList.add(start.slopeTo(end));
                        }
                    }

                    // if ((slopeArrayList.indexOf(start.slopeTo(end)) == -1) && (startPoints.indexOf(start) == -1)
                    //         || (slopeArrayList.indexOf(start.slopeTo(end)) != startPoints.indexOf(start))) {
                    //     segs.add(temp);
                    //     startPoints.add(start);
                    //     endPoints.add(end);
                    //     slopeArrayList.add(start.slopeTo(end));
                    // }
                }
            }
        }

        LineSegment[] res = new LineSegment[segs.size()];
        for (int i = 0; i < segs.size(); i++)
            res[i] = segs.get(i);
        return res;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}