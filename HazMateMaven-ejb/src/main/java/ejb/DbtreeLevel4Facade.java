/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbtreeLevel4;
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
public class DbtreeLevel4Facade extends AbstractFacade<DbtreeLevel4> implements DbtreeLevel4FacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbtreeLevel4Facade() {
        super(DbtreeLevel4.class);
    }

    @Override
    public List<DbtreeLevel4> findByLevel3Id(int level3Id) {
        String querySTR;
        List<DbtreeLevel4> resultList = new ArrayList<>();
        try {
            querySTR = "FROM DbtreeLevel4 l4 WHERE l4.dbtreeLevel3.dbtreeLevel3PK.treeLevel3Id = ?1";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, level3Id);
            resultList = query.getResultList();

        } catch (Exception e) {
            throw e;
        }
        return resultList;
    }

}
