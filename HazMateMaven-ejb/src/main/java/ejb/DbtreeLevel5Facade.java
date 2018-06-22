/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbtreeLevel5;
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
public class DbtreeLevel5Facade extends AbstractFacade<DbtreeLevel5> implements DbtreeLevel5FacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbtreeLevel5Facade() {
        super(DbtreeLevel5.class);
    }

    @Override
    public List<DbtreeLevel5> findByLevel4Id(int level4Id) {
        String querySTR;
        List<DbtreeLevel5> resultList = new ArrayList<>();
        try {
            querySTR = "FROM DbtreeLevel5 l5 WHERE l5.dbtreeLevel4.dbtreeLevel4PK.treeLevel4Id = ?1";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, level4Id);
            resultList = query.getResultList();

        } catch (Exception e) {
            throw e;
        }
        return resultList;
    }
    
    
}
