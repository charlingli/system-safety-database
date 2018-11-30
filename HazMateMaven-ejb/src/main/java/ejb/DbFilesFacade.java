/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import customObjects.fileHeaderObject;
import entities.DbFiles;
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
public class DbFilesFacade extends AbstractFacade<DbFiles> implements DbFilesFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbFilesFacade() {
        super(DbFiles.class);
    }
    
    @Override
    public List<DbFiles> findFileFromId(int fileId) {
        String querySTR;
        List<DbFiles> resultantList = new ArrayList<>();
        try {
            querySTR = "SELECT f FROM DbFiles f WHERE f.fileId = ?1";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, fileId);

            resultantList = query.getResultList();

        } catch (Exception e) {
            throw e;
        }
        
        return resultantList;
    }
    
    @Override
    public List<fileHeaderObject> findHeadersForDuplicate(String fileName, String fileExtension) {
        String querySTR;
        List<fileHeaderObject> resultantList = new ArrayList<>();
        try {
            querySTR = "SELECT new customObjects.fileHeaderObject"
                    + "(f.fileId, f.fileName, f.fileExtension, f.fileSize) "
                    + "FROM DbFiles f WHERE (f.fileName = ?1 "
                    + "AND f.fileExtension = ?2)";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, fileName);
            query.setParameter(2, fileExtension);

            resultantList = query.getResultList();

        } catch (Exception e) {
            throw e;
        }
        
        return resultantList;
    }
    
    @Override
    public List<fileHeaderObject> listAllHeaders() {
        String querySTR;
        List<fileHeaderObject> resultantList = new ArrayList<>();
        try {
            querySTR = "SELECT new customObjects.fileHeaderObject"
                    + "(f.fileId, f.fileName, f.fileExtension, f.fileSize) "
                    + "FROM DbFiles f";
            Query query = em.createQuery(querySTR);

            resultantList = query.getResultList();

        } catch (Exception e) {
            throw e;
        }
        
        return resultantList;
    }
    
}
