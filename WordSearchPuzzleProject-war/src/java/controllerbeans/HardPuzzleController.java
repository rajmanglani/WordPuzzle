/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerbeans;

import applogic.HeuristicKnightsTour1;
import applogic.WordPuzzle1;
import entities.Hardwords;
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
import session.HardwordsFacade;
import session.PlayerFacade;

/**
 *
 * @author rkmanglani2018
 */
@ManagedBean
@ViewScoped
public class HardPuzzleController {

    @EJB
    private HardwordsFacade hardwordsFacade;
     
     @ManagedProperty(value="#{logInController}")
    private LogInController loginController;
     
     @EJB
     private PlayerFacade playerFacade;
    
    private List<Hardwords> hardWords;
    private List<String> wordsInPuzzle;
    private List<String> distractionWords;
    private List<String> lettersSelected;
    private List<String> temp;
    
    private String[][] puzzleLetters;
    private WordPuzzle1 puzzleGenerator;
    private final int size = 14;
    
    private boolean done = false;
    private int wordsGiven;
    private int wordsFound = 0;
    private int totalHardGiven;
    private int totalHardFound;
    private Double winPercentage;
    private Random r;
    private String lettersSelectedStr2="";
    /**
     * Creates a new instance of UserEasyPuzzleController
     */
    public HardPuzzleController() {
        hardwordsFacade = new HardwordsFacade();
        playerFacade = new PlayerFacade();
        wordsInPuzzle = new ArrayList<>();
        distractionWords = new ArrayList<>();
        lettersSelected = new ArrayList<>();
        temp = new ArrayList<>();
        r = new Random();
    }

    @PostConstruct
    public void init(){
        hardWords = hardwordsFacade.findAll();
        String[] arr = new String[hardWords.size()];
        for(int i =0; i<hardWords.size(); i++){
            arr[i] = hardWords.get(i).getHardWords();
        }
        puzzleGenerator = new WordPuzzle1(arr, size);
        puzzleLetters = puzzleGenerator.retrievePuzzle();
        wordsInside();
        for(int i=0; i<wordsInPuzzle.size(); i++){
            temp.add(wordsInPuzzle.get(i));
        }
        wordsGiven = wordsInPuzzle.size();
        for(int i=0; i<hardWords.size(); i++){
            distractionWords.add(hardWords.get(i).getFakeHardWords().toUpperCase());
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

    public String getLettersSelectedStr2() {
        return lettersSelectedStr2;
    }

    public void setLettersSelectedStr2(String lettersSelectedStr2) {
        this.lettersSelectedStr2 = lettersSelectedStr2;
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
        lettersSelectedStr2 = lettersSelected + s;
        RequestContext.getCurrentInstance().update("form2:lettersSelectedStr2");
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
            context.execute("PF('dlg3').show();");
        }
        lettersSelected.clear();
        RequestContext.getCurrentInstance().update("form2:display2");
        
        if(wordsFound == wordsGiven){
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlgPlayerWon2').show();");
            Player player = new Player();
            String email = loginController.getLoginEmail();
            player = playerFacade.find(email.toLowerCase());
            totalHardGiven = player.getHardWordsGiven() + wordsGiven;
            player.setHardWordsGiven(totalHardGiven);
            totalHardFound = player.getHardWordsSolved() + wordsFound + 1;
            player.setHardWordsSolved(totalHardFound);
            player.setHardPercentage((double)(totalHardFound) / (double) (totalHardGiven)*100);
            playerFacade.edit(player);
            reset();
        }
    }
    
     public void closeDlg(){
        RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlg3').hide();");
    }
    
    public void executeKnightsTour(){
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable(){
            public void run(){
                System.out.println("Solving....");
                HeuristicKnightsTour1 ht1 = new HeuristicKnightsTour1(900);
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
                    context.execute("PF('dlgTourCompleted2').show();");
                    Player player = new Player();
                    String email = loginController.getLoginEmail();
                    player = playerFacade.find(email.toLowerCase());
                    totalHardGiven = player.getHardWordsGiven() + wordsGiven;
                    player.setHardWordsGiven(totalHardGiven);
                    totalHardFound = player.getHardWordsSolved() + wordsFound;
                    player.setHardWordsSolved(totalHardFound);
                    player.setHardPercentage((double)(totalHardFound) / (double) (totalHardGiven)*100);
                    playerFacade.edit(player);
                    done = false;
                    reset();
                    FacesContext.getCurrentInstance().getExternalContext().redirect("UserHomePage.xhtml");
              }
    }
    
    private void reset(){
        wordsGiven=0;
        wordsFound=0;
        totalHardFound=0;
        totalHardGiven =0;
    }
    
    public void onClickStats() throws IOException{
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "UserHomePage.xhtml"); 
    }
    
    public void onClickPlayAgain() throws IOException{
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
         ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
    
}
