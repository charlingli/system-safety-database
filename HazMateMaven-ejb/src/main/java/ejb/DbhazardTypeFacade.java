/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbHazard;
import entities.DbhazardType;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author alan8
 */
@Stateless
public class DbhazardTypeFacade extends AbstractFacade<DbhazardType> implements DbhazardTypeFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbhazardTypeFacade() {
        super(DbhazardType.class);
    }
    
    @Override
    public List<DbHazard> checkHazardType(int hazardTypeId)   {
        return em.createQuery("FROM DbHazard h WHERE h.hazardTypeId.hazardTypeId = :checkHazardTypeId")
                .setParameter("checkHazardTypeId", hazardTypeId)
                .setMaxResults(10)
                .getResultList(); 
    }
}
