/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * This class encapsulates the logic for solving the knight's tour using the heuristic algorithm.
 * The knight goes to the square on the chess board with fewer exit points first and then 
 * covers the other squares. It also checks for the degree of freedom offered by the board to the 
 * knight at a particular square. 
 * @author rkmanglani2018
 */
public class HeuristicKnightsTour1 {
    private static final int[][] moves = {{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2} }; // all legal (x,y) moves
    private final int[][] movement; 
    private Set<Position1> Path;   // final set of the path taken by the Knight to complete the tour.
    
    /* Comparator that puts the positions with fewer exit points and least degree of freedom first. */
    private Comparator<Position1> hardestPosition = new Comparator<Position1>(){

        @Override
        public int compare(Position1 t, Position1 t1) {
            int tPossibleMoves =  possibleMoves(t).size();
            int t1PossibleMoves = possibleMoves(t1).size();
            
            if(tPossibleMoves != t1PossibleMoves)
                return tPossibleMoves - t1PossibleMoves;
            else
                return getDegreeOfFreedom(t) - getDegreeOfFreedom(t1);
        }
        
    };
    
    
    public HeuristicKnightsTour1(int size, Position1 start){
        this.movement = PossibleMovement(size);
        this.Path = new LinkedHashSet<Position1>(size * size);
        if(this.Solve(start))
            this.Path = Collections.unmodifiableSet(Path);  // iterator cannot make any kind of change to the set Path. If does an exception will be thrown
        else
            this.Path.clear(); // No more moves are possible 
        
//        for(Position P : Path){
//            System.out.println(P.toString());
//        }
    }

   
    public HeuristicKnightsTour1(int size){
        this(size, new Position1(0,0));
    }
    
    
    public int getSize(){
        return this.movement.length;
    }
    
    /**
     * This method returns the degree of freedom for any position on the board using the two dimensional array movement. 
     * @param p
     * @return 
     */
    private int getDegreeOfFreedom(Position1 p){
        return this.movement[p.getX()][p.getY()];
    }
    
    private int[][] PossibleMovement(int size){
        int[][] board = new int[size][size];      // full freedom in most squares
        for(int[] row : board){
            Arrays.fill(row, moves.length);            // fills each row array with the value moves.length.
        }
        
        for(int i =0; i<board.length; i++){      // restrictions near the edges.
            for(int j : new int[] {0 ,1, board.length-2, board.length -1}){
                board[i][j] = board[j][i]= possibleMoves(new Position1(i,j), size, Collections.<Position1>emptySet()).size();
            }
        }
        return board;
    }
    
    /**
     * This method returns a list of all possible moves from a given square. It checks during backtracking, if the square 
     * is present in the set nope it doesn't use it or places the Knight there. 
     * @param p  - current position
     * @param size - size of the board
     * @param nope - restricted moves
     * @return 
     */
    private List<Position1> possibleMoves(Position1 p, int size, Set<Position1> nope){
        List<Position1> result = new ArrayList<Position1>(moves.length);
        for(int[] move : moves){
            int x = p.getX() + move[0];
            int y = p.getY() + move[1];
            if(x>=0 && y>=0 && x<size && y<size){
                Position1 possible = new Position1(x,y);
                if(!nope.contains(possible))
                    result.add(possible);
            }
        }
        return result;
    }
    
    private List<Position1> possibleMoves(Position1 p){
        return possibleMoves(p, this.getSize(), this.Path);
    }
    
    /**
     * This method solves the board until the length of the set Path becomes size*size -1.
     * @param p
     * @return 
     */
    private boolean Solve(Position1 p){
        this.Path.add(p);
        while(this.Path.size() < this.getSize() * this.getSize()){
            List<Position1> possibleMoves = possibleMoves(p);
            if(possibleMoves.isEmpty())
                return false;                                             
            
            Collections.sort(possibleMoves, hardestPosition);    // sorts the posiible moves list based on the hardest position to visit.          
            this.Path.add(p = possibleMoves.get(0));                     // adds the first position from the possible moves list
        } 
        return true;
    }
}
