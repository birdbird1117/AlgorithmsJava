import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class Subset {
    public static void main(String[] args){
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        while (!StdIn.isEmpty())
        {
            q.enqueue(StdIn.readString());
        }
        int k = Integer.parseInt(args[0]);
        for(int i =0; i<k; i++)
            StdOut.println( q.dequeue());      
    }
}