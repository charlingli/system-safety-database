/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import customObjects.fileHeaderObject;
import entities.DbHazardFiles;
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
public class DbHazardFilesFacade extends AbstractFacade<DbHazardFiles> implements DbHazardFilesFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbHazardFilesFacade() {
        super(DbHazardFiles.class);
    }
    
    @Override
    public List<fileHeaderObject> findHeadersForHazard(String hazardId) {
        String querySTR;
        List<fileHeaderObject> resultantList = new ArrayList<>();
        try {
            querySTR = "SELECT new customObjects.fileHeaderObject"
                    + "(f.fileId, f.fileName, f.fileExtension, f.fileSize, f.fileDescription) "
                    + "FROM DbFiles f WHERE EXISTS (SELECT 'x' FROM DbHazardFiles h "
                    + "WHERE h.dbHazardFilesPK.hazardId = ?1 "
                    + "AND h.dbHazardFilesPK.fileId = f.fileId)"; // Change the f to link from files to hazard_files
            Query query = em.createQuery(querySTR);
            query.setParameter(1, hazardId);
            
            resultantList = query.getResultList();

        } catch (Exception e) {
            throw e;
        }
        
        return resultantList;
    }
    
    @Override
    public int customRemove(String hazardId, int fileId) {
        String querySTR;
        int rows = 0;
        try {
            querySTR = "DELETE FROM DbHazardFiles hf WHERE hf.dbHazardFilesPK.hazardId = ?1 "
                    + "AND hf.dbHazardFilesPK.fileId = ?2 ";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, hazardId);
            query.setParameter(2, fileId);

            rows = query.executeUpdate();
            
        } catch (Exception e) {
            throw e;
        }
        return rows;        
    }
    
    @Override
    public boolean hasHazardsLinked(int fileId) {
        String querySTR;
        List<DbHazardFiles> resultantList;
        try {
            querySTR = "SELECT hf FROM DbHazardFiles hf WHERE hf.dbHazardFilesPK.fileId = ?1";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, fileId);
            
            resultantList = query.getResultList();
            
        } catch (Exception e) {
            throw e;
        }
        return !resultantList.isEmpty();
    }
}
