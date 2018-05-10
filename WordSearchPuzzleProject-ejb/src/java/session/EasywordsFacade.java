/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entities.Easywords;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author rkmanglani2018
 */
@Stateless
public class EasywordsFacade extends AbstractFacade<Easywords> {
    @PersistenceContext(unitName = "WordSearchPuzzleProject-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EasywordsFacade() {
        super(Easywords.class);
    }
//    
//    public List<String> findOnlyFake(){
//        return em.createNamedQuery("Easywords.findOnlyFake").getResultList();
//    }
}
