/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbRole;
import entities.DbUser;
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
public class DbRoleFacade extends AbstractFacade<DbRole> implements DbRoleFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbRoleFacade() {
        super(DbRole.class);
    }

    @Override
    public List<DbRole> listActiveRoles() {
        List<DbRole> resultList = new ArrayList<>();
        String querySTR;
        try {
            querySTR = "FROM DbRole r WHERE r.roleStatus = 1";
            Query query = em.createQuery(querySTR);
            resultList = query.getResultList();
        } catch (Exception e) {
            throw e;
        }

        return resultList;
    }

    public List<DbUser> checkUser(Integer roleId) {
        return em.createQuery("FROM DbUser u WHERE u.roleId.roleId=:checkRoleId")
                .setParameter("checkRoleId", roleId)
                .setMaxResults(10)
                .getResultList();
    }

}
