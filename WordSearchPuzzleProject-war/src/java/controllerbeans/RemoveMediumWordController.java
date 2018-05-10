/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerbeans;

import entities.Mediumwords;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import session.MediumwordsFacade;

/**
 *
 * @author rkmanglani2018
 */
@ManagedBean
@RequestScoped
public class RemoveMediumWordController {

   @EJB
    private MediumwordsFacade mediumwordsFacade;
    
    private List<Mediumwords> mediumWords;
    /**
     * Creates a new instance of RemoveMediumWordController
     */
    public RemoveMediumWordController() {
        mediumwordsFacade = new MediumwordsFacade();
        mediumWords = new ArrayList<Mediumwords>();
    }

    public List<Mediumwords> getMediumWords() {
        mediumWords = mediumwordsFacade.findAll();
        return mediumWords;
    }

    public void setMediumWords(List<Mediumwords> mediumWords) {
        this.mediumWords = mediumWords;
    }
    
    public void onClickRemove(Mediumwords word) throws IOException {
        mediumwordsFacade.remove(word);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
    
}
