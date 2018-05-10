/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applogic;

/**
 *
 * @author rkmanglani2018
 */
public class Position1 {
    private int x;
    private int y;
    
    public Position1(int a, int b){
        x=a;
        y=b;
    }
    
    /**
     * Returns the row value
     * @return 
     */
    public int getX(){
        return x;
    }
    
    /**
     * Returns the column value
     * @return 
     */
    
    public int getY(){
        return y;
    }
    
    @Override
    public int hashCode(){
       return x*100 + y *100; 
    }
    
    @Override
    public boolean equals(Object t){
        Position1 p = (Position1)t;
        return this.x == p.x && this.y == p.y;
    }
    /**
     * This method converts a position (x and y) value into a square on the chess board. If the size of the board exceeds 26, the square is represented as AA2, AB4 and so on.. 
     * @return 
     */
    @Override
    public String toString() {
        int a = x + 1;
        StringBuilder result = new StringBuilder();
        do {
            result.append((char)('A' + (--a % 26)));
            a /= 26;
        } while (a != 0);
        return result.append(y + 1).toString();
    }
}
