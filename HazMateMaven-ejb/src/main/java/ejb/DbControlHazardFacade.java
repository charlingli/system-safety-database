/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbControlHazard;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Juan David
 */
@Stateless
public class DbControlHazardFacade extends AbstractFacade<DbControlHazard> implements DbControlHazardFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbControlHazardFacade() {
        super(DbControlHazard.class);
    }

    @Override
    public List<DbControlHazard> findByHazardId(String hazardId) {
        String querySTR;
        List<DbControlHazard> resultantList = new ArrayList<>();
        try {
            querySTR = "FROM DbControlHazard HCtl WHERE HCtl.dbHazard.hazardId = ?1";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, hazardId);

            resultantList = query.getResultList();

        } catch (Exception e) {
            throw e;
        }
        return resultantList;
    }

    @Override
    public int customRemove(DbControlHazard dbControlHazard) {
        String querySTR;
        int rows = 0;
        try {
            querySTR = "DELETE FROM DbControlHazard HCtl WHERE HCtl.dbHazard.hazardId = ?1 "
                    + "AND HCtl.dbControl.controlId = ?2 ";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, dbControlHazard.getDbControlHazardPK().getHazardId());
            query.setParameter(2, dbControlHazard.getDbControlHazardPK().getControlId());

            rows = query.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
        return rows;
    }

}
