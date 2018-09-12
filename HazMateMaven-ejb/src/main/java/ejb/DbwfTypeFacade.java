/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbwfHeader;
import entities.DbwfType;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author lxra
 */
@Stateless
public class DbwfTypeFacade extends AbstractFacade<DbwfType> implements DbwfTypeFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbwfTypeFacade() {
        super(DbwfType.class);
    }
    
    @Override
    public List<DbwfHeader> checkwfType(String wfTypeId)   {
        return em.createQuery("FROM DbwfHeader w WHERE w.wfTypeId.wfTypeId = :checkId")
                .setParameter("checkId", wfTypeId)
                .setMaxResults(10)
                .getResultList(); 
    }
}
