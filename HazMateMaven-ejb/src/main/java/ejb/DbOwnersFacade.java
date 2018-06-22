/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbControl;
import entities.DbHazard;
import entities.DbOwners;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author alan8
 */
@Stateless
public class DbOwnersFacade extends AbstractFacade<DbOwners> implements DbOwnersFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbOwnersFacade() {
        super(DbOwners.class);
    }
    
    @Override
    public List<DbHazard> checkHazardOwners(int ownerId) {
        return em.createQuery("FROM DbHazard h WHERE h.ownerId.ownerId = :checkOwnerId")
                .setParameter("checkOwnerId", ownerId)
                .setMaxResults(10)
                .getResultList();
    }
    
    @Override
    public List<DbControl> checkControlOwners(int ownerId) {
        return em.createQuery("FROM DbControl c WHERE c.ownerId.ownerId = :checkOwnerId")
                .setParameter("checkOwnerId", ownerId)
                .setMaxResults(10)
                .getResultList();
    }
}
