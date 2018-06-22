/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbtreeLevel6;
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
public class DbtreeLevel6Facade extends AbstractFacade<DbtreeLevel6> implements DbtreeLevel6FacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbtreeLevel6Facade() {
        super(DbtreeLevel6.class);
    }

    @Override
    public List<DbtreeLevel6> findByLevel5Id(int level5Id) {
                String querySTR;
        List<DbtreeLevel6> resultList = new ArrayList<>();
        try {
            querySTR = "FROM DbtreeLevel6 l6 WHERE l6.dbtreeLevel5.dbtreeLevel5PK.treeLevel5Id = ?1";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, level5Id);
            resultList = query.getResultList();

        } catch (Exception e) {
            throw e;
        }
        return resultList;
    }
    
}
