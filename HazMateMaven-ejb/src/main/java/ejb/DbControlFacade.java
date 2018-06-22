/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbControl;
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
public class DbControlFacade extends AbstractFacade<DbControl> implements DbControlFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbControlFacade() {
        super(DbControl.class);
    }

    @Override
    public List<DbControl> findUnrelatedByHazardId(String hazardId) {
        String querySTR;
        List<DbControl> resultantList = new ArrayList<>();
        try {
            querySTR = "SELECT Ctl FROM DbControl Ctl "
                    + "WHERE NOT EXISTS "
                    + "(SELECT 'X' FROM DbControlHazard HCtl "
                    + "WHERE HCtl.dbControlHazardPK.controlId = Ctl.controlId "
                    + "AND HCtl.dbControlHazardPK.hazardId = ?1)";
            Query query = em.createQuery(querySTR);
            query.setParameter(1, hazardId);

            resultantList = query.getResultList();

        } catch (Exception e) {
            throw e;
        }
        return resultantList;
    }

}
