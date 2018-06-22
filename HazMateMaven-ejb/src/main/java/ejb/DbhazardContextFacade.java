/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbHazard;
import entities.DbhazardContext;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Juan David
 */
@Stateless
public class DbhazardContextFacade extends AbstractFacade<DbhazardContext> implements DbhazardContextFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbhazardContextFacade() {
        super(DbhazardContext.class);
    }

    @Override
    public List<DbHazard> checkHazardContext(int hazardContextId) {
        return em.createQuery("FROM DbHazard h WHERE h.hazardContextId.hazardContextId = :checkHazardContextId")
                .setParameter("checkHazardContextId", hazardContextId)
                .setMaxResults(10)
                .getResultList();
    }

}
