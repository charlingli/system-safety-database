/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

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
public class DbUserFacade extends AbstractFacade<DbUser> implements DbUserFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbUserFacade() {
        super(DbUser.class);
    }
    
    @Override
    public DbUser initSession(String userEmail, String password){
        DbUser loggedUser = null;
        String querySTR;
        List<DbUser> resultList = new ArrayList<>();
        try {
            querySTR = "FROM DbUser u WHERE u.userEmail = ?1 and u.password = ?2 and u.userStatus = 1";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, userEmail);
            query.setParameter(2, password);
            
            resultList = query.getResultList();
            if (!resultList.isEmpty()){
                loggedUser = resultList.get(0);                       
            }
            
        } catch (Exception e) {
            throw  e;
        }
        
        return loggedUser;     
    }
    
    @Override
    public List<DbUser> getUsersByRole(String roleName){
        String querySTR;
        List<DbUser> resultList = new ArrayList<>();
        try {
            querySTR = "FROM DbUser u WHERE u.roleId.roleName = ?1";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, roleName);
             
            resultList = query.getResultList();
            
        } catch (Exception e) {
            throw  e;
        }
        
        return resultList;     
    }
    
    @Override
    public boolean getPageAccessForUser(int userId, String pageLocation) {
        String querySTR;
        List<DbUser> resultList = new ArrayList<>();
        try {
            querySTR = "SELECT u FROM DbUser u, DbRolePage rp, DbPage p "
                    + "WHERE u.roleId.roleId = rp.dbRolePagePK.roleId "
                    + "AND rp.dbRolePagePK.pageId = p.pageId "
                    + "AND u.userId = ?1 "
                    + "AND p.pageLocation = ?2";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, userId);
            query.setParameter(2, pageLocation);
            
            resultList = query.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return resultList.size() > 0;
    }

    @Override
    public List<DbUser> getWfApproverUsers() {
                String querySTR;
        List<DbUser> resultList = new ArrayList<>();
        try {
            querySTR = "FROM DbUser u WHERE u.roleId.roleWFApprover = 'Y'";
            Query query = em.createQuery(querySTR);
             
            resultList = query.getResultList();
            
        } catch (Exception e) {
            throw  e;
        }
        
        return resultList;     
    }

}
