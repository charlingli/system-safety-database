/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbHazard;
import entities.DbriskFrequency;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author lxra
 */
@Stateless
public class DbriskFrequencyFacade extends AbstractFacade<DbriskFrequency> implements DbriskFrequencyFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbriskFrequencyFacade() {
        super(DbriskFrequency.class);
    }
    
    @Override
    public List<DbHazard> checkRiskFrequency(int riskFrequencyId)   {
        return em.createQuery("FROM DbHazard h WHERE h.riskFrequencyId.riskFrequencyId = :checkRiskFrequencyId")
                .setParameter("checkRiskFrequencyId", riskFrequencyId)
                .setMaxResults(10)
                .getResultList(); 
    }
    
    @Override
    public List<DbriskFrequency> getRiskFrequency(int riskFrequencyId) {
        return em.createQuery("FROM DbriskFrequency r WHERE r.riskFrequencyId = :checkRiskFrequencyId")
                .setParameter("checkRiskFrequencyId", riskFrequencyId)
                .getResultList();
    }
}
