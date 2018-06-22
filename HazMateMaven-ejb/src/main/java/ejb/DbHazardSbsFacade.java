/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbHazardSbs;
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
public class DbHazardSbsFacade extends AbstractFacade<DbHazardSbs> implements DbHazardSbsFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbHazardSbsFacade() {
        super(DbHazardSbs.class);
    }

    @Override
    public List<DbHazardSbs> findByHazardId(String hazardId) {
        String querySTR;
        List<DbHazardSbs> resultantList = new ArrayList<>();
        try {
            querySTR = "FROM DbHazardSbs HS WHERE HS.dbHazardSbsPK.hazardId = ?1";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, hazardId);

            resultantList = query.getResultList();

        } catch (Exception e) {
            throw e;
        }
        return resultantList;
    }

    @Override
    public void removeHazardSbs(String hazardId) {

        String querySTR;
        int rows;

        try {
            querySTR = "DELETE FROM DbHazardSbs s WHERE s.dbHazardSbsPK.hazardId = :checkHazardId";

            Query query = em.createQuery(querySTR)
                    .setParameter("checkHazardId", hazardId);
            rows = query.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<DbHazardSbs> checkHazardSbs(String hazardId, String sbsId) {
        String querySTR;
        List<DbHazardSbs> resultantList = new ArrayList();

        try {
            querySTR = "FROM DbHazardSbs s "
                    + "WHERE s.dbHazardSbsPK.hazardId = :checkHazardId "
                    + "AND s.dbHazardSbsPK.sbsId = :checkSbsId";
            Query query = em.createQuery(querySTR)
                    .setParameter("checkHazardId", hazardId)
                    .setParameter("checkSbsId", sbsId);
            resultantList = query.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return resultantList;
    }

}
