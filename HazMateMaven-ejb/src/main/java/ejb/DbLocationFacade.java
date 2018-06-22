/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbHazard;
import entities.DbLocation;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author alan8
 */
@Stateless
public class DbLocationFacade extends AbstractFacade<DbLocation> implements DbLocationFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbLocationFacade() {
        super(DbLocation.class);
    }

    @Override
    public List<DbHazard> checkLocation(int locationId) {
        return em.createQuery("FROM DbHazard h WHERE h.hazardLocation.locationId = :checkLocationId")
                .setParameter("checkLocationId", locationId)
                .setMaxResults(10)
                .getResultList();
    }

    @Override
    public List<DbLocation> getLocationAbbrev(int locationId) {
        return em.createQuery("FROM DbLocation l WHERE l.locationId = :checkLocationId")
                .setParameter("checkLocationId", locationId)
                .getResultList();
    }

    @Override
    public List<DbLocation> checkLocationAbbrev(String locationAbbrev) {
        return em.createQuery("FROM DbLocation l WHERE l.locationAbbrev = :checkLocationAbbrev")
                .setParameter("checkLocationAbbrev", locationAbbrev)
                .getResultList();
    }

}
