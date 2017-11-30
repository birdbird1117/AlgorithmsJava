import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int n;
    
    private class Node{
        private Item item;
        private Node next;
        private Node previous;
    }
    
    public Deque(){
        first = null;
        last = null;
        n = 0;
    }                           // construct an empty deque
    
    public boolean isEmpty(){
        return n == 0;
    }                 // is the deque empty?
    
    public int size(){
        return n;
    }                        // return the number of items on the deque
    
    public void addFirst(Item item){       
        if (item == null)
            throw new NullPointerException();  
        else 
        {
            Node oldfirst = first;
            first = new Node();
            first.item = item;
            first.next = oldfirst;
            first.previous = null;
            if (n != 0)
                oldfirst.previous = first;
            else
                last = first;
            //first.previous = null;
            n++;
        }
    }          // add the item to the front
    public void addLast(Item item){
        if (item == null)
            throw new NullPointerException();  
        else 
        {
            Node oldlast = last;
            last = new Node();
            last.item = item;
            last.next = null;       
            last.previous = oldlast;
            if (n != 0)
                oldlast.next = last;
            else
                first = last;
            n++;
        }
    }           // add the item to the end
    public Item removeFirst()    {

         if (n == 1)
        {
            Node oldfirst = first;
            last = null;
            first = null;
            n--;
            return oldfirst.item;
        }
        else if (n != 0)
        {        
            Node oldfirst = first;
            first = first.next;// loitering?
            first.previous = null;// loitering?        
            n--;
            return oldfirst.item;
        }
        else
            throw new NoSuchElementException();  
    }            // remove and return the item from the front
    public Item removeLast()    {
        // Node oldlast = last;
        if (n == 1)
        {
            Node oldlast = last;
            last = null;
            first = null;
            n--;
            return oldlast.item;
        }
        else if (n != 0)
        {        
            Node oldlast = last;
            last = last.previous;
            last.next = null;         
            n--;
            return oldlast.item;
        }
        else
            throw new NoSuchElementException();
    }             // remove and return the item from the end
    public Iterator<Item> iterator() {
        return new DequeIterator();    
    }        // return an iterator over items in order from front to end
    
    private class DequeIterator implements Iterator<Item>{
        private Node current = first;
       // private Node revcurrent = last;
        public boolean hasNext() {
            return current != null;
            //return n >= 1;
        }
        public void remove() {
            throw new UnsupportedOperationException();  
        }
        
        public Item next() {
//            if (n==1)
//                return first.item;
           
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
           // StdOut.println("item :" + item); 
            return item;
        }
//        public boolean hasPrevious() {
//            return current.previous != null;
//        } 
//        
//        public Item Previous() {
//            if (!hasPrevious()) 
//                throw new NoSuchElementException();
//            Item item = current.item;
//            current = current.previous; 
//            return item;
//        }
    }
    public static void main(String[] args){
        Deque<Integer> s = new Deque<Integer>();
        for(Integer i = 1; i<11; i++)
            s.addFirst(i);
        for(Integer i : s)
            
        //for(Integer i = 1; i<11; i++)
            StdOut.println("s_i :" + s.removeFirst()); 
        StdOut.println("n :" + s.n); 
        
//        for(Integer i = 1; i<100; i++)
//            s.addLast(i);
//        for(Integer i = 1; i<100; i++)
//            StdOut.println("s_i :" + s.removeFirst());         
//        for(Integer i = 1; i<2; i++)
//            s.addFirst(i);
//        for(Integer i = 1; i<3; i++)
//            StdOut.println("s_i :" + s.removeLast()); 
//        for(Integer i = 1; i<100; i++)
//            s.addLast(i);
//        for(Integer i = 1; i<100; i++)
//            StdOut.println("s_i :" + s.removeFirst()); 
    }   // unit testing
}