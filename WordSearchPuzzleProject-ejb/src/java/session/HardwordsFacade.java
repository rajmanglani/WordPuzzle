/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entities.Hardwords;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author rkmanglani2018
 */
@Stateless
public class HardwordsFacade extends AbstractFacade<Hardwords> {
    @PersistenceContext(unitName = "WordSearchPuzzleProject-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HardwordsFacade() {
        super(Hardwords.class);
    }
    
}
