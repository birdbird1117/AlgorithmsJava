import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.util.Arrays;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;         // array of items
    private int n;            // number of elements on stack
   // private int n;
    
    public RandomizedQueue() {
        a = (Item[]) new Object[2];
        n = 0;
       // n = 0;
    }                // construct an empty randomized queue
    public boolean isEmpty() {
        return n == 0;
    }                // is the queue empty?
    public int size() {
        return n;
    }                       // return the number of items on the queue
    
    private void resize(int capacity) {        
        // textbook implementation
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = a[i];
        }
        a = temp;
        
        // alternative implementation
        // a = java.util.Arrays.copyOf(a, capacity);
    }
    
    public void enqueue(Item item){
        //n++;
        if (item == null)
            throw new NullPointerException();  
        
        if (n == a.length) 
            resize(2*a.length);    // double size of array if necessary
        
        a[n] = item;                            // add item
        n++;
//        StdOut.println("en itme:" + item);
//        StdOut.println("en n:" + n);
//                StdOut.println("en len:" + a.length);
//                    System.out.println("eeee"+Arrays.toString(a));
//
//            StdOut.println("e item:" + item);          
//            StdOut.println("e n:" + n);          
//            StdOut.println("e n:" + n);
//            StdOut.println("e len:" + a.length);
    }           // add the item
    public Item dequeue() {
        if (n != 0) {
            int pick = StdRandom.uniform(0, n);
            Item item = a[pick];
            //a[pick] = null;                              // to avoid loitering

//           System.out.println(Arrays.toString(a));
           if (pick != n-1)
           {
               //           for (int i = pick; i < n; i++)
//               System.out.println(Arrays.toString(a));
//               StdOut.println("d n:" + n);                              
//               StdOut.println("d an-1:" + a[n-1]);               
//               StdOut.println("d an:" + a[n]);
               a[pick] = a[n-1];
           }
//            System.out.println(Arrays.toString(a));
            a[n-1] = null;
            n--;
            
            if ((n > 0) && (n) == a.length/4) resize(a.length/2);// n>0 is important

//            System.out.println("ddddd"+Arrays.toString(a));
//            StdOut.println("d pick:" + pick);
//            StdOut.println("d item:" + item);          
//            StdOut.println("d n:" + n);          
//            StdOut.println("d n:" + n);
//            StdOut.println("d len:" + a.length);
            return item;
        }
        else
            throw new NoSuchElementException();  
    }                   // remove and return a random item
    public Item sample()  {
        if (n != 0) 
        {
            int pick = StdRandom.uniform(0, n);
            Item item = a[pick];
            return item;
        }
        else
            throw new NoSuchElementException();  
    }                   // return (but do not remove) a random item
    public Iterator<Item> iterator()    {
        return new RandomizedQueueIterator();
        
    }     // return an independent iterator over items in random order
    // FIXME, not fully understand iterator
    private class  RandomizedQueueIterator implements Iterator<Item> {
        private int i = 0;
        private int[] shuffledIndexes = new int[n];
        public RandomizedQueueIterator() {
                for (int j = 0; j < n; j++)
                    shuffledIndexes[j] = j;
                StdRandom.shuffle(shuffledIndexes);
       }
        
        public boolean hasNext() {

                
            return i < n;
//            if (n = 0)
//                return false;
//            else if (n = 1)
//            return a[i+1] != null;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
           // int pick = StdRandom.uniform(0, n);
            //Item item = a[pick];

            return a[shuffledIndexes[i++]]; // why?
        }
    }
    
    public static void main(String[] args) {
        RandomizedQueue<Integer> s = new RandomizedQueue<Integer>();
        for(Integer i = 1; i<7; i++)
            s.enqueue(i);
        for(Integer i = 1; i<2; i++)
            StdOut.println("s_iasdf :" + s.dequeue());
        
        StdOut.println("s_i :" + s.isEmpty());
        
        for(Integer i = 1; i<2; i++)
            s.enqueue(i);
        for(Integer i = 1; i<7; i++)
            StdOut.println("s_i :" + s.dequeue());
        
        s.enqueue(21);
        StdOut.println("s_i :" + s.dequeue());
        s.enqueue(22);                    
        s.enqueue(23);
        StdOut.println("s_i :" + s.dequeue());                   
        StdOut.println("s_i :" + s.dequeue()); 
        
        StdOut.println("s_i :" + s.isEmpty());
    }  // unit testing
}