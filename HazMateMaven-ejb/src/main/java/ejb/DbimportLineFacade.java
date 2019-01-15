/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbimportLine;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author lxra
 */
@Stateless
public class DbimportLineFacade extends AbstractFacade<DbimportLine> implements DbimportLineFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbimportLineFacade() {
        super(DbimportLine.class);
    }
    
    @Override
    public List<DbimportLine> findLineById(String processId, int processIdLine) {
        List<DbimportLine> resultantList = new ArrayList<>();
        
        String querySTR;
        try {
            querySTR = "SELECT l FROM DbimportLine l "
                    + "WHERE l.dbimportLinePK.processId = ?1 "
                    + "AND l.dbimportLinePK.processIdLine = ?2";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, processId);
            query.setParameter(2, processIdLine);
            
            resultantList = query.getResultList();
        } catch (Exception e) {
            throw e;
        }

        return resultantList;
    }
    
    @Override
    public List<DbimportLine> findNextLinesByUser(int userId) {
        List<DbimportLine> resultantList = new ArrayList<>();
        
        String querySTR;
        try {
            querySTR = "SELECT l FROM DbimportLine l WHERE EXISTS (SELECT 'x' "
                    + "FROM DbimportHeader h WHERE h.userId = ?1 "
                    + "AND h.processStatus = 'P' AND h.processId = l.dbimportLinePK.processId)";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, userId);
            
            resultantList = query.getResultList();
        } catch (Exception e) {
            throw e;
        }

        return resultantList;
    }
}
