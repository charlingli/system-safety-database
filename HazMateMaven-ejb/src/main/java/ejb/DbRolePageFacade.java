/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbRolePage;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Juan David
 */
@Stateless
public class DbRolePageFacade extends AbstractFacade<DbRolePage> implements DbRolePageFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbRolePageFacade() {
        super(DbRolePage.class);
    }

    @Override
    public List<DbRolePage> checkRolePage(Integer roleId, Integer pageId) {
        return em.createQuery("FROM DbRolePage i WHERE i.dbRole.roleId= :checkRoleId AND i.dbPage.pageId = :checkPageId")
                .setParameter("checkRoleId", roleId)
                .setParameter("checkPageId", pageId)
                .setMaxResults(10)
                .getResultList();
    }

}
