/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbLocation;
import entities.DbgradeSeparation;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author alan8
 */
@Stateless
public class DbgradeSeparationFacade extends AbstractFacade<DbgradeSeparation> implements DbgradeSeparationFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbgradeSeparationFacade() {
        super(DbgradeSeparation.class);
    }

    @Override
    public List<DbLocation> checkGradeSeparation(int gradeSeparationId) {
        return em.createQuery("FROM DbLocation l WHERE l.locationGradeSeparation.gradeSeparationId = :checkGradeSeparationId")
                .setParameter("checkGradeSeparationId", gradeSeparationId)
                .setMaxResults(10)
                .getResultList();
    }
}
