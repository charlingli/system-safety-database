/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbLocation;
import entities.DbchangeType;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Juan David
 */
@Stateless
public class DbchangeTypeFacade extends AbstractFacade<DbchangeType> implements DbchangeTypeFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbchangeTypeFacade() {
        super(DbchangeType.class);
    }

    @Override
    public List<DbLocation> checkChangeType(int changeTypeId) {
        return em.createQuery("FROM DbLocation l WHERE l.locationChangeType.changeTypeId = :checkChangeTypeId")
                .setParameter("checkChangeTypeId", changeTypeId)
                .setMaxResults(10)
                .getResultList();
    }

}
