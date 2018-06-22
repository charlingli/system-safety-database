/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbHazard;
import entities.DbhazardActivity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author alan8
 */
@Stateless
public class DbhazardActivityFacade extends AbstractFacade<DbhazardActivity> implements DbhazardActivityFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbhazardActivityFacade() {
        super(DbhazardActivity.class);
    }
    
    @Override
    public List<DbHazard> checkHazardActivity(int hazardActivityId)   {
        return em.createQuery("FROM DbHazard h WHERE h.hazardActivity.activityId = :checkHazardActivityId")
                .setParameter("checkHazardActivityId", hazardActivityId)
                .setMaxResults(10)
                .getResultList(); 
    }
    
}
