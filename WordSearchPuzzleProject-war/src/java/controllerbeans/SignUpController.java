/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerbeans;

import entities.Player;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.context.RequestContext;
import session.PlayerFacade;

/**
 *
 * @author rkmanglani2018
 */
@ManagedBean
@RequestScoped
public class SignUpController {

    @EJB
    private PlayerFacade playerFacade;
    
    
    private Player newPlayer;
    
    
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String password;
    private String repeatPassword;
    /**
     * Creates a new instance of SignUpController
     */
    public SignUpController() {
        playerFacade = new PlayerFacade();
        newPlayer = new Player();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
    
    public void onClickSignUp(){
        if(this.getPassword().equals(this.getRepeatPassword())){
            newPlayer.setEmailAddress(this.getEmail().toLowerCase());
            newPlayer.setFirstName(this.getFirstName());
            newPlayer.setLastName(this.getLastName());
            newPlayer.setGender(this.getGender());
            newPlayer.setPassword(this.getPassword());
            newPlayer.setEasyWordsSolved(0);
            newPlayer.setEasyWordsGiven(0);
            newPlayer.setEasyPercentage(0.0);
            newPlayer.setMediumWordsSolved(0);
            newPlayer.setMediumWordsGiven(0);
            newPlayer.setMediumPercentage(0.0);
            newPlayer.setHardWordsSolved(0);
            newPlayer.setHardWordsGiven(0);
            newPlayer.setHardPercentage(0.0);
            playerFacade.create(newPlayer);
            
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlgSignup').show();");
        }else{
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlgSignupfail').show();");
        }
    }
    
}
