/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbwfDecision;
import entities.DbwfLine;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author lxra
 */
@Stateless
public class DbwfDecisionFacade extends AbstractFacade<DbwfDecision> implements DbwfDecisionFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbwfDecisionFacade() {
        super(DbwfDecision.class);
    }
    
    @Override
    public List<DbwfLine> checkWfDecision(String wfDecisionId)   {
        return em.createQuery("FROM DbwfLine l WHERE l.wfApproverDecisionId.wfDecisionId = :checkwfDecisionId")
                .setParameter("checkwfDecisionId", wfDecisionId)
                .setMaxResults(10)
                .getResultList(); 
    }
    
}
