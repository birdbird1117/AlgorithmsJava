
/*Dequeue. A double-ended queue or deque (pronounced “deck”) is a generalization
* of a stack and a queue that supports adding and removing items from either 
* the front or the back of the data structure. Create a generic data type Deque
* that implements the following API:

public class Deque<Item> implements Iterable<Item> {
public Deque()                           // construct an empty deque
public boolean isEmpty()                 // is the deque empty?
public int size()                        // return the number of items on the deque
public void addFirst(Item item)          // add the item to the front
public void addLast(Item item)           // add the item to the end
public Item removeFirst()                // remove and return the item from the front
public Item removeLast()                 // remove and return the item from the end
public Iterator<Item> iterator()         // return an iterator over items in order from front to end
public static void main(String[] args)   // unit testing (optional)
}
Corner cases.  Throw the specified exception for the following corner cases:

Throw a java.lang.IllegalArgumentException if the client calls either addFirst() or addLast() with a null argument.
Throw a java.util.NoSuchElementException if the client calls either removeFirst() or removeLast when the deque is empty.
Throw a java.util.NoSuchElementException if the client calls the next() method in the iterator when there are no more items to return.
Throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator.
Performance requirements.  Your deque implementation must support each deque operation (including construction) in constant worst-case time. A deque containing n items must use at most 48n + 192 bytes of memory and use space proportional to the number of items currently in the deque. Additionally, your iterator implementation must support each operation (including construction) in constant worst-case time.

*/

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int n;

    private class Node {
        private Item item;
        private Node next;
        private Node previous;
    }

    public Deque() {
        first = null;
        last = null;
        n = 0;
    } // construct an empty deque

    public boolean isEmpty() {
        return n == 0;
    } // is the deque empty?

    public int size() {
        return n;
    } // return the number of items on the deque

    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        else {
            Node oldfirst = first;
            first = new Node();
            first.item = item;
            first.next = oldfirst;
            first.previous = null;
            if (n != 0)
                oldfirst.previous = first;
            else
                last = first;
            n++;
        }
    } // add the item to the front

    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        else {
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
    } // add the item to the end

    public Item removeFirst() {
        if (n == 1) {
            Node oldfirst = first;
            last = null;
            first = null;
            n--;
            return oldfirst.item;
        } else if (n != 0) {
            Node oldfirst = first;
            first = first.next;
            first.previous = null;// avoid loitering?        
            n--;
            return oldfirst.item;
        } else
            throw new NoSuchElementException();
    } // remove and return the item from the front

    public Item removeLast() {
        if (n == 1) {
            Node oldlast = last;
            last = null;
            first = null;
            n--;
            return oldlast.item;
        } else if (n != 0) {
            Node oldlast = last;
            last = last.previous;
            last.next = null;
            n--;
            return oldlast.item;
        } else
            throw new NoSuchElementException();
    } // remove and return the item from the end

    public Iterator<Item> iterator() {
        return new DequeIterator();
    } // return an iterator over items in order from front to end

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<Integer> s = new Deque<Integer>();
        for (Integer i = 1; i < 11; i++)
            s.addFirst(i);
        for (Integer i : s)
            StdOut.println("s_i :" + s.removeFirst());
        StdOut.println("n :" + s.n);
    } // unit testing
}