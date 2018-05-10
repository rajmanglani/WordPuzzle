/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerbeans;

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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import org.primefaces.context.RequestContext;
import session.EasywordsFacade;
import session.PlayerFacade;

/**
 *
 * @author rkmanglani2018
 */
@ManagedBean()
@ApplicationScoped
public class OnlinePuzzleController {

    @EJB
    private EasywordsFacade easywordsFacade;
     
     
     @EJB
     private PlayerFacade playerFacade;
    
    private List<Easywords> easyWords;
    private List<String> wordsInPuzzle;
    private List<String> lettersSelected;
    private List<String> temp;
    
    private String[][] puzzleLetters;
    private WordPuzzle1 puzzleGenerator;
    private final int size = 10;
    
    private boolean done = false;
    private Random r;
    private String lettersSelectedStr="";
    private String username;
    /**
     * Creates a new instance of OnlinePuzzleController
     */
    public OnlinePuzzleController() {
        easywordsFacade = new EasywordsFacade();
        playerFacade = new PlayerFacade();
        wordsInPuzzle = new ArrayList<>();
        lettersSelected = new ArrayList<>();
        temp = new ArrayList<>();
        r = new Random();
    }
    
    @PostConstruct
    public void init(){
        easyWords = easywordsFacade.findAll();
        String[] arr = new String[easyWords.size()];
        for(int i =0; i<easyWords.size(); i++){
            arr[i] = easyWords.get(i).getEasyWords();
        }
        puzzleGenerator = new WordPuzzle1(arr, size);
        puzzleLetters = puzzleGenerator.retrievePuzzle();
        wordsInside();
        for(int i=0; i<wordsInPuzzle.size(); i++){
            temp.add(wordsInPuzzle.get(i));
        }
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

    public String getLettersSelectedStr() {
        return lettersSelectedStr;
    }

    public void setLettersSelectedStr(String lettersSelectedStr) {
        this.lettersSelectedStr = lettersSelectedStr;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        lettersSelectedStr = lettersSelected + s;
        RequestContext.getCurrentInstance().update("formonline:lettersSelectedStr4");
    }
    
    public void clearSelected(){
        lettersSelected.clear();
    }
    
    
    public void checkWord() throws InterruptedException{
        
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
                Player temp = new Player();
                temp = playerFacade.find(this.getUsername());
                int given = temp.getEasyWordsGiven();
                given++;
                int solved = temp.getEasyWordsSolved();
                solved++;
                temp.setEasyPercentage((double)(solved) / (double) (given)*100);
                temp.setEasyWordsGiven(given);
                temp.setEasyWordsSolved(solved);
                playerFacade.edit(temp);
                break;
                
            }
        }
        lettersSelected.clear();
        RequestContext.getCurrentInstance().update("formonline:displayonline");
        
        if(temp.size()==0){
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlgGameOver').show();");
        }
    }
    
//    public void closeDlg(){
//        RequestContext context = RequestContext.getCurrentInstance();
//            context.execute("PF('dlg1').hide();");
//    }
//    
   
    
    public void refreshPage() throws IOException{
//        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//         ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        RequestContext.getCurrentInstance().update("formonline:displayonline");
    }
}
