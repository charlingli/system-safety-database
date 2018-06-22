/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbConsequence;
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
public class DbConsequenceFacade extends AbstractFacade<DbConsequence> implements DbConsequenceFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbConsequenceFacade() {
        super(DbConsequence.class);
    }

    @Override
    public List<DbConsequence> findUnrelatedByHazardId(String hazardId) {
        String querySTR;
        List<DbConsequence> resultantList = new ArrayList<>();
        try {
            querySTR = "SELECT Coq FROM DbConsequence Coq "
                    + "WHERE NOT EXISTS "
                    + "(SELECT 'X' FROM DbHazardConsequence HCoq "
                    + "WHERE HCoq.dbHazardConsequencePK.consequenceId = Coq.consequenceId "
                    + "AND HCoq.dbHazardConsequencePK.hazardId = ?1)";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, hazardId);

            resultantList = query.getResultList();

        } catch (Exception e) {
            throw e;
        }
        return resultantList;
    }

}
