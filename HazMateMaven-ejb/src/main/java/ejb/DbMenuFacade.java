/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbMenu;
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
public class DbMenuFacade extends AbstractFacade<DbMenu> implements DbMenuFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbMenuFacade() {
        super(DbMenu.class);
    }

    @Override
    public List<DbPage> customUserMenu(int userId) {
        String querySTR;
        List<DbPage> resultList = new ArrayList<>();
        try {
            querySTR = "SELECT p FROM DbUser u, DbRole r, DbRolePage rp, DbPage p "
                    + "WHERE u.roleId = r.roleId "
                    + "AND r.roleStatus = 1 "
                    + "AND r.roleId = rp.dbRole.roleId "
                    + "AND rp.dbPage.pageId = p.pageId "
                    + "AND u.userId = ?1";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, userId);

            resultList = query.getResultList();

        } catch (Exception e) {
            throw e;
        }
        return resultList;
    }

    @Override
    public DbMenu findOneMenu(int menuId) {
        String querySTR;
        List<DbMenu> resultList = new ArrayList<>();
        DbMenu resultMenu = new DbMenu();
        try {
            querySTR = "FROM DbMenu m WHERE m.menuId = ?1";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, menuId);

            resultList = query.getResultList();

            if (!resultList.isEmpty()) {
                resultMenu = resultList.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return resultMenu;
    }

    @Override
    public List<DbMenu> findAllMainMenus() {
        String querySTR;
        List<DbMenu> resultList = new ArrayList<>();
        try {
            querySTR = "FROM DbMenu m WHERE m.menuType = 'M'";
            Query query = em.createQuery(querySTR);

            resultList = query.getResultList();

        } catch (Exception e) {
            throw e;
        }
        return resultList;
    }

    @Override
    public DbMenu findOneMenuByIndex(int indexMenu) {
        String querySTR;
        List<DbMenu> resultList = new ArrayList<>();
        DbMenu resultMenu = new DbMenu();
        try {
            querySTR = "FROM DbMenu m WHERE m.indexMenu = ?1";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, indexMenu);

            resultList = query.getResultList();

            if (!resultList.isEmpty()) {
                resultMenu = resultList.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return resultMenu;
    }

    @Override
    public List<DbMenu> findMenusByParent(int menuParent) {
        String querySTR;
        List<DbMenu> resultList = new ArrayList<>();
        try {
            querySTR = "FROM DbMenu m WHERE m.parentMenu = ?1";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, menuParent);

            resultList = query.getResultList();

        } catch (Exception e) {
            throw e;
        }

        return resultList;
    }

    @Override
    public List<DbMenu> findPagesByParent(int pageParent) {
        String querySTR;
        List<DbMenu> resultList = new ArrayList<>();
        try {
            querySTR = "SELECT m FROM DbMenu m, DbPage p WHERE m.menuId = p.menuId AND p.menuId.menuId = ?1";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, pageParent);

            resultList = query.getResultList();

        } catch (Exception e) {
            throw e;
        }

        return resultList;
    }

}
