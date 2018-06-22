/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbHazard;
import entities.DbhazardStatus;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author alan8
 */
@Stateless
public class DbhazardStatusFacade extends AbstractFacade<DbhazardStatus> implements DbhazardStatusFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbhazardStatusFacade() {
        super(DbhazardStatus.class);
    }

    @Override
    public List<DbHazard> checkHazardStatus(int hazardStatusId) {
        return em.createQuery("FROM DbHazard h WHERE h.hazardStatusId.hazardStatusId = :checkHazardStatusId")
                .setParameter("checkHazardStatusId", hazardStatusId)
                .setMaxResults(10)
                .getResultList();
    }
}
