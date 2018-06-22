/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbCause;
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
public class DbCauseFacade extends AbstractFacade<DbCause> implements DbCauseFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbCauseFacade() {
        super(DbCause.class);
    }

    @Override
    public List<DbCause> findUnrelatedByHazardId(String hazardId) {
        String querySTR;
        List<DbCause> resultantList = new ArrayList<>();
        try {
            querySTR = "SELECT Cau FROM DbCause Cau "
                    + "WHERE NOT EXISTS "
                    + "(SELECT 'X' FROM DbHazardCause HCau "
                    + "WHERE HCau.dbHazardCausePK.causeId = Cau.causeId "
                    + "AND HCau.dbHazardCausePK.hazardId = ?1)";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, hazardId);

            resultantList = query.getResultList();

        } catch (Exception e) {
            throw e;
        }
        
        return resultantList;
    }

}
