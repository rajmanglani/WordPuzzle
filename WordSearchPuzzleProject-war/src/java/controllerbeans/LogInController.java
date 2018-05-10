/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerbeans;

import entities.Player;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import session.PlayerFacade;

/**
 *
 * @author rkmanglani2018
 */
@ManagedBean
@SessionScoped
public class LogInController {
@EJB
    private PlayerFacade playerFacade;
    
    private Player player;
    
    private String loginEmail;
    private String loginPassword;
    
    /**
     * Creates a new instance of LogInController
     */
    public LogInController() {
        playerFacade = new PlayerFacade();
        player = new Player();
    }

    public String getLoginEmail() {
        return loginEmail;
    }

    public void setLoginEmail(String loginEmail) {
        this.loginEmail = loginEmail;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    
    
    public String onClickLogin(){
        
        if(this.getLoginEmail().toLowerCase().equals("admin")){
            player = playerFacade.find(this.getLoginEmail().toLowerCase());
            if(player.getPassword().equals(this.getLoginPassword())){
                return "AdminAddWords.xhtml";
            }
        }
        
        player = playerFacade.find(this.getLoginEmail().toLowerCase());
        if(player.getPassword().equals(this.getLoginPassword())){
            return "UserHomePage.xhtml";
        }
        
        return "Error.xhtml";
    }
    
}
