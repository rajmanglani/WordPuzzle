/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * This class encapsulates the logic for generating a word puzzle board given an array of words
 * and board size. 
 * @author rkmanglani2018
 */
public class WordPuzzle1 {
     private char[][] filledBoard; // Words + all random alphabets
    private char[][] board;          // Words arranged on the board
    private String[] words;           // Array of words that can be in the puzzle
    private List<String> flippedList = new ArrayList<String>();  // List of all words with orientation
    private HashMap<String,String>Checker=new HashMap<String,String>();  // Map that records the word and its orientation in the puzzle
  //  private List<String> straightList = new ArrayList<String>();
    private HashMap<String,String> map = new HashMap<String,String>(); // Map that records the words that are not in the puzzle and their orientation
    
    public WordPuzzle1(String[] wordList, int boardSize){
       board = new char[boardSize][boardSize];
       for(int i =0; i<board.length; i++){
           for(int j = 0; j<board.length; j++){
               //Initialize the board to ' ' 
               board[i][j] = ' ';
           }
       }
       
       for(String word : wordList){
           
           if(this.addWord(word, board)){
             //  flippedList.add(word);
           }
       }
       
       
       
       filledBoard = this.filledBoardGenerator(board);
    }
    
    /**
     * This method adds a word to the puzzle 
     * @param word
     * @param puzzle
     * @return 
     */
    private boolean addWord(String word, char[][] puzzle){
        word = word.toUpperCase(); // all words should be displayed in uppercase
        char[][] initialPuzzle = new char[puzzle.length][puzzle.length];
        
        for(int i = 0; i<puzzle.length; i++){
            for(int j = 0; j<puzzle.length; j++){
                initialPuzzle[i][j] = puzzle[i][j];
            }
        }
        
        for(int tries = 0; ; tries++){
            Random r = new Random();
            int direction = r.nextInt(3);   // 0 - horizontal , 1 - vertical , 2 - diagnol
            int orientation = r.nextInt(2);  // 0 - forward and 1 - backward
            if(orientation == 1){
               word = this.flipWord(word);
               Checker.put(word, "flipped");
            }
            else{
                Checker.put(word,"straight");
            }
            flippedList.add(word);
            int row = r.nextInt(puzzle.length-word.length());   // random row and col to place the word
            int column = r.nextInt(puzzle.length-word.length());
            
            int i = 0;
            for(i =0; i<word.length(); i++){
                if(puzzle[row][column] == ' ' || puzzle[row][column] == word.charAt(i)){
                    puzzle[row][column] = word.charAt(i);
                    if(direction == 0){
                        column++;
                    }
                    if(direction == 1){
                        row++;
                    }
                    if(direction == 2){
                        row++;
                        column++;
                    }
                     
                } else{
                    
                    for(int j =i; j>0; j--){
                
                        if(direction == 0){
                            column--;
                        }
                        if(direction == 1){
                            row--;
                        }
                        if(direction == 2){
                            column--;
                            row--;
                        }
                        
                        puzzle[row][column] = initialPuzzle[row][column]; 
                        
                    }if(orientation==1){
                        map.put(word,"flipped");
                    }else{
                        map.put(word, "straight");
                    }
                    break;
                }
               
            }
            if(--i > 0){
                return true;  
            } if(orientation==1){
                map.put(word, "flipped");
            }else{
                map.put(word, "straight");
            }
            return false;
       }
        
        
    }
    
    /**
     * This method flips a word. It uses string builder object 
     * @param word flipped word
     * @return 
     */
    private String flipWord(String word){
        StringBuilder flippedWord = new StringBuilder();
        for(int i =word.length()-1; i>= 0; i--){
            flippedWord.append(word.charAt(i));
        }
        return flippedWord.toString();
    }
    
    private char randChar(){
        Random r = new Random();
        char[] alphabets = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        
        int randomInt = r.nextInt(alphabets.length);
        return alphabets[randomInt];               // Returns a random alphabet from A-Z
    }
    
    /**
     * This method fills up the board with random alphabets to make it a full word puzzle
     * @param board
     * @return 
     */
    private char[][] filledBoardGenerator(char[][] board){
        char[][] randomFilledBoard = new char[board.length][board.length];
        for(int i =0; i<randomFilledBoard.length; i++){
            for(int j = 0; j<randomFilledBoard.length; j++){
                if(board[i][j] != ' '){
                    randomFilledBoard[i][j] = board[i][j];
                } else{
                    randomFilledBoard[i][j] = this.randChar();
                }
            }
        }
        
        return randomFilledBoard;
    }

    public char[][] getFilledBoard() {
        return filledBoard;
    }

    public char[][] getBoard() {
        return board;
    }
    
    public List<String> getFlippedList(){
        return flippedList;
    }

    public HashMap<String, String> getMap() {
        return map;
    }
    public HashMap<String, String> getChecker() {
        return Checker;
    }
    
    /**
     * This method returns the puzzle in a 2 dimensional String array form 
     * @return 
     */
    public String[][] retrievePuzzle(){
        String[][] retArr = new String[filledBoard.length][filledBoard.length];
        for(int i=0;i<filledBoard.length;i++){
            for(int j =0; j<filledBoard.length;j++){
                retArr[i][j] = String.valueOf(filledBoard[i][j]);
            }
        }
        return retArr;
    }
}
