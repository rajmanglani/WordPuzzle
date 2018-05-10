/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerbeans;

import applogic.HeuristicKnightsTour1;
import applogic.WordPuzzle1;
import entities.Easywords;
import entities.Player;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;
import session.EasywordsFacade;
import session.PlayerFacade;

/**
 * This controller consists of logic for the Easy puzzle page. It allows a user to 
 * play the easy puzzle whose view is rendered on the EasyLevelPuzzle.xhtml
 * @author rkmanglani2018
 */
@ManagedBean
@ViewScoped
public class EasyPuzzleController {
    
    @EJB
    private EasywordsFacade easywordsFacade;
     
    
     @ManagedProperty(value="#{logInController}")           // Bean Injection  --- to get details about the logged in user and record points .
    private LogInController loginController;
     
     @EJB
     private PlayerFacade playerFacade;
    
    private List<Easywords> easyWords;                   // To store easy words retrieved from db
    private List<String> wordsInPuzzle;                   // Words in the puzzle
    private List<String> distractionWords;                 // Distraction words
    private List<String> lettersSelected;                    // Letters selected by the user on the webpage
    private List<String> temp;                               // List of words displayed on the webpage
    
    private String[][] puzzleLetters;                     // Puzzle retrieved from the word puzzle object
    private WordPuzzle1 puzzleGenerator;
    private final int size = 10;                            // Size of easy puzzle
    
    private boolean done = false;                        // to check if the knight's tour is completed or not
    private int wordsGiven;                                  
    private int wordsFound = 0;
    private int totalEasyGiven;
    private int totalEasyFound;
    private Double winPercentage;
    private Random r;
    private String lettersSelectedStr="";
    private String distractionWord;                          // The puzzle can have one or no distraction words
    /**
     * Creates a new instance of UserEasyPuzzleController
     */
    public EasyPuzzleController() {
        easywordsFacade = new EasywordsFacade();
        playerFacade = new PlayerFacade();
        wordsInPuzzle = new ArrayList<>();
        distractionWords = new ArrayList<>();
        lettersSelected = new ArrayList<>();
        temp = new ArrayList<>();
        r = new Random();
    }

    @PostConstruct
    public void init(){                                   // Method executed while loading the page
        easyWords = easywordsFacade.findAll();
        String[] arr = new String[easyWords.size()+1];
        for(int i =0; i<easyWords.size(); i++){
            //Pu words from db into array for input into the word puzzle class
            arr[i] = easyWords.get(i).getEasyWords();
        }
        for(int i=0; i<easyWords.size(); i++){
            distractionWords.add(easyWords.get(i).getFakeEasyWords().toUpperCase());
        }
        int n = r.nextInt(distractionWords.size());  // Select a ronadom distraction word 
        distractionWord = distractionWords.get(n);
        arr[easyWords.size()] = distractionWord;
   
        puzzleGenerator = new WordPuzzle1(arr, size);
        
        puzzleLetters = puzzleGenerator.retrievePuzzle();
        wordsInside();
        for(int i=0; i<wordsInPuzzle.size(); i++){
            temp.add(wordsInPuzzle.get(i));
        }
        wordsGiven = wordsInPuzzle.size();
        
        //temp.add(distractionWords.get(n));
    }

    public String[][] getPuzzleLetters() {
        return puzzleLetters;
    }

    public void setPuzzleLetters(String[][] puzzleLetters) {
        this.puzzleLetters = puzzleLetters;
    }
    /**
     * This method evaluates the list of words that are to be scored in the puzzle.
     * It removes the distraction word and other words that were not entered into 
     * puzzle due to length overflow.
     */
    private void wordsInside(){
       //wordsInPuzzle = puzzleGenerator.getFlippedList();    // all words
       HashMap<String, String> p = puzzleGenerator.getChecker();  // all words with orientation as values
       Set sp = p.entrySet();
       Iterator i = sp.iterator();
       while(i.hasNext()){
           Map.Entry element = (Map.Entry) i.next();
           if(element.getValue().equals("flipped")){
               String str = element.getKey().toString();
               str = flipWord(str);
               wordsInPuzzle.add(str);
           }else{
               wordsInPuzzle.add(element.getKey().toString());
           }
       }
       
       
       HashMap<String,String> l = puzzleGenerator.getMap();  // words not in the puzzle
        Set s = l.entrySet();
        Iterator itr = s.iterator();
        
        while(itr.hasNext()){
            Map.Entry element = (Map.Entry) itr.next();
            String str;
            if(element.getValue().equals("flipped")){
                     str = flipWord(element.getKey().toString());
                }else{
                str = element.getKey().toString();
            }
            for(int j =0; j<wordsInPuzzle.size(); j++){
                if(str.equals(wordsInPuzzle.get(j))){
                    wordsInPuzzle.remove(str);
                }
            }
        }
        
        for(int j =0 ; j <wordsInPuzzle.size(); j++){
            if(wordsInPuzzle.get(j).equals(distractionWord)){
                wordsInPuzzle.remove(distractionWord);
                System.out.println(distractionWord);
            }
        }

    }

    public List<String> getWordsInPuzzle() {
        return wordsInPuzzle;
    }

    public void setWordsInPuzzle(List<String> wordsInPuzzle) {
        this.wordsInPuzzle = wordsInPuzzle;
    }

    public List<String> getLettersSelected() {
        return lettersSelected;
    }

