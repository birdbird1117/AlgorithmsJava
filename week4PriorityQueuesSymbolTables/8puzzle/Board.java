import edu.princeton.cs.algs4.*;
import java.lang.*;
import java.util.*;

public class Board {

    private int[][] block_init;
    private int[][] block_twin;
    private int dimension = 0;
    private int numOfMoves = 0;

    public Board(int[][] blocks) // construct a board from an n-by-n array of blocks
    {
        dimension = blocks[0].length;
        block_init = new int[dimension][dimension];
        block_twin = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++) {
                block_init[i][j] = blocks[i][j];
                block_twin[i][j] = blocks[i][j];
            }
    } // (where blocks[i][j] = block in row i, column j)

    public int dimension() {
        return dimension;
    } // board dimension n

    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++) {
                if (block_init[i][j] != 0)// not right down corner
                {
                    if (block_init[i][j] != (j + 1 + (i) * dimension))
                        hamming++;
                }
            }
        return hamming;
    } // number of blocks out of place

    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++) {
                if (block_init[i][j] != 0)// not right down corner
                {
                    if (block_init[i][j] != (j + 1 + (i) * dimension))
                        //      manhattan += Math.abs(block_init[i][j]-(i+1+(j+1)*dimension))%dimension  + Math.abs([i][j]-(i+1+(j+1)*dimension));             
                        manhattan += Math.abs((block_init[i][j] - 1) / dimension - i)
                                + Math.abs((block_init[i][j] - 1) % dimension - (j));
                    //   StdOut.println("i,j, block[][],manhattan " +i+j+block_init[i][j]+manhattan);
                    //else if(block_init[i][j] < (i+1+(j+1)*dimension))
                    //    manhattan += ((i+1+(j+1)*dimension)-block_init[i][j]);
                }
            }
        return manhattan;
    } // sum of Manhattan distances between blocks and goal

    public boolean isGoal() {
        return manhattan() == 0;
    } // is this board the goal board?

    public Board twin() {
        if (block_twin[0][0] != 0) {
            if (block_twin[0][1] != 0) {
                block_twin[0][1] = block_init[0][0];
                block_twin[0][0] = block_init[0][1];
            } else {
                block_twin[1][0] = block_init[0][0];
                block_twin[0][0] = block_init[1][0];
            }
        } else {
            block_twin[0][1] = block_init[1][0];
            block_twin[1][0] = block_init[0][1];
        }
        return new Board(block_twin);
    } // a board that is obtained by exchanging any pair of blocks

    public boolean equals(Object y) {
        if (y == this)
            return true;
        if (y == null)
            return false;
        if (y.getClass() != this.getClass()) // what is this
            return false;
        Board that = (Board) y;
        if (that.dimension != this.dimension)
            return false;
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++) {
                if (this.block_init[i][j] != that.block_init[i][j])
                    return false;
            }
        return true;

    }// does this board equal y?

    public Iterable<Board> neighbors() {
        Bag<Board> neighbors = new Bag<Board>();
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++) {
                if (block_init[i][j] == 0) {
                    if ((i == 0)) {
                        if (j == 0) {
                            neighbors.add(exch(i, j, i, j + 1));
                            neighbors.add(exch(i, j, i + 1, j));
                            //   StdOut.println("4444"+this);
                        } else if (j == dimension - 1) {
                            neighbors.add(exch(i, j, i, j - 1));
                            neighbors.add(exch(i, j, i + 1, j));
                        } else {
                            neighbors.add(exch(i, j, i, j + 1));
                            neighbors.add(exch(i, j, i, j - 1));
                            neighbors.add(exch(i, j, i + 1, j));
                        }
                    } else if (i == dimension - 1) {
                        if (j == 0) {
                            neighbors.add(exch(i, j, i, j + 1));
                            neighbors.add(exch(i, j, i - 1, j));
                        } else if (j == dimension - 1) {
                            neighbors.add(exch(i, j, i, j - 1));
                            neighbors.add(exch(i, j, i - 1, j));
                        } else {
                            neighbors.add(exch(i, j, i, j + 1));
                            neighbors.add(exch(i, j, i, j - 1));
                            neighbors.add(exch(i, j, i - 1, j));
                        }
                    } else {
                        if (j == 0) {
                            neighbors.add(exch(i, j, i + 1, j));
                            neighbors.add(exch(i, j, i - 1, j));
                            neighbors.add(exch(i, j, i, j + 1));
                        } else if (j == dimension - 1) {
                            neighbors.add(exch(i, j, i + 1, j));
                            neighbors.add(exch(i, j, i - 1, j));
                            neighbors.add(exch(i, j, i, j - 1));
                        } else {
                            neighbors.add(exch(i, j, i, j + 1));
                            neighbors.add(exch(i, j, i, j - 1));
                            neighbors.add(exch(i, j, i - 1, j));
                            neighbors.add(exch(i, j, i + 1, j));
                        }

                    }
                }
            }
        return neighbors;
    }// all neighboring boards

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                s.append(String.format("%2d ", block_init[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    } // string representation of this board (in the output format specified below)

    private Board exch(int a, int b, int c, int d) {
        Board that = new Board(this.block_init);
        //StdOut.println("11111111"+that);
        that.block_init[a][b] = this.block_init[c][d];
        // StdOut.println("22222"+this);        
        // StdOut.println("22222that"+that);
        that.block_init[c][d] = this.block_init[a][b];
        // StdOut.println("3333"+that);
        return that;

    }

    public static void main(String[] args) // unit tests (not graded)
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

        StdOut.println("goal: " + initial.isGoal());
        StdOut.println("string: " + initial.toString());
        StdOut.println("manhattan: " + initial.manhattan());
        StdOut.println("hamming: " + initial.hamming());
        for (Board board : initial.neighbors())
            StdOut.println(board);
        StdOut.println("twin: " + initial.twin());

    }
}
