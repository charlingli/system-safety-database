/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbQuality;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author lxra
 */
@Stateless
public class DbQualityFacade extends AbstractFacade<DbQuality> implements DbQualityFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbQualityFacade() {
        super(DbQuality.class);
    }
    
    @Override
    public Double getHazardRating(String hazardId) {
        String queryStr;
        BigDecimal result = BigDecimal.ZERO;
        try {
            queryStr = "select sum(weighting * rating) / sum(weighting) from db_quality where hazardId = ?";
            Query query = em.createNativeQuery(queryStr);
            query.setParameter(1, hazardId);
            if ((BigDecimal) query.getResultList().get(0) != null) {
                result = (BigDecimal) query.getResultList().get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return result.doubleValue();    
    }
    
    @Override
    public List<DbQuality> getUserHazardRating(int userId, String hazardId) {
        String queryStr;
        List<DbQuality> resultList;
        try {
            queryStr = "SELECT r FROM DbQuality r WHERE r.dbQualityPK.userId = ?1 AND r.dbQualityPK.hazardId = ?2";
            Query query = em.createQuery(queryStr);
            query.setParameter(1, userId);
            query.setParameter(2, hazardId);
            resultList = query.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return resultList;    
    }
    
    @Override
    public Long getNumberOfRatings(String hazardId) {
        String queryStr;
        long result = 0;
        try {
            queryStr = "SELECT SUM(r.weighting) FROM DbQuality r WHERE r.dbQualityPK.hazardId = ?1";
            Query query = em.createQuery(queryStr);
            query.setParameter(1, hazardId);
            if (query.getResultList().get(0) != null) {
                result = (long) query.getResultList().get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return result;
    }
}
