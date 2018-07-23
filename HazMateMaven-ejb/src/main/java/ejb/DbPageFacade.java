/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbPage;
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
public class DbPageFacade extends AbstractFacade<DbPage> implements DbPageFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbPageFacade() {
        super(DbPage.class);
    }

    @Override
    public List<DbPage> findAllSortedByInx() {
        String queryStr;
        List<DbPage> resultList = new ArrayList<>();
        try {
            queryStr = "SELECT p FROM DbPage p ORDER BY p.indexPage";
            Query query = em.createQuery(queryStr);
            resultList = query.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return resultList;
    }

    @Override
    public DbPage retrievePageMatch(int pageIndex) {
        String queryStr;
        List<DbPage> resultList = new ArrayList<>();
        DbPage resultPage = new DbPage();

        try {
            queryStr = "FROM DbPage p WHERE p.indexPage = ?1";
            Query query = em.createQuery(queryStr);
            query.setParameter(1, pageIndex);

            resultList = query.getResultList();

            if (!resultList.isEmpty()) {
                resultPage = resultList.get(0);
            }

        } catch (Exception e) {

            throw e;
        }
        return resultPage;
    }

    @Override
    public DbPage retrievePageName(String pageLoc) {
        String queryStr;
        List<DbPage> resultList = new ArrayList<>();
        DbPage resultPage = new DbPage();

        try {
            queryStr = "FROM DbPage p WHERE p.pageLocation = ?1";
            Query query = em.createQuery(queryStr);
            query.setParameter(1, pageLoc);

            resultList = query.getResultList();

            if (!resultList.isEmpty()) {
                resultPage = resultList.get(0);
            }

        } catch (Exception e) {

            throw e;
        }
        return resultPage;
    }

}
