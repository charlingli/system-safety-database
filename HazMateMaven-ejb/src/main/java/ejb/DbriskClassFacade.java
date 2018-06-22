/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbHazard;
import entities.DbriskClass;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author alan8
 */
@Stateless
public class DbriskClassFacade extends AbstractFacade<DbriskClass> implements DbriskClassFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbriskClassFacade() {
        super(DbriskClass.class);
    }
    
    @Override
    public List<DbHazard> checkRiskClass(int riskClassId)   {
        return em.createQuery("FROM DbHazard h WHERE h.riskClassId.riskClassId = :checkRiskClassId")
                .setParameter("checkRiskClassId", riskClassId)
                .setMaxResults(10)
                .getResultList(); 
    }
}
