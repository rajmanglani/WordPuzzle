/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerbeans;

import entities.Easywords;
import entities.Hardwords;
import entities.Mediumwords;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.context.RequestContext;
import session.EasywordsFacade;
import session.HardwordsFacade;
import session.MediumwordsFacade;

/**
 * This controller enables the Admin to add easy, medium and hard words to the database. These words
 * are the contenders for being in the puzzle. It also allows the Admin to add distraction words
 * corresponding to the words.
 * @author rkmanglani2018
 */
@ManagedBean
@RequestScoped
public class AdminAddWordsController {

    @EJB 
    private EasywordsFacade easywordsFacade;
    @EJB
    private MediumwordsFacade mediumwordsFacade;
    @EJB
    private HardwordsFacade hardwordsFacade;
    
    private Easywords easyWord;
    private Mediumwords mediumWord;
    private Hardwords hardWord;
    
    
    private String easyNewWord;
    private String fakeEasyword;
    private String mediumNewWord;
    private String fakeMediumword;
    private String hardNewWord;
    private String fakeHardword;
    /**
     * Creates a new instance of AdminAddWordsController
     */
    public AdminAddWordsController() {
        //Initializing all objects
        easywordsFacade = new EasywordsFacade();
        easyWord = new Easywords();
        mediumwordsFacade = new MediumwordsFacade();
        mediumWord = new Mediumwords();
        hardwordsFacade = new HardwordsFacade();
        hardWord = new Hardwords();
    }

    public String getEasyNewWord() {
        return easyNewWord;
    }

    public void setEasyNewWord(String easyNewWord) {
        this.easyNewWord = easyNewWord;
    }

    public String getMediumNewWord() {
        return mediumNewWord;
    }

    public void setMediumNewWord(String mediumNewWord) {
        this.mediumNewWord = mediumNewWord;
    }

    public String getHardNewWord() {
        return hardNewWord;
    }

    public void setHardNewWord(String hardNewWord) {
        this.hardNewWord = hardNewWord;
    }

    public String getFakeEasyword() {
        return fakeEasyword;
    }

    public void setFakeEasyword(String fakeEasyword) {
        this.fakeEasyword = fakeEasyword;
    }

    public String getFakeMediumword() {
        return fakeMediumword;
    }

    public void setFakeMediumword(String fakeMediumword) {
        this.fakeMediumword = fakeMediumword;
    }

    public String getFakeHardword() {
        return fakeHardword;
    }

    public void setFakeHardword(String fakeHardword) {
        this.fakeHardword = fakeHardword;
    }
    
    /**
     * This method adds an easy word and its corresponding distraction word in the db
     */
    public void onClickEasy(){
        easyWord.setEasyWords(this.getEasyNewWord().toLowerCase());
        easyWord.setFakeEasyWords(this.getFakeEasyword().toLowerCase());
        easywordsFacade.create(easyWord);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlg').show();");
    }
    /**
     * This method adds an medium word and its corresponding distraction word in the db
     */
    public void onClickMedium(){
        mediumWord.setMediumWords(this.getMediumNewWord().toLowerCase());
        mediumWord.setFakeMediumWords(this.getFakeMediumword().toLowerCase());
        mediumwordsFacade.create(mediumWord);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlg2').show();");
    }
    
    /**
     * This method adds an hard word and its corresponding distraction word in the db
     */
    public void onClickHard(){
        hardWord.setHardWords(this.getHardNewWord().toLowerCase());
        hardWord.setFakeHardWords(this.getFakeHardword().toLowerCase());
        hardwordsFacade.create(hardWord);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlg3').show();");
    }
    
}
