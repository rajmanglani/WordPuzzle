/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerbeans;

import entities.Hardwords;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import session.HardwordsFacade;

/**
 *
 * @author rkmanglani2018
 */
@ManagedBean
@RequestScoped
public class RemoveHardWordController {

   @EJB
    private HardwordsFacade hardwordsFacade;
    
    private List<Hardwords> hardWords;
    /**
     * Creates a new instance of RemoveMediumWordController
     */
    public RemoveHardWordController() {
        hardwordsFacade = new HardwordsFacade();
        hardWords = new ArrayList<Hardwords>();
    }

    public List<Hardwords> getHardWords() {
        hardWords = hardwordsFacade.findAll();
        return hardWords;
    }

    public void setHardWords(List<Hardwords> hardWords) {
        this.hardWords = hardWords;
    }
    
    public void onClickRemove(Hardwords word) throws IOException {
        hardwordsFacade.remove(word);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
    
}
