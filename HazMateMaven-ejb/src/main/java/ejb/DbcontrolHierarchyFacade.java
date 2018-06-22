/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbControl;
import entities.DbcontrolHierarchy;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Charling
 */
@Stateless
public class DbcontrolHierarchyFacade extends AbstractFacade<DbcontrolHierarchy> implements DbcontrolHierarchyFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbcontrolHierarchyFacade() {
        super(DbcontrolHierarchy.class);
    }

    @Override
    public List<DbControl> checkControlHierarchy(int controlHierarchyId) {
        return em.createQuery("FROM DbControl c WHERE c.controlHierarchyId.controlHierarchyId = :checkControlHierarchyId")
                .setParameter("checkControlHierarchyId", controlHierarchyId)
                .setMaxResults(10)
                .getResultList();
    }

}
