/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applogic;

/**
 * This class encapsulates the logic for solving the knight's tour using brute force algorithm. 
 * The knight is moved to a possible position and backtracks if there no other possible moves.
 * @author rkmanglani2018
 */
public class BruteForceKnightsTour1 {
    private int[][] solution;      // Will contain the solution once the knight has visited all the squares.
    private int size1;             // Number of rows in the board
    private int size2;             // Number of columns in the board
    private int startPos1;         // Start positions for the knight
    private int startPos2;
    
    public BruteForceKnightsTour1(int n1, int n2){
        solution = new int[n1][n2];
        size1 = n1;
        size2 = n2;
        
    }

    public BruteForceKnightsTour1() {
    }
    
    public BruteForceKnightsTour1(int n1, int n2, int pos1, int pos2){
        solution = new int[n1][n2];
        size1 = n1;
        size2 = n2;
        startPos1 = pos1;
        startPos2 = pos2;
    }
    
    /**
     * The method canMove() determines if the next valid move is safe and be selected by the knight to move. For the move to be valid, the square must lie within the board.
     * @param x
     * @param y
     * @return 
     */
    private boolean canMove(int x, int y){
        if( x>=0 && x < size1 && y>=0 && y < size2 && solution[x][y] == -1 )
            return true;
        return false;
    }
    
    /**
     * The method Done() accepts an initial position x and y of the knight along with the number of moves (0 - (size1*size2)-1) and the set of possible moves along row and column. 
     * It is a recursive method and returns true when the board is solved. It checks for valid moves, if found, moves the knight or else backtracks the knight and chooses another 
     * valid square on the board. It does this until it finds a solution. It returns false if there exists no solution to the board.
     * @param x
     * @param y
     * @param move
     * @param xPossible
     * @param yPossible
     * @return 
     */
    private boolean Done(int x, int y, int move, int[] xPossible, int[] yPossible){
        int nextX;                  // Used to determine the next valid square.
        int nextY;
        
        if(move == size1*size2)          // Solution to the board was found                    --- escape condition for recursive method.
            return true;
        
        for(int i = 0; i<xPossible.length; i++){
            //Check all valid moves on the row and columns.
            nextX = x + xPossible[i];
            nextY = y + yPossible[i];
            if(this.canMove(nextX, nextY)){      // If the move is valid and not visited
                solution[nextX][nextY] = move;      // Mark it with the move number 
//                System.out.println("At position : " + nextX + ", " + nextY);
                
                if(Done(nextX, nextY, move+1, xPossible, yPossible))   // recursively check for further valid moves.
                    return true;
                else{
                    solution[nextX][nextY] = -1;     // backtracks
//                    System.out.println("Backing from : " + nextX + ", " + nextY);
                }
                    
            }
        }
        return false;
    }
    
    /**
     * This method initializes the board with value -1 (meaning all squares are unvisited at the start). It also sets the start positions as visited and then solves 
     * for the board using above recursive method. 
     */
    public void Solve(){
        for(int i = 0; i<size1; i++){
            for(int j = 0; j<size2; j++){
                solution[i][j] = -1;
            }
        }
        
        int[] xPossible = {2, 1, -1, -2, -2, -1, 1, 2};
        int[] yPossible = {1, 2, 2, 1, -1, -2, -2, -1};
        
        solution[startPos1][startPos2] = 0;
        
        if(!this.Done(startPos1, startPos2, 1, xPossible, yPossible))
            System.out.println("No solution");
        else
        {
//            for(int i = 0; i<size1; i++){
//                for(int j =0 ; j<size2; j++){
//                    System.out.print(solution[i][j] + "  ");
//                }
//                System.out.println();
//            }
        }
    }
}
