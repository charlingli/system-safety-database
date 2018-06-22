/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbLocation;
import entities.DbconstructionType;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Juan David
 */
@Stateless
public class DbconstructionTypeFacade extends AbstractFacade<DbconstructionType> implements DbconstructionTypeFacadeLocal {

    @PersistenceContext(unitName = "HazMate-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DbconstructionTypeFacade() {
        super(DbconstructionType.class);
    }

    @Override
    public List<DbLocation> checkConstructionType(int constructionTypeId) {
        return em.createQuery("FROM DbLocation l WHERE l.locationConstructionType.constructionTypeId = :checkConstructionTypeId")
                .setParameter("checkConstructionTypeId", constructionTypeId)
                .setMaxResults(10)
                .getResultList();
    }

}
