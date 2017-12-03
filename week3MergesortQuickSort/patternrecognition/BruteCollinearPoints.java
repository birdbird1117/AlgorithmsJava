import edu.princeton.cs.algs4.*;
import java.lang.*;
import java.util.*;

public class BruteCollinearPoints {
    private    Point[] testPoints;
    private   int cnt = 0;
    private  LineSegment[] segs;
    private  int segs_index = 0;
    
    public BruteCollinearPoints(Point[] points){
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
        segs = new LineSegment[testPoints.length];// too long
    }    // finds all line segments containing 4 points
    
    public           int numberOfSegments(){
        return cnt;
    }        // the number of line segments
    
    public LineSegment[] segments(){        
        
        Point start, end;
        for(int i = 0; i < testPoints.length; i++)
        {
            for(int j = i+1; j < testPoints.length; j++)
            {
                
                for(int k = j+1; k < testPoints.length; k++)
                {
                    
                    
                    if (testPoints[i].slopeTo(testPoints[j]) == testPoints[i].slopeTo(testPoints[k]))
                    {
                        for (int l = k+1; l <testPoints.length; l++)
                        {
                            
                            if (testPoints[i].slopeTo(testPoints[j]) == testPoints[i].slopeTo(testPoints[l]))
                            {
                                // update start and end
                                if (testPoints[i].compareTo(testPoints[j]) < 0)
                                {
                                    start = testPoints[i];
                                    end = testPoints[j];
                                }
                                else
                                {
                                    start = testPoints[j];
                                    end = testPoints[i];
                                }
                                
                                if (start.compareTo(testPoints[k]) > 0)
                                {
                                    start = testPoints[k];
                                }
                                if (end.compareTo(testPoints[k]) < 0)
                                {
                                    end = testPoints[k];
                                }
                                
                                if (start.compareTo(testPoints[l]) > 0)
                                {
                                    start = testPoints[l];
                                }
                                if (end.compareTo(testPoints[l]) < 0)
                                {
                                    end = testPoints[l];
                                }
                                cnt++;
//                            StdOut.println("testpoints" +testPoints[i]);                        
//                            StdOut.println("testpoints" +testPoints[l]);                        
//                            StdOut.println("i-l slope" +testPoints[i].slopeTo(testPoints[l]));                        
//                            StdOut.println("i,j,k,l, segs_index,cnt, i_l slope:" + i+j+k+l+segs_index+cnt+testPoints[i].slopeTo(testPoints[l]));                        
                                segs[segs_index++] = new LineSegment(start,end);
                            }   
                        }
                    }   
                }        
            }
        }
        LineSegment[] res = new LineSegment[segs_index];
        for (int i = 0; i < segs_index; i++)
            res[i] = segs[i];
        return res;
    }                // the line segments
    
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