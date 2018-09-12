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
    
}
