/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbimportLineError;
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
public class DbimportLineErrorFacade extends AbstractFacade<DbimportLineError> implements DbimportLineErrorFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbimportLineErrorFacade() {
        super(DbimportLineError.class);
    }
    
    @Override
    public List<DbimportLineError> listErrorsByLine(String processId, int processIdLine) {
        List<DbimportLineError> resultantList = new ArrayList<>();
        String queryString;
        try {
            queryString = "FROM DbimportLineError e WHERE e.dbimportLineErrorPK.processId = ?1 "
                    + "AND e.dbimportLineErrorPK.processIdLine = ?2 AND e.processErrorStatus = 'P'";
            Query query = em.createQuery(queryString);
            query.setParameter(1, processId);
            query.setParameter(2, processIdLine);
            
            resultantList = query.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return resultantList;
    }
    
    @Override
    public List<DbimportLineError> findErrorByCell(String processId, int processIdLine, String errorLocation) {
        List<DbimportLineError> resultantList = new ArrayList<>();
        String queryString;
        try {
            queryString = "FROM DbimportLineError e WHERE e.dbimportLineErrorPK.processId = ?1 "
                    + "AND e.dbimportLineErrorPK.processIdLine = ?2 "
                    + "AND e.processErrorLocation = ?3";
            Query query = em.createQuery(queryString);
            query.setParameter(1, processId);
            query.setParameter(2, processIdLine);
            query.setParameter(3, errorLocation);
            
            resultantList = query.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return resultantList;
    }
    
}
