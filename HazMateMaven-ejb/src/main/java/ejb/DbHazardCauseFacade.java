/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbHazardCause;
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
public class DbHazardCauseFacade extends AbstractFacade<DbHazardCause> implements DbHazardCauseFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbHazardCauseFacade() {
        super(DbHazardCause.class);
    }

    @Override
    public List<DbHazardCause> findByHazardId(String hazardId) {
        String querySTR;
        List<DbHazardCause> resultantList = new ArrayList<>();
        try {
            querySTR = "FROM DbHazardCause HCau WHERE HCau.dbHazard.hazardId = ?1";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, hazardId);

            resultantList = query.getResultList();

        } catch (Exception e) {
            throw e;
        }
        return resultantList;
    }

    @Override
    public int customRemove(DbHazardCause dbHazardCause) {
        String querySTR;
        int rows = 0;
        try {
            querySTR = "DELETE FROM DbHazardCause HCau WHERE HCau.dbHazard.hazardId = ?1 "
                    + "AND HCau.dbCause.causeId = ?2 ";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, dbHazardCause.getDbHazard().getHazardId());
            query.setParameter(2, dbHazardCause.getDbCause().getCauseId());

            rows = query.executeUpdate();
            
        } catch (Exception e) {
            throw e;
        }
        return rows;        
    }

}
