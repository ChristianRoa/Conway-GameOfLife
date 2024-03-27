package conwaygame;
import java.util.ArrayList;
/**
 * Conway's Game of Life Class holds various methods that will
 * progress the state of the game's board through it's many iterations/generations.
 *
 * Rules 
 * Alive cells with 0-1 neighbors die of loneliness.
 * Alive cells with >=4 neighbors die of overpopulation.
 * Alive cells with 2-3 neighbors survive.
 * Dead cells with exactly 3 neighbors become alive by reproduction.

 * @author Seth Kelley 
 * @author Maxwell Goldberg
 */
public class GameOfLife {

    // Instance variables
    private static final boolean ALIVE = true;
    private static final boolean  DEAD = false;

    private boolean[][] grid;    // The board has the current generation of cells
    private int totalAliveCells; // Total number of alive cells in the grid (board)

    /**
    * Default Constructor which creates a small 5x5 grid with five alive cells.
    * This variation does not exceed bounds and dies off after four iterations.
    */
    public GameOfLife() {
        grid = new boolean[5][5];
        totalAliveCells = 5;
        grid[1][1] = ALIVE;
        grid[1][3] = ALIVE;
        grid[2][2] = ALIVE;
        grid[3][2] = ALIVE;
        grid[3][3] = ALIVE;
    }

