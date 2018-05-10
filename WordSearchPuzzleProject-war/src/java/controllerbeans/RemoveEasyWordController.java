/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerbeans;

import entities.Easywords;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import session.EasywordsFacade;

/**
 *
 * @author rkmanglani2018
 */
@ManagedBean
@RequestScoped
public class RemoveEasyWordController {

    @EJB
    private EasywordsFacade easywordsFacade;
    
    private List<Easywords> easyWords;
    /**
     * Creates a new instance of RemoveEasyWordController
     */
    public RemoveEasyWordController() {
        easywordsFacade = new EasywordsFacade();
        easyWords = new ArrayList<Easywords>();
    }
    
    public List<Easywords> getEasyWords() {
        easyWords = easywordsFacade.findAll();
        return easyWords;
    }

    public void setEasyWords(List<Easywords> easyWords) {
        this.easyWords = easyWords;
    }
    
    public void onClickRemove(Easywords word) throws IOException{
        easywordsFacade.remove(word);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
         ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
    
}