    public void setLettersSelected(List<String> lettersSelected) {
        this.lettersSelected = lettersSelected;
    }

    public List<String> getTemp() {
        return temp;
    }

    public void setTemp(List<String> temp) {
        this.temp = temp;
    }

    public void setLoginController(LogInController loginController) {
        this.loginController = loginController;
    }

    public String getLettersSelectedStr() {
        return lettersSelectedStr;
    }

    public void setLettersSelectedStr(String lettersSelectedStr) {
        this.lettersSelectedStr = lettersSelectedStr;
    }
    
    private String flipWord(String word){
        StringBuilder flippedWord = new StringBuilder();
        for(int i =word.length()-1; i>= 0; i--){
            flippedWord.append(word.charAt(i));
        }
        return flippedWord.toString();
    }
    /**
     * Collects all the letters selected by the user
     * @param s 
     */
    public void collectLetters(String s){
        System.out.println(s);
        lettersSelected.add(s);
        lettersSelectedStr = lettersSelected + s;
        RequestContext.getCurrentInstance().update("form:lettersSelectedStr");
    }
    
    public void clearSelected(){
        lettersSelected.clear();
    }
    
    /**
     * This method checks the word selected by the user
     * @throws InterruptedException 
     */
    public void checkWord() throws InterruptedException{
        
        boolean found = false;
        StringBuilder bld = new StringBuilder();
        for(int i = 0; i<lettersSelected.size(); i++){
            bld.append(lettersSelected.get(i));
        }
        //User can select the word in forwards or backwards orientation
        StringBuilder opp = new StringBuilder();
        for(int j=lettersSelected.size()-1; j>=0; j--){
            opp.append(lettersSelected.get(j));
        }
        String oppwordSelected = opp.toString();
        String wordSelected = bld.toString();
        System.out.println(wordSelected);
        for(int j =0; j<temp.size(); j++){
            if(wordSelected.equals(temp.get(j)) || oppwordSelected.equals(temp.get(j))){
                temp.remove(j);
                wordsFound++;
                found = true;
                break;
            }
        }
        if(!found){
            //Put user in penalty box
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlg1').show();");
        }
        lettersSelected.clear();
        RequestContext.getCurrentInstance().update("form:display");
        
        if(wordsFound == wordsGiven){
            //If the player finishes the game
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlgPlayerWon1').show();");
            Player player = new Player();
            String email = loginController.getLoginEmail();
            player = playerFacade.find(email.toLowerCase());
            totalEasyGiven = player.getEasyWordsGiven() + wordsGiven;
            player.setEasyWordsGiven(totalEasyGiven);
            totalEasyFound = player.getEasyWordsSolved() + wordsFound + 1;
            player.setEasyWordsSolved(totalEasyFound);
            player.setEasyPercentage((double)(totalEasyFound) / (double) (totalEasyGiven)*100);
            playerFacade.edit(player);
            reset();
        }
    }
    
    public void closeDlg(){ // Take the player out of the penalty box
        RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlg1').hide();");
    }
    /**
     * This method starts the execution of the Knight's tour on a different 
     * thread and sets the boolean attribute to true when done 
     */
    public void executeKnightsTour(){
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable(){
            public void run(){
                System.out.println("Solving....");
                HeuristicKnightsTour1 ht1 = new HeuristicKnightsTour1(700);
                System.out.println("Done Solving...");
                done=true;
                
            }});
          executor.shutdown();
          
             // System.out.println(done);
//              if(done){
//                  RequestContext context = RequestContext.getCurrentInstance();
//                  context.execute("PF('dlgTourCompleted').show();");
//                 
//              }
             
          
          
    }
    
//    @Asynchronous
//    public void executeKnightsTour(){
//        BruteForceKnightsTour1 bt1 = new BruteForceKnightsTour1(6,6,4,4);
//        System.out.println("Solving....");
//        bt1.Solve();
//        System.out.println("Done Solving...");
//        RequestContext context = RequestContext.getCurrentInstance();
//        context.execute("PF('dlgTourCompleted').show();");
//    }
     /**
      * This method checks if the knight's tour is done. It is called asynchronously
      * by AJAX calls from the web page. 
      * @throws IOException 
      */
    public void checkDone() throws IOException{
        if(done){
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("PF('dlgTourCompleted1').show();");
                    Player player = new Player();
                    String email = loginController.getLoginEmail();
                    player = playerFacade.find(email.toLowerCase());
                    totalEasyGiven = player.getEasyWordsGiven() + wordsGiven;
                    player.setEasyWordsGiven(totalEasyGiven);
                    totalEasyFound = player.getEasyWordsSolved() + wordsFound;
                    player.setEasyWordsSolved(totalEasyFound);
                    player.setEasyPercentage((double)(totalEasyFound) / (double) (totalEasyGiven)*100);
                    playerFacade.edit(player);
                    done = false;
                    reset();
                    FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "UserHomePage.xhtml"); 
              }
    }
    
    private void reset(){
        wordsGiven=0;
        wordsFound=0;
        totalEasyFound=0;
        totalEasyGiven =0;
    }
    
    public void onClickStats() throws IOException{
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "UserHomePage.xhtml"); 
    }
    /**
     * Allows users to play the game again
     * @throws IOException 
     */
    public void onClickPlayAgain() throws IOException{
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
         ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
}
