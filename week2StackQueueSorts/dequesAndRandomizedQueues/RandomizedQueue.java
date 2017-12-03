
/* Randomized queue. A randomized queue is similar to a stack or queue, except that the item removed is chosen uniformly at random from items in the data structure. Create a generic data type RandomizedQueue that implements the following API:

public class RandomizedQueue<Item> implements Iterable<Item> {
   public RandomizedQueue()                 // construct an empty randomized queue
   public boolean isEmpty()                 // is the randomized queue empty?
   public int size()                        // return the number of items on the randomized queue
   public void enqueue(Item item)           // add the item
   public Item dequeue()                    // remove and return a random item
   public Item sample()                     // return a random item (but do not remove it)
   public Iterator<Item> iterator()         // return an independent iterator over items in random order
   public static void main(String[] args)   // unit testing (optional)
}
Iterator.  Each iterator must return the items in uniformly random order. The order of two or more iterators to the same randomized queue must be mutually independent; each iterator must maintain its own random order.

Corner cases.  Throw the specified exception for the following corner cases:

Throw a java.lang.IllegalArgumentException if the client calls enqueue() with a null argument.
Throw a java.util.NoSuchElementException if the client calls either sample() or dequeue() when the randomized queue is empty.
Throw a java.util.NoSuchElementException if the client calls the next() method in the iterator when there are no more items to return.
Throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator.
Performance requirements.  Your randomized queue implementation must support each randomized queue operation (besides creating an iterator) in constant amortized time. That is, any sequence of m randomized queue operations (starting from an empty queue) must take at most cm steps in the worst case, for some constant c. A randomized queue containing n items must use at most 48n + 192 bytes of memory. Additionally, your iterator implementation must support operations next() and hasNext() in constant worst-case time; and construction in linear time; you may (and will need to) use a linear amount of extra memory per iterator.
*/

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.util.Arrays;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a; // array of items
    private int n; // number of elements on stack

    public RandomizedQueue() {
        a = (Item[]) new Object[2];
        n = 0;
    } // construct an empty randomized queue

    public boolean isEmpty() {
        return n == 0;
    } // is the queue empty?

    public int size() {
        return n;
    } // return the number of items on the queue

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

    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (n == a.length)
            resize(2 * a.length); // double size of array if necessary
        a[n] = item; // add item
        n++;
    } // add the item

    public Item dequeue() {
        if (n != 0) {
            int pick = StdRandom.uniform(0, n);
            Item item = a[pick];
            if (pick != n - 1) {
                a[pick] = a[n - 1];
            }
            a[n - 1] = null;
            n--;
            if ((n > 0) && (n) == a.length / 4)
                resize(a.length / 2);// n>0 is important
            return item;
        } else
            throw new NoSuchElementException();
    } // remove and return a random item

    public Item sample() {
        if (n != 0) {
            int pick = StdRandom.uniform(0, n);
            Item item = a[pick];
            return item;
        } else
            throw new NoSuchElementException();
    } // return (but do not remove) a random item

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();

    } // return an independent iterator over items in random order

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i = 0;
        private int[] shuffledIndexes = new int[n];

        public RandomizedQueueIterator() {
            for (int j = 0; j < n; j++)
                shuffledIndexes[j] = j;
            StdRandom.shuffle(shuffledIndexes);
        }

        public boolean hasNext() {
            return i < n;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return a[shuffledIndexes[i++]];
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> s = new RandomizedQueue<Integer>();
        for (Integer i = 1; i < 7; i++)
            s.enqueue(i);
        for (Integer i = 1; i < 2; i++)
            StdOut.println("s_iasdf :" + s.dequeue());

        StdOut.println("s_i :" + s.isEmpty());

        for (Integer i = 1; i < 2; i++)
            s.enqueue(i);
        for (Integer i = 1; i < 7; i++)
            StdOut.println("s_i :" + s.dequeue());

        s.enqueue(21);
        StdOut.println("s_i :" + s.dequeue());
        s.enqueue(22);
        s.enqueue(23);
        StdOut.println("s_i :" + s.dequeue());
        StdOut.println("s_i :" + s.dequeue());

        StdOut.println("s_i :" + s.isEmpty());
    } // unit testing
}