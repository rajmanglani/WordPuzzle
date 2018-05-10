/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerbeans;

import entities.Player;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import org.primefaces.context.RequestContext;
import session.PlayerFacade;

/**
 *
 * @author rkmanglani2018
 */
@ManagedBean
@RequestScoped
public class UserHomePageController {

    @ManagedProperty(value="#{logInController}")
    private LogInController loginController;
    
    @EJB
    private PlayerFacade playerFacade;
    
    private String fName;
    private String lName;
    private Player p;
    private int EasyFound;
    private int EasyGiven;
    private Double EasyP;
    private int MediumFound;
    private int MediumGiven;
    private Double MediumP;
    private int HardFound;
    private int HardGiven;
    private Double HardP;
    /**
     * Creates a new instance of UserHomePageController
     */
    public UserHomePageController() {
        playerFacade = new PlayerFacade();
    }

    @PostConstruct
    private void init(){
        EasyFound = loginController.getPlayer().getEasyWordsSolved();
        EasyGiven = loginController.getPlayer().getEasyWordsGiven();
        EasyP = loginController.getPlayer().getEasyPercentage();
        MediumFound = loginController.getPlayer().getMediumWordsSolved();
        MediumGiven = loginController.getPlayer().getMediumWordsGiven();
        MediumP = loginController.getPlayer().getMediumPercentage();
        HardFound = loginController.getPlayer().getHardWordsSolved();
        HardGiven = loginController.getPlayer().getHardWordsGiven();
        HardP = loginController.getPlayer().getHardPercentage();
    }
    public void setLoginController(LogInController loginController) {
        this.loginController = loginController;
    }

    public String getfName() {
        fName = loginController.getPlayer().getFirstName().toUpperCase();
        
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        lName = loginController.getPlayer().getLastName().toUpperCase();
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public Player getP() {
        p = loginController.getPlayer();
        return p;
    }

    public void setP(Player p) {
        this.p = p;
    }

    public int getEasyFound() {
        return EasyFound;
    }

    public void setEasyFound(int EasyFound) {
        this.EasyFound = EasyFound;
    }

    public int getEasyGiven() {
        return EasyGiven;
    }

    public void setEasyGiven(int EasyGiven) {
        this.EasyGiven = EasyGiven;
    }

   

    public int getMediumFound() {
        return MediumFound;
    }

    public void setMediumFound(int MediumFound) {
        this.MediumFound = MediumFound;
    }

    public int getMediumGiven() {
        return MediumGiven;
    }

    public void setMediumGiven(int MediumGiven) {
        this.MediumGiven = MediumGiven;
    }

    public int getHardFound() {
        return HardFound;
    }

    public void setHardFound(int HardFound) {
        this.HardFound = HardFound;
    }

    public int getHardGiven() {
        return HardGiven;
    }

    public void setHardGiven(int HardGiven) {
        this.HardGiven = HardGiven;
    }

    public Double getEasyP() {
        return EasyP;
    }

    public void setEasyP(Double EasyP) {
        this.EasyP = EasyP;
    }

    public Double getMediumP() {
        return MediumP;
    }

    public void setMediumP(Double MediumP) {
        this.MediumP = MediumP;
    }

    public Double getHardP() {
        return HardP;
    }

    public void setHardP(Double HardP) {
        this.HardP = HardP;
    }

    
    public void onClickStats(){
        
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlgStats').show();");
        
    }
    
    
    public String onClickEasy(){
        return "EasyLevelPuzzle.xhtml";
    }
    
    public void resetEasy(){
        loginController.getPlayer().setEasyWordsGiven(0);
        loginController.getPlayer().setEasyWordsSolved(0);
        loginController.getPlayer().setEasyPercentage(0.0);
        playerFacade.edit(loginController.getPlayer());
    }
    
    public void resetMedium(){
        loginController.getPlayer().setMediumWordsGiven(0);
        loginController.getPlayer().setMediumWordsSolved(0);
        loginController.getPlayer().setMediumPercentage(0.0);
        playerFacade.edit(loginController.getPlayer());
    }
    
    public void resetHard(){
        loginController.getPlayer().setHardWordsGiven(0);
        loginController.getPlayer().setHardWordsSolved(0);
        loginController.getPlayer().setHardPercentage(0.0);
        playerFacade.edit(loginController.getPlayer());
    }
    
}
