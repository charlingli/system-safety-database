/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbHazard;
import entities.DbriskSeverity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author lxra
 */
@Stateless
public class DbriskSeverityFacade extends AbstractFacade<DbriskSeverity> implements DbriskSeverityFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbriskSeverityFacade() {
        super(DbriskSeverity.class);
    }
    
    @Override
    public List<DbHazard> checkRiskSeverity(int riskSeverityId)   {
        return em.createQuery("FROM DbHazard h WHERE h.riskSeverityId.riskSeverityId = :checkRiskSeverityId")
                .setParameter("checkRiskSeverityId", riskSeverityId)
                .setMaxResults(10)
                .getResultList(); 
    }
    
    @Override
    public List<DbriskSeverity> getRiskSeverity(int riskSeverityId) {
        return em.createQuery("FROM DbriskSeverity r WHERE r.riskSeverityId = :checkRiskSeverityId")
                .setParameter("checkRiskSeverityId", riskSeverityId)
                .getResultList();
    }
}
