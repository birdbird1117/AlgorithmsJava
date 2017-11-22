import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] grid1Dstatus;
    private WeightedQuickUnionUF grid1D;
    private int N;
    public Percolation(int n) // create n-by-n grid, with all sites blocked
    {
        if (n <= 0) 
            throw new IllegalArgumentException("n is negative");
        else 
        {
            N = n;
            grid1Dstatus = new boolean[n*n+2];
            grid1D = new WeightedQuickUnionUF(n*n+2);       
        }
    }
    
    private int xyTo1D(int row, int col)
    {
        if (row <= 0 || col <= 0 || row > N || col > N) 
        {
            System.out.printf("rol is %d, col is %d", row, col);
            throw new IndexOutOfBoundsException("row or col index i out of bounds");
        }
        else
            return (row-1)*N+(col-1);
    }
    // open site (row, col) if it is not open already
    public void open(int row, int col)     
    {
        if (row <= 0 || col <= 0 || row > N || col > N) 
        {
            System.out.printf("rol is %d, col is %d", row, col);
            throw new IndexOutOfBoundsException("row or col index i out of bounds");
        }
        else
        {
            if (grid1Dstatus[xyTo1D(row, col)] == false)// if is open
            {
                grid1Dstatus[xyTo1D(row, col)] = true; // if not, open it
            }
        }
        
        if (row == 1)           
            grid1D.union(N*N, xyTo1D(row, col));   
        
        if (row == N)
            grid1D.union(N*N+1, xyTo1D(row, col));
        
        if ((row != 1) && (grid1Dstatus[xyTo1D(row-1, col)] == true)) 
            grid1D.union(xyTo1D(row-1, col), xyTo1D(row, col));          
        if ((col != 1) && (grid1Dstatus[xyTo1D(row, col-1)] == true))
            grid1D.union(xyTo1D(row, col-1), xyTo1D(row, col)); 
        if ((row != N) && (grid1Dstatus[xyTo1D(row+1, col)] == true))
            grid1D.union(xyTo1D(row+1, col), xyTo1D(row, col)); 
        if ((col != N) && (grid1Dstatus[xyTo1D(row, col+1)] == true))
            grid1D.union(xyTo1D(row, col+1), xyTo1D(row, col)); 
    }
    
    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        if (row <= 0 || col <= 0 || row > N || col > N) 
        {
            System.out.printf("rol is %d, col is %d", row, col);
            throw new IndexOutOfBoundsException("row or col index i out of bounds");
        }
        else
            return grid1Dstatus[xyTo1D(row, col)] == true;
    }
    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        if (row <= 0 || col <= 0 || row > N || col > N) 
            throw new IndexOutOfBoundsException("row or col index i out of bounds");
        else
        {
            return grid1D.connected(xyTo1D(row, col), (N*N));
        }
    }
    public boolean percolates() { 
        return grid1D.connected(N*N, N*N+1);
    }              // does the system percolate?
    
    public static void main(String[] args) { }   // test client (optional)
}