    /**
    * Constructor used that will take in values to create a grid with a given number
    * of alive cells
    * @param file is the input file with the initial game pattern formatted as follows:
    * An integer representing the number of grid rows, say r
    * An integer representing the number of grid columns, say c
    * Number of r lines, each containing c true or false values (true denotes an ALIVE cell)
    */
    public GameOfLife (String file) {
        StdIn.setFile(file);
        int r = StdIn.readInt();
        int c = StdIn.readInt();
        this.grid = new boolean[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                this.grid[i][j] = StdIn.readBoolean();
            }
        }
        // WRITE YOUR CODE HERE
    }

    /**
     * Returns grid
     * @return boolean[][] for current grid
     */
    public boolean[][] getGrid () {
        return grid;
    }
    
    /**
     * Returns totalAliveCells
     * @return int for total number of alive cells in grid
     */
    public int getTotalAliveCells () {
        return totalAliveCells;
    }

    /**
     * Returns the status of the cell at (row,col): ALIVE or DEAD
     * @param row row position of the cell
     * @param col column position of the cell
     * @return true or false value "ALIVE" or "DEAD" (state of the cell)
     */
    public boolean getCellState (int row, int col) {
        if ( this.grid[row][col] == ALIVE){
            return true;
        }
        else {
            return false;
        }

        // WRITE YOUR CODE HERE
         // update this line, provided so that code compiles
    }

    /**
     * Returns true if there are any alive cells in the grid
     * @return true if there is at least one cell alive, otherwise returns false
     */
    public boolean isAlive () {
        boolean check = false;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if(this.grid[i][j] == true){
                    check = true;
                }
            }
            
        }
        return check;
        // WRITE YOUR CODE HERE
        // update this line, provided so that code compiles
    }

    /**
     * Determines the number of alive cells around a given cell.
     * Each cell has 8 neighbor cells which are the cells that are 
     * horizontally, vertically, or diagonally adjacent.
     * 
     * @param col column position of the cell
     * @param row row position of the cell
     * @return neighboringCells, the number of alive cells (at most 8).
     */
    public int numOfAliveNeighbors (int row, int col) {
        int numOfAliveCells = 0;
        int numRows = grid.length;
        int numCols = grid[0].length;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int neighborRow = (row + i + numRows) % numRows;
                int neighborCol = (col + j + numCols) % numCols;
                if (!(i == 0 && j == 0) && grid[neighborRow][neighborCol] == ALIVE) {
                    numOfAliveCells++;
                }
            }
        }
            return numOfAliveCells;
    }
    //     if (row - 1 >= 0 && this.grid[row - 1][col] == ALIVE) {
    //         numOfAliveCells++;
    //     }
    //     if (row + 1 < this.grid.length && this.grid[row + 1][col] == ALIVE) {
    //         numOfAliveCells++;
    //     }
    //     if (col - 1 >= 0 && this.grid[row][col - 1] == ALIVE) {
    //         numOfAliveCells++;
    //     }
    //     if (col + 1 < this.grid[row].length && this.grid[row][col + 1] == ALIVE) {
    //         numOfAliveCells++;
    //     }
    //     if (row - 1 >= 0 && col - 1 >= 0 && this.grid[row - 1][col - 1] == ALIVE) {
    //         numOfAliveCells++;
    //     }
    //     if (row - 1 >= 0 && col + 1 < this.grid[row].length && this.grid[row - 1][col + 1] == ALIVE) {
    //         numOfAliveCells++;
    //     }
    //     if (row + 1 < this.grid.length && col - 1 >= 0 && this.grid[row + 1][col - 1] == ALIVE) {
    //         numOfAliveCells++;
    //     }
    //     if (row + 1 < this.grid.length && col + 1 < this.grid[row].length && this.grid[row + 1][col + 1] == ALIVE) {
    //         numOfAliveCells++;
    //     }
    //     return numOfAliveCells;

    /**
     * Creates a new grid with the next generation of the current grid using 
     * the rules for Conway's Game of Life.
     * 
     * @return boolean[][] of new grid (this is a new 2D array)
     */
    public boolean[][] computeNewGrid () {
        int row = grid.length;
        int col = grid[0].length;
        boolean[][] newGrid = new boolean[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int aliveNeighbors = numOfAliveNeighbors(i, j);
                if(aliveNeighbors == 0 || aliveNeighbors == 1){
                    newGrid[i][j] = DEAD;
                }
                if(aliveNeighbors == 3 && this.grid[i][j] == DEAD){
                    newGrid[i][j] = ALIVE;
                }
                if(this.grid[i][j] == ALIVE && (aliveNeighbors == 2 || aliveNeighbors == 3)){
                    newGrid[i][j] = ALIVE;
                }
                if(aliveNeighbors >= 4 && this.grid[i][j] == ALIVE){
                    newGrid[i][j] = DEAD;
                }
            }
            
        }
        return newGrid;
        
        // WRITE YOUR CODE HERE
        // update this line, provided so that code compiles
    }

    /**
     * Updates the current grid (the grid instance variable) with the grid denoting
     * the next generation of cells computed by computeNewGrid().
     * 
     * Updates totalAliveCells instance variable
     */
    public void nextGeneration () {
        boolean[][] newGrid  = computeNewGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                this.grid[i][j] = newGrid[i][j];   
            } 
        }
        // WRITE YOUR CODE HERE
    }

    /**
     * Updates the current grid with the grid computed after multiple (n) generations. 
     * @param n number of iterations that the grid will go through to compute a new grid
     */
    public void nextGeneration (int n) {
        for (int generations = 0; generations < n; generations++) {
            boolean[][] newGrid = computeNewGrid();
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    this.grid[i][j] = newGrid[i][j];
                }
            }
        }
        // WRITE YOUR CODE HERE
    }

    /**
     * Determines the number of separate cell communities in the grid
     * @return the number of communities in the grid, communities can be formed from edges
     */
    public int numOfCommunities() {
        int rowLength = grid.length;
        int colLength = grid[0].length;
        WeightedQuickUnionUF wqu = new WeightedQuickUnionUF(rowLength, colLength);
        ArrayList<Integer> newRoots = new ArrayList<>();
        int counter = 0;
        for (int i = 0; i < rowLength; i++) {
            for (int j = 0; j < colLength; j++) {
                if(grid[i][j] == ALIVE){
                    for (int i2 = -1; i2 < 2; i2++) {
                        for (int j2 = -1; j2 < 2; j2++) {
                            int neighborRow = (i + i2 + rowLength) % rowLength;
                            int neighborCol = (j + j2 + colLength) % colLength;
                            if (!(i2 == 0 && j2 == 0) && grid[neighborRow][neighborCol] == ALIVE) {
                                wqu.union(i,j,neighborRow,neighborCol);
                            }
                        }
                    }
                    
                }
            }
        }
        for (int i = 0; i < rowLength; i++) {
            for (int j = 0; j < colLength; j++) {
                if( grid[i][j] == ALIVE){
                    int root = wqu.find(i,j);
                    if(!newRoots.contains(root)){
                        newRoots.add(root);
                        counter++;
                    }
                }
            }
            
        }
        return counter;
    }
}

