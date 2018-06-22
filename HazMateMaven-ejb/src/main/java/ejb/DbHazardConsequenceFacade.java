/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbHazardConsequence;
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
public class DbHazardConsequenceFacade extends AbstractFacade<DbHazardConsequence> implements DbHazardConsequenceFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbHazardConsequenceFacade() {
        super(DbHazardConsequence.class);
    }

    @Override
    public List<DbHazardConsequence> findByHazardId(String hazardId) {
        String querySTR;
        List<DbHazardConsequence> resultantList = new ArrayList<>();
        try {
            querySTR = "FROM DbHazardConsequence HCoq WHERE HCoq.dbHazard.hazardId = ?1";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, hazardId);

            resultantList = query.getResultList();

        } catch (Exception e) {
            throw e;
        }
        return resultantList;
    }

    @Override
    public int customRemove(DbHazardConsequence dbHazardConsequence) {
        String querySTR;
        int rows = 0;
        try {
            querySTR = "DELETE FROM DbHazardConsequence HCoq WHERE HCoq.dbHazard.hazardId = ?1 "
                    + "AND HCoq.dbConsequence.consequenceId = ?2 ";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, dbHazardConsequence.getDbHazard().getHazardId());
            query.setParameter(2, dbHazardConsequence.getDbConsequence().getConsequenceId());

            rows = query.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
        return rows;
    }

}
