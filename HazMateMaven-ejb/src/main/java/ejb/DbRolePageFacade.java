/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbRole;
import entities.DbRolePage;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Juan David
 */
@Stateless
public class DbRolePageFacade extends AbstractFacade<DbRolePage> implements DbRolePageFacadeLocal {

    @EJB
    private DbRoleFacadeLocal dbRoleFacade;

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
    public List<String> listPermissions() {
        List<String> resultantList = new ArrayList<>();
        try {
            List<DbRole> listRoles = dbRoleFacade.findAll();
            
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT CONCAT(p.pageId, ', ', ");
            for (DbRole role : listRoles) {
                int rNum = role.getRoleId();
                sb.append("IF(COUNT(rp").append(rNum).append(".roleId) > 0, 'true', 'false'), ', ', ");
            }
            sb.delete(sb.length() - 2, sb.length());
            sb.append(") FROM db_page p ");
            for (DbRole role : listRoles) {
                int rNum = role.getRoleId();
                sb.append("LEFT JOIN db_role_page rp")
                        .append(rNum).append(" ON rp").append(rNum)
                        .append(".pageId = p.pageId AND rp").append(rNum)
                        .append(".roleId = ").append(rNum).append(" ");
            }
            sb.append("GROUP BY p.pageId;");
            
            Query query = em.createNativeQuery(sb.toString());
            resultantList = query.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return resultantList;
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
