import edu.princeton.cs.algs4.*;
import java.lang.*;
import java.util.*;

public class FastCollinearPoints {
    private int cnt = 0;
    private Point[] testPoints;
    private Point[] originaltestPoints;
    private LineSegment[] segs;
    private int segs_index = 0;
    
    public FastCollinearPoints(Point[] points){
        if (points == null) {
            throw new NullPointerException("argument is null");
        }
        
        for (int i = 0; i < points.length; i++)
        {
            if (points[i] == null)
                throw new NullPointerException("argument is null");
        }
        for (int i = 0; i < points.length; i++)
        {
            for (int j = i+1; j < points.length; j++)
            {
                if (points[i].compareTo(points[j])==0)
                    throw new IllegalArgumentException("repeated points");
            }
        }
        testPoints = points;
        originaltestPoints = new Point[testPoints.length];
        for (int i = 0; i < points.length; i++)
        {
            originaltestPoints[i] = points[i];
        }
        segs = new LineSegment[testPoints.length];// too long
    }    // finds all line segments containing 4 points
    
    
    public           int numberOfSegments(){
        return cnt;
    }        // the number of line segments
    
    public LineSegment[] segments() {
        
        Point start = null, end = null;
        int tmp = 0;
        Arrays.sort(originaltestPoints); // which order?
        
        // debuging
//        for (int i = 0; i < testPoints.length; i++)
//            StdOut.println("originaltestpoints1" +originaltestPoints[i]);   
        
        for (int i = 0; i < testPoints.length; i++)
        {
            Arrays.sort(testPoints,originaltestPoints[i].slopeOrder());
            
//            for (int k = 0; k < testPoints.length; k++) // debugging
//                StdOut.println("testpoints2" +testPoints[k]); 
//            StdOut.println("**********"+i); 
            
            for (int j = 0; j < testPoints.length-3; j++)
            {
                if(testPoints[j].slopeTo(testPoints[j+1]) == testPoints[j+3].slopeTo(testPoints[j]))
                {
                                                    // update start and end
                    if (testPoints[j].compareTo(testPoints[j+1]) < 0)
                    {
                        start = testPoints[j];
                        end = testPoints[j+1];
                    }
                    else
                    {
                        start = testPoints[j+1];
                        end = testPoints[j];
                    }
                    
                    if (start.compareTo(testPoints[j+2]) > 0)
                    {
                        start = testPoints[j+2];
                    }
                    if (end.compareTo(testPoints[j+2]) < 0)
                    {
                        end = testPoints[j+2];
                    }
                    
                    if (start.compareTo(testPoints[j+3]) > 0)
                    {
                        start = testPoints[j+3];
                    }
                    if (end.compareTo(testPoints[j+3]) < 0)
                    {
                        end = testPoints[j+3];
                    }

//                    StdOut.println("1j" + j);                                            
                    for (int l = j+4; l < testPoints.length; l++)
                    {
//                        StdOut.println("l" + l);    
//                        StdOut.println("2j" + j);                                            
                        if(start.slopeTo(end) == testPoints[l].slopeTo(start))
                        {
                            if (start.compareTo(testPoints[l]) > 0)
                            {
                                start = testPoints[l];
                            }
                            if (end.compareTo(testPoints[l]) < 0)
                            {
                                end = testPoints[l];
                            }
                            tmp = l-j-1;
                        }
                        else
                           tmp = 3;
                    }
                    
                    j = j+tmp;      
//                    StdOut.println("tmp" + tmp);                                            
//                    StdOut.println("3j" + j);                                            
//                    StdOut.println("start" +start);                                            
//                    StdOut.println("end" +end);      
                    segs[segs_index++] = new LineSegment(start,end);
                }
            }            
        }
        
        
        LineSegment[] res = new LineSegment[segs_index];
        for (int i = 0; i < segs_index; i++)
            res[i] = segs[i];
        return res;
    }               // the line segments
    
    
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