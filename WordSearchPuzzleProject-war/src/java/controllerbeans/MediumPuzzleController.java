/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerbeans;

import applogic.HeuristicKnightsTour1;
import applogic.WordPuzzle1;
import entities.Mediumwords;
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
import session.MediumwordsFacade;
import session.PlayerFacade;

/**
 *
 * @author rkmanglani2018
 */
@ManagedBean
@ViewScoped
public class MediumPuzzleController {

   @EJB
    private MediumwordsFacade mediumwordsFacade;
     
     @ManagedProperty(value="#{logInController}")
    private LogInController loginController;
     
     @EJB
     private PlayerFacade playerFacade;
    
    private List<Mediumwords> mediumWords;
    private List<String> wordsInPuzzle;
    private List<String> distractionWords;
    private List<String> lettersSelected;
    private List<String> temp;
    
    private String[][] puzzleLetters;
    private WordPuzzle1 puzzleGenerator;
    private final int size = 12;
    
    private boolean done = false;
    private int wordsGiven;
    private int wordsFound = 0;
    private int totalMediumGiven;
    private int totalMediumFound;
    private Double winPercentage;
    private Random r;
    private String lettersSelectedStr1="";
    /**
     * Creates a new instance of UserEasyPuzzleController
     */
    public MediumPuzzleController() {
        mediumwordsFacade = new MediumwordsFacade();
        playerFacade = new PlayerFacade();
        wordsInPuzzle = new ArrayList<>();
        distractionWords = new ArrayList<>();
        lettersSelected = new ArrayList<>();
        temp = new ArrayList<>();
        r = new Random();
    }

    @PostConstruct
    public void init(){
        mediumWords = mediumwordsFacade.findAll();
        String[] arr = new String[mediumWords.size()];
        for(int i =0; i<mediumWords.size(); i++){
            arr[i] = mediumWords.get(i).getMediumWords();
        }
        puzzleGenerator = new WordPuzzle1(arr, size);
        puzzleLetters = puzzleGenerator.retrievePuzzle();
        wordsInside();
        for(int i=0; i<wordsInPuzzle.size(); i++){
            temp.add(wordsInPuzzle.get(i));
        }
        wordsGiven = wordsInPuzzle.size();
        for(int i=0; i<mediumWords.size(); i++){
            distractionWords.add(mediumWords.get(i).getFakeMediumWords().toUpperCase());
        }
        int n = r.nextInt(distractionWords.size());
        temp.add(distractionWords.get(n));
    }

    public String[][] getPuzzleLetters() {
        return puzzleLetters;
    }

    public void setPuzzleLetters(String[][] puzzleLetters) {
        this.puzzleLetters = puzzleLetters;
    }
    
    private void wordsInside(){
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

    public String getLettersSelectedStr1() {
        return lettersSelectedStr1;
    }

    public void setLettersSelectedStr1(String lettersSelectedStr1) {
        this.lettersSelectedStr1 = lettersSelectedStr1;
    }
    
    private String flipWord(String word){
        StringBuilder flippedWord = new StringBuilder();
        for(int i =word.length()-1; i>= 0; i--){
            flippedWord.append(word.charAt(i));
        }
        return flippedWord.toString();
    }
    
    public void collectLetters(String s){
        System.out.println(s);
        lettersSelected.add(s);
        lettersSelectedStr1 = lettersSelected + s;
        RequestContext.getCurrentInstance().update("form1:lettersSelectedStr1");
    }
    
    public void clearSelected(){
        lettersSelected.clear();
    }
    
    
    public void checkWord(){
        
        boolean found = false;
        StringBuilder bld = new StringBuilder();
        for(int i = 0; i<lettersSelected.size(); i++){
            bld.append(lettersSelected.get(i));
        }
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
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlg2').show();");
        }
        lettersSelected.clear();
        RequestContext.getCurrentInstance().update("form1:display1");
        
        if(wordsFound == wordsGiven){
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlgPlayerWon').show();");
            Player player = new Player();
            String email = loginController.getLoginEmail();
            player = playerFacade.find(email.toLowerCase());
            totalMediumGiven = player.getMediumWordsGiven() + wordsGiven;
            player.setMediumWordsGiven(totalMediumGiven);
            totalMediumFound = player.getMediumWordsSolved() + wordsFound +1;
            player.setMediumWordsSolved(totalMediumFound);
            player.setMediumPercentage((double)(totalMediumFound) / (double) (totalMediumGiven)*100);
            playerFacade.edit(player);
            reset();
        }
    }
     public void closeDlg(){
        RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlg2').hide();");
    }
    
    
    public void executeKnightsTour(){
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable(){
            public void run(){
                System.out.println("Solving....");
                HeuristicKnightsTour1 ht1 = new HeuristicKnightsTour1(800);
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
    
    public void checkDone() throws IOException{
        if(done){
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("PF('dlgTourCompleted').show();");
                    Player player = new Player();
                    String email = loginController.getLoginEmail();
                    player = playerFacade.find(email.toLowerCase());
                    totalMediumGiven = player.getMediumWordsGiven() + wordsGiven;
                    player.setMediumWordsGiven(totalMediumGiven);
                    totalMediumFound = player.getMediumWordsSolved() + wordsFound;
                    player.setMediumWordsSolved(totalMediumFound);
                    player.setMediumPercentage((double)(totalMediumFound) / (double) (totalMediumGiven)*100);
                    playerFacade.edit(player);
                    done = false;
                    reset();
                    FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "UserHomePage.xhtml");
              }
    }
    
    private void reset(){
        wordsGiven=0;
        wordsFound=0;
        totalMediumFound=0;
        totalMediumGiven =0;
    }
    
    public void onClickStats() throws IOException{
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "UserHomePage.xhtml"); 
    }
    
    public void onClickPlayAgain() throws IOException{
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
         ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
    
}
