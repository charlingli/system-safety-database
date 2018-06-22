/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbtreeLevel2;
import entities.DbtreeLevel3;
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
public class DbtreeLevel3Facade extends AbstractFacade<DbtreeLevel3> implements DbtreeLevel3FacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbtreeLevel3Facade() {
        super(DbtreeLevel3.class);
    }
    
    @Override
    public List<DbtreeLevel3> findByLevel2Id(int level2Id) {
        String querySTR;
        List<DbtreeLevel3> resultList = new ArrayList<>();
        try {
            querySTR = "FROM DbtreeLevel3 l3 WHERE l3.dbtreeLevel2.dbtreeLevel2PK.treeLevel2Id = ?1";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, level2Id);
            resultList = query.getResultList();

        } catch (Exception e) {
            throw e;
        }
        return resultList;
    }
    
}
