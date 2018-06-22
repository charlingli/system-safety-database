/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbuserPreferences;
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
public class DbuserPreferencesFacade extends AbstractFacade<DbuserPreferences> implements DbuserPreferencesFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbuserPreferencesFacade() {
        super(DbuserPreferences.class);
    }

    @Override
    public List<DbuserPreferences> getUserPreferences(int userId) {
        String querySTR;
        List<DbuserPreferences> resultList = new ArrayList<>();
        try {
            querySTR = "SELECT p FROM DbUser u, DbuserPreferences p "
                    + "WHERE u.userId = p.dbuserPreferencesPK.userId AND u.userId = ?1";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, userId);

            resultList = query.getResultList();

        } catch (Exception e) {
            throw e;
        }
        return resultList;
    }

    @Override
    public List<DbuserPreferences> getSpecificPreference(int userId, String pageName, String tableName) {
        String querySTR;
        List<DbuserPreferences> resultList = new ArrayList<>();
        try {
            querySTR = "SELECT p FROM DbUser u, DbuserPreferences p "
                    + "WHERE u.userId = p.dbuserPreferencesPK.userId AND u.userId = ?1 "
                    + "AND p.dbuserPreferencesPK.pageName = :checkPageName "
                    + "AND p.dbuserPreferencesPK.tableName = :checkTableName ";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, userId);
            query.setParameter("checkPageName", pageName);
            query.setParameter("checkTableName", tableName);

            resultList = query.getResultList();

        } catch (Exception e) {
            throw e;
        }
        return resultList;
    }
}
