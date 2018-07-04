import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
//import java.lang.*;
//import java.util.*;
import java.util.ArrayList;

public class Solver {

    private MinPQ<Node> pq;
    private MinPQ<Node> pq_twin;
    private int moves = 0;
    private Queue<Board> boards;
    private Board aBoard;
    private boolean issolvable = false;

    private class Node implements Comparable<Node> {
        private Board nodeboard;
        private int priority = 0;
        private int nodeMoves = 0;
        private Node predecessor = null;

        private Node(Board board, int moves) {
            this.nodeboard = board;
            this.nodeMoves = moves;
        }

        private void setPredecessor(Node that) {
            this.predecessor = that;
        }

        private int priority() {
            this.priority = this.nodeMoves + this.nodeboard.manhattan();
            return this.priority;
        }

        public int compareTo(Node that) {
            if (this.priority() < that.priority()) {
                return -1;
            }
            if (this.priority() > that.priority()) {
                return +1;
            }
            return 0;
        }
    }

    public Solver(Board initial) // find a solution to the initial board (using the A* algorithm)
    {
        aBoard = initial;
        boards = new Queue<Board>();
        Node node_aBoard = new Node(aBoard, 0);
        node_aBoard.predecessor = null;
        Node node_aBoard_twin = new Node(aBoard.twin(), 0);
        node_aBoard_twin.predecessor = null;

        pq = new MinPQ<Node>();
        pq_twin = new MinPQ<Node>();

        //pq.insert(node_aBoard);
        //pq_twin.insert(node_aBoard_twin);

        boolean found = false;
        Node board1 = node_aBoard;
        Node board1_twin = node_aBoard_twin;
        while (!found)//pq.delMin().board.isGoal() | !pq_twin.delMin().board.isGoal() )
        {

            StdOut.println("board1: " + board1.nodeboard.toString());

            boards.enqueue(board1.nodeboard);

            if ((board1.nodeboard.isGoal()) | (board1_twin.nodeboard.isGoal())) {
                found = true;
                if (board1.nodeboard.isGoal())
                    issolvable = true;
                else
                    issolvable = false;

            }
            moves++;
            StdOut.println("moves: " + moves);
            
            for (Board i : board1.nodeboard.neighbors()) {
                StdOut.println("board nabor: " + i.toString());
                if ((board1.predecessor == null) || (i.equals(board1.predecessor.nodeboard) == false)) {
                    Node aNode = new Node(i, moves);
                    aNode.predecessor = board1;
                    StdOut.println("inserted this one " );
                    pq.insert(aNode);
                    StdOut.println("pq size: " + pq.size());

                } else {
                    StdOut.println("NOT inserted this one " );
                    StdOut.println("pq size: " + pq.size());
                    
                }
            }

            for (Board i : board1_twin.nodeboard.neighbors()) {
                if ((board1_twin.predecessor == null) || (i.equals(board1_twin.predecessor.nodeboard) == false)) {
                    Node aNode = new Node(i, moves);
                    aNode.predecessor = board1_twin;
                    pq_twin.insert(aNode);
                }
            }
            board1 = pq.delMin();
            board1_twin = pq_twin.delMin();
            StdOut.println("-----------------------------------------------------");            

        }

    }

    public boolean isSolvable() // is the initial board solvable?
    {
        return issolvable;

    }

    public int moves() // min number of moves to solve initial board; -1 if unsolvable
    {

        if (issolvable) {
            return moves - 1;
        } else {
            return -1;
        }
    }

    public Iterable<Board> solution() // sequence of boards in a shortest solution; null if unsolvable
    {
        if (issolvable) {
            return boards;
        } else {
            return null;
        }

    }

    public static void main(String[] args) // solve a slider puzzle (given below)
    {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
