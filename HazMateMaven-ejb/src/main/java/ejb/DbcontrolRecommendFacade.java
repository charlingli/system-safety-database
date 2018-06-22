/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbControlHazard;
import entities.DbcontrolRecommend;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author alan8
 */
@Stateless
public class DbcontrolRecommendFacade extends AbstractFacade<DbcontrolRecommend> implements DbcontrolRecommendFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbcontrolRecommendFacade() {
        super(DbcontrolRecommend.class);
    }

    @Override
    public List<DbControlHazard> checkControlRecommend(int controlRecommendId) {
        return em.createQuery("FROM DbControlHazard i WHERE i.controlRecommendId.controlRecommendId = :checkControlRecommendId")
                .setParameter("checkControlRecommendId", controlRecommendId)
                .setMaxResults(10)
                .getResultList();
    }

}
