/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbMenu;
import entities.DbtreeLevel2;
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
public class DbtreeLevel2Facade extends AbstractFacade<DbtreeLevel2> implements DbtreeLevel2FacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbtreeLevel2Facade() {
        super(DbtreeLevel2.class);
    }

    @Override
    public List<DbtreeLevel2> findByLevel1Id(int leve1Id) {
        String querySTR;
        List<DbtreeLevel2> resultList = new ArrayList<>();
        try {
            querySTR = "FROM DbtreeLevel2 l2 WHERE l2.dbtreeLevel1.treeLevel1Id = ?1";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, leve1Id);
            resultList = query.getResultList();

        } catch (Exception e) {
            throw e;
        }
        return resultList;
    }
    
    

}
