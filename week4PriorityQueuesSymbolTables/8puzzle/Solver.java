import edu.princeton.cs.algs4.*;
import java.lang.*;
//import java.util.*;

public class Solver {
    
    private MinPQ<Node> pq; 
    private MinPQ<Node> pq_twin; 
    private int moves = 0;
    private Queue<Board> boards;    
    private Board aboard;
    private boolean issolvable = false;
    
    private class Node implements Comparable<Node>
    {
        private Board nodeboard;
        private int priority = 0;
        private int moves = 0;
        
        private Node (Board board, int moves)
        {
            this.nodeboard = board;
            this.moves = moves;        
        }
        
        private int priority()
        {
            priority = moves + nodeboard.manhattan();
            return priority;
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
    
    
    
    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        aboard = initial;
        boards = new Queue<Board>();    
        Node node_aboard = new Node(aboard, 0);
        Node node_aboard_twin = new Node(aboard.twin(), 0);
        
        pq = new MinPQ<Node>();       
        pq_twin = new MinPQ<Node>();
        
      //  int moves = 0;
        //int moves_twin = 0;
        
        pq.insert(node_aboard);
        pq_twin.insert(node_aboard_twin);
        
        //boards.push(aboard);
        
        Bag<Board> board_history = new Bag<Board>();
        Bag<Board> board_history_twin = new Bag<Board>();
        
//        board_history.add(node_aboard.board);
//        board_history_twin.add(node_aboard_twin.board);
        boolean found = false;    
        while(!found)//pq.delMin().board.isGoal() | !pq_twin.delMin().board.isGoal() )
        {
            Board board1 = pq.delMin().nodeboard;
            Board board1_twin = pq_twin.delMin().nodeboard;
            
//            StdOut.println("board1"+board1);
//            StdOut.println("moves"+moves);
            
            boards.enqueue(board1);
            
            if ((board1.isGoal())|(board1_twin.isGoal()))
            {
                found = true;
                if(board1.isGoal())
                    issolvable = true;
                else
                    issolvable = false;
                    
            }
            
            
            board_history.add(board1);
            board_history_twin.add(board1_twin);
            
            boolean repeated = false;
            boolean repeated_twin = false;
            moves++;
            for (Board i : board1.neighbors())
            {
//////                StdOut.println("board1.neighbors"+i);
                for (Board j : board_history)
                {
//                    StdOut.println("board1_history"+j);
                    if (i.equals(j))
                        repeated = true;
                }
                if (repeated != true)
                {
                    pq.insert(new Node(i, moves));
                    board_history.add(i);
                    
                }
                repeated = false;
            }

            for (Board i : board1_twin.neighbors())
            {
                for (Board j : board_history_twin)
                {
                    if (i.equals(j))
                        repeated_twin = true;
                }
                if (repeated_twin != true)
                {
                    pq_twin.insert(new Node(i, moves));
                    board_history_twin.add(i);
                   
                }
                 repeated_twin = false;
            }
            
            
        }
        
    }
    public boolean isSolvable()            // is the initial board solvable?
    {
        return issolvable;
        
    }
    
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        
        if (issolvable) {
            return moves-1;
        } else {
            return -1;
}
    }
    
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
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